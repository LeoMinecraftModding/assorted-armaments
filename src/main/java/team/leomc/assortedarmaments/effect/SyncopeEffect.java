package team.leomc.assortedarmaments.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.neoforged.neoforge.network.PacketDistributor;
import team.leomc.assortedarmaments.network.SyncopeStarsPayload;
import team.leomc.assortedarmaments.registry.AAEffects;

public class SyncopeEffect extends MobEffect {
	public static final int MIN_DURATION = 10;
	public static final int MAX_DURATION = 100;

	public SyncopeEffect() {
		super(MobEffectCategory.HARMFUL, 16646006);
	}

	public static boolean isActive(LivingEntity entity) {
		MobEffectInstance instance = entity.getEffect(AAEffects.SYNCOPE);
		return instance != null && instance.getDuration() >= MIN_DURATION;
	}

	@Override
	public void onEffectAdded(LivingEntity entity, int amplifier) {
		if (entity.level().isClientSide) return;
		MobEffectInstance instance = entity.getEffect(AAEffects.SYNCOPE);
		if (instance == null || instance.isInfiniteDuration() || instance.getDuration() > MAX_DURATION) return;
		PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncopeStarsPayload(entity.getId(), Math.max(instance.getDuration(), 1)));
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity instanceof Mob mob) {
			entity.setXRot(30.0F);
			entity.xRotO = 30.0F;
			if (!mob.level().isClientSide) {
				mob.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
				mob.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
				mob.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
			}
		}
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration > 0;
	}
}
