package team.leomc.assortedarmaments.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import team.leomc.assortedarmaments.AssortedArmaments;

public class AAEntityTypeTags {
	public static final TagKey<EntityType<?>> ZOMBIES = create("zombies");
	public static final TagKey<EntityType<?>> PIGLINS = create("piglins");
	public static final TagKey<EntityType<?>> SYNCOPE_IMMUNE = create("syncope_immune");

	private static TagKey<EntityType<?>> create(String id) {
		return TagKey.create(Registries.ENTITY_TYPE, AssortedArmaments.id(id));
	}
}
