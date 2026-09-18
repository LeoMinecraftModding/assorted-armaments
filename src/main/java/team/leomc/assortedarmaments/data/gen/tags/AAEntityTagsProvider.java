package team.leomc.assortedarmaments.data.gen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.tags.AAEntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class AAEntityTagsProvider extends EntityTypeTagsProvider {
	public AAEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
		super(output, future, AssortedArmaments.ID, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		tag(AAEntityTypeTags.ZOMBIES).add(
			EntityType.ZOMBIE,
			EntityType.HUSK,
			EntityType.DROWNED
		);
		tag(AAEntityTypeTags.PIGLINS).add(
			EntityType.ZOMBIFIED_PIGLIN,
			EntityType.PIGLIN,
			EntityType.PIGLIN_BRUTE
		);
	}
}
