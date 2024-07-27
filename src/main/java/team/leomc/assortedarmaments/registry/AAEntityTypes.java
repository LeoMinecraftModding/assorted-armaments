package team.leomc.assortedarmaments.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.entity.ThrownFlail;

public class AAEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, AssortedArmaments.ID);

	public static final DeferredHolder<EntityType<?>, EntityType<ThrownFlail>> FLAIL = ENTITY_TYPES.register("flail", () -> EntityType.Builder.<ThrownFlail>of(ThrownFlail::new, MobCategory.MISC).sized(0.8f, 0.8f).build(AssortedArmaments.strId("flail")));
}
