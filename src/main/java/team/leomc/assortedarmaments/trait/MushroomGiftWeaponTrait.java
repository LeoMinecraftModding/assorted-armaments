package team.leomc.assortedarmaments.trait;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import team.leomc.assortedarmaments.AAUtils;

public class MushroomGiftWeaponTrait extends WeaponTrait {
	private static final float SPORE_EXPLOSION_RADIUS = 1.5F;

	public static void onHit(LivingEntity attacker, LivingEntity target) {
		if (!AAUtils.isFullAttack(attacker)) {
			return;
		}
		int roll = attacker.getRandom().nextInt(4);
		switch (roll) {
			case 0 -> {
				if (target.isAlive()) {
					target.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 1));
				}
			}
			case 1 -> {
				if (attacker instanceof Player player) {
					player.getFoodData().eat(1, 0.5F);
				}
			}
			case 2 -> {
				if (target.isAlive()) {
					target.igniteForSeconds(12.0F);
				}
			}
			case 3 -> sporeExplosion(attacker, target);
		}
	}

	private static void sporeExplosion(LivingEntity attacker, LivingEntity target) {
		Level level = target.level();
		if (!(level instanceof ServerLevel serverLevel)) {
			return;
		}

		float damage = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5F;
		DamageSource source = level.damageSources().explosion(attacker, null);
		double radiusSqr = SPORE_EXPLOSION_RADIUS * SPORE_EXPLOSION_RADIUS;

		for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(SPORE_EXPLOSION_RADIUS))) {
			if (entity == attacker || entity == target || !entity.isAlive() || entity.isSpectator()) {
				continue;
			}
			if (entity.distanceToSqr(target) > radiusSqr) {
				continue;
			}
			if (!attacker.canAttack(entity) || attacker.isAlliedTo(entity)) {
				continue;
			}
			entity.hurt(source, damage);
		}

		serverLevel.sendParticles(ParticleTypes.SPORE_BLOSSOM_AIR, target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), 24, SPORE_EXPLOSION_RADIUS, SPORE_EXPLOSION_RADIUS, SPORE_EXPLOSION_RADIUS, 0.02);
	}
}
