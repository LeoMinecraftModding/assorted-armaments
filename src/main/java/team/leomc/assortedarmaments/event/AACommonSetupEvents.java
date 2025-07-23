package team.leomc.assortedarmaments.event;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import team.leomc.assortedarmaments.AssortedArmaments;

import java.util.concurrent.atomic.AtomicReference;

@EventBusSubscriber(modid = AssortedArmaments.ID)
public class AACommonSetupEvents {
	@SubscribeEvent
	private static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			AtomicReference<ItemStack> before = new AtomicReference<>(Items.NETHERITE_AXE.getDefaultInstance());
			BuiltInRegistries.ITEM.stream().filter(i -> BuiltInRegistries.ITEM.getKey(i).getNamespace().equals(AssortedArmaments.ID)).forEach(item -> {
				event.insertAfter(before.get(), item.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
				before.set(item.getDefaultInstance());
			});
		}
	}
}
