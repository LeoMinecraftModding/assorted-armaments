package team.leomc.assortedarmaments.data.gen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.data.AAEnchantments;

import java.util.concurrent.CompletableFuture;

public class AAEnchantmentTagsProvider extends EnchantmentTagsProvider {
	public AAEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, AssortedArmaments.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		tag(EnchantmentTags.TOOLTIP_ORDER)
			.add(
				AAEnchantments.CRIT,
				AAEnchantments.ARMOR_PENETRATION,
				AAEnchantments.SUPER_THUMP,
				AAEnchantments.KINETIC_ENERGY,
				AAEnchantments.IMPACT
			);
		tag(EnchantmentTags.IN_ENCHANTING_TABLE)
			.add(
				AAEnchantments.CRIT,
				AAEnchantments.ARMOR_PENETRATION,
				AAEnchantments.SUPER_THUMP,
				AAEnchantments.KINETIC_ENERGY,
				AAEnchantments.IMPACT
			);
		tag(EnchantmentTags.NON_TREASURE)
			.add(
				AAEnchantments.CRIT,
				AAEnchantments.ARMOR_PENETRATION,
				AAEnchantments.SUPER_THUMP,
				AAEnchantments.KINETIC_ENERGY,
				AAEnchantments.IMPACT
			);
	}
}
