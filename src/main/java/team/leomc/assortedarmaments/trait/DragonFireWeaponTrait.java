package team.leomc.assortedarmaments.trait;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import team.leomc.assortedarmaments.registry.AADataAttachments;

public class DragonFireWeaponTrait extends WeaponTrait {
	public static void onIncomingDamage(LivingEntity victim) {
		if (victim.invulnerableTime > 10) {
			return;
		}
		victim.hurt(victim.level().damageSources().dragonBreath(), 2.0F);
		victim.invulnerableTime = 0;
		victim.setData(AADataAttachments.DRAGON_FIRE_TICKS, 20);
	}

	public static void onTick(LivingEntity living) {
		int ticks = living.getData(AADataAttachments.DRAGON_FIRE_TICKS);
		if (ticks <= 0) {
			return;
		}
		living.setData(AADataAttachments.DRAGON_FIRE_TICKS, ticks - 1);
		if (living.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH,
				living.getX(), living.getY() + living.getBbHeight() * 0.5, living.getZ(),
				3, 0.3, 0.3, 0.3, 0.01);
		}
	}
}
