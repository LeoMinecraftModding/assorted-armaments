package team.leomc.assortedarmaments.data.gen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.data.AAEnchantments;
import team.leomc.assortedarmaments.tags.AAEnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class AAEnchantmentTagsProvider extends EnchantmentTagsProvider {
	public AAEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, AssortedArmaments.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		tag(AAEnchantmentTags.EXCLUSIVE_SET_DEEP_WOUND)
			.add(
				AAEnchantments.DEEP_WOUND,
				AAEnchantments.PIN_UP
			);
		tag(EnchantmentTags.TOOLTIP_ORDER)
			.add(
				AAEnchantments.CRIT,
				AAEnchantments.KINETIC_ENERGY,
				AAEnchantments.SUPER_THUMP,
				AAEnchantments.INITIAL_VELOCITY,
				AAEnchantments.BLAST,
				AAEnchantments.DEEP_WOUND,
				AAEnchantments.PIN_UP,
				AAEnchantments.WITHSTAND,
				AAEnchantments.SPLIT_AIR
			);
		tag(EnchantmentTags.IN_ENCHANTING_TABLE)
			.add(
				AAEnchantments.CRIT,
				AAEnchantments.KINETIC_ENERGY,
				AAEnchantments.SUPER_THUMP,
				AAEnchantments.INITIAL_VELOCITY,
				AAEnchantments.BLAST,
				AAEnchantments.DEEP_WOUND,
				AAEnchantments.PIN_UP,
				AAEnchantments.WITHSTAND,
				AAEnchantments.SPLIT_AIR
			);
		tag(EnchantmentTags.NON_TREASURE)
			.add(
				AAEnchantments.CRIT,
				AAEnchantments.KINETIC_ENERGY,
				AAEnchantments.SUPER_THUMP,
				AAEnchantments.INITIAL_VELOCITY,
				AAEnchantments.BLAST,
				AAEnchantments.DEEP_WOUND,
				AAEnchantments.PIN_UP,
				AAEnchantments.WITHSTAND,
				AAEnchantments.SPLIT_AIR
			);
	}
}
