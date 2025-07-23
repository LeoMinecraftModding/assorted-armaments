package team.leomc.assortedarmaments.data.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.data.gen.lang.AAChineseLanguageProvider;
import team.leomc.assortedarmaments.data.gen.lang.AAEnglishLanguageProvider;
import team.leomc.assortedarmaments.data.gen.model.AAItemModelProvider;
import team.leomc.assortedarmaments.data.gen.tags.AABlockTagsProvider;
import team.leomc.assortedarmaments.data.gen.tags.AAEnchantmentTagsProvider;
import team.leomc.assortedarmaments.data.gen.tags.AAEntityTagsProvider;
import team.leomc.assortedarmaments.data.gen.tags.AAItemTagsProvider;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = AssortedArmaments.ID)
public class DataGenerators {
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		generator.addProvider(event.includeClient(), new AAEnglishLanguageProvider(output));
		generator.addProvider(event.includeClient(), new AAChineseLanguageProvider(output));

		generator.addProvider(event.includeClient(), new AAItemModelProvider(output, helper));

		AABlockTagsProvider blockTags = new AABlockTagsProvider(output, lookupProvider, helper);
		generator.addProvider(event.includeServer(), blockTags);
		generator.addProvider(event.includeServer(), new AAItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), helper));

		DatapackBuiltinEntriesProvider registryProvider = new AARegistryProvider(output, lookupProvider);
		CompletableFuture<HolderLookup.Provider> lookup = registryProvider.getRegistryProvider();
		generator.addProvider(event.includeServer(), registryProvider);
		generator.addProvider(event.includeServer(), new AAEntityTagsProvider(output, lookup, helper));
		generator.addProvider(event.includeServer(), new AAEnchantmentTagsProvider(output, lookup, helper));
		generator.addProvider(event.includeServer(), new AARecipeProvider(output, lookup));
	}
}
