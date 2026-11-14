package team.leomc.assortedarmaments.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;

public class AAParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, AssortedArmaments.ID);

	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SYNCOPE_STAR = PARTICLE_TYPES.register("syncope_star", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EXTRACT_EXPLOSION = PARTICLE_TYPES.register("extract_explosion", () -> new SimpleParticleType(false));
}
