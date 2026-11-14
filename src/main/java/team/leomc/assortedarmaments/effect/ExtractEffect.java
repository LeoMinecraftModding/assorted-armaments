package team.leomc.assortedarmaments.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import team.leomc.assortedarmaments.registry.AAEffects;
import team.leomc.assortedarmaments.registry.AAParticleTypes;

public class ExtractEffect extends MobEffect {
	public static final double RADIUS = 5.0;
	public static final float DAMAGE_PERCENTAGE = 0.2F;
	public static final int DURATION = 200;

	private static final int EXPLOSION_PARTICLES = 8;
	private static final int EXTRACT_PARTICLES = 24;
	private static final double GOLDEN_ANGLE = Math.PI * (3.0D - Math.sqrt(5.0D));

	public ExtractEffect() {
		super(MobEffectCategory.HARMFUL, 14456339);
	}

	public static void onDeath(LivingEntity entity) {
		if (entity.level().isClientSide) {
			return;
		}
		MobEffectInstance self = entity.getEffect(AAEffects.EXTRACT);
		int amplifier = self == null ? 0 : self.getAmplifier();
		float damage = entity.getMaxHealth() * DAMAGE_PERCENTAGE;
		AABB area = entity.getBoundingBox().inflate(RADIUS);
		for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area, target -> target != entity && target instanceof Enemy && target.isAlive() && !target.isSpectator() && target.distanceToSqr(entity) <= RADIUS * RADIUS)) {
			target.hurt(entity.damageSources().magic(), damage);
			if (target.isAlive()) {
				target.addEffect(new MobEffectInstance(AAEffects.EXTRACT, DURATION, amplifier, false, true));
			}
		}
		playExplosion(entity);
	}

	private static void playExplosion(LivingEntity entity) {
		if (!(entity.level() instanceof ServerLevel serverLevel)) {
			return;
		}
		RandomSource random = serverLevel.random;
		double cx = entity.getX();
		double cy = entity.getY() + entity.getBbHeight() * 0.5D;
		double cz = entity.getZ();

		serverLevel.playSound(null, cx, cy, cz, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 4.0F,
			(1.0F + (random.nextFloat() - random.nextFloat()) * 0.2F) * 0.7F);

		for (int i = 0; i < EXPLOSION_PARTICLES; i++) {
			Vec3 pos = spreadPoint(random, cx, cy, cz, i, EXPLOSION_PARTICLES, RADIUS * 0.7D);
			serverLevel.sendParticles(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
		}
		for (int i = 0; i < EXTRACT_PARTICLES; i++) {
			Vec3 pos = spreadPoint(random, cx, cy, cz, i, EXTRACT_PARTICLES, RADIUS * 0.9D);
			serverLevel.sendParticles(AAParticleTypes.EXTRACT_EXPLOSION.get(), pos.x, pos.y, pos.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
		}
	}

	private static Vec3 spreadPoint(RandomSource random, double cx, double cy, double cz, int index, int count, double radius) {
		double y = 1.0D - 2.0D * (index + 0.5D) / count;
		double ring = Math.sqrt(Math.max(0.0D, 1.0D - y * y));
		double angle = index * GOLDEN_ANGLE;
		double distance = radius * (0.55D + 0.45D * random.nextDouble());
		double jitter = 0.4D;
		return new Vec3(
			cx + Math.cos(angle) * ring * distance + (random.nextDouble() - 0.5D) * jitter,
			cy + y * distance * 0.6D + (random.nextDouble() - 0.5D) * jitter,
			cz + Math.sin(angle) * ring * distance + (random.nextDouble() - 0.5D) * jitter
		);
	}
}
