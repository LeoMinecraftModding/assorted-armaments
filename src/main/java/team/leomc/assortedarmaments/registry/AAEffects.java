package team.leomc.assortedarmaments.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.effect.PerforationEffect;
import team.leomc.assortedarmaments.effect.SyncopeEffect;

public class AAEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AssortedArmaments.ID);

	public static final DeferredHolder<MobEffect, SyncopeEffect> SYNCOPE = MOB_EFFECTS.register("syncope", SyncopeEffect::new);
	public static final DeferredHolder<MobEffect, PerforationEffect> PERFORATION = MOB_EFFECTS.register("perforation", PerforationEffect::new);
}
