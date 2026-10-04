package team.leomc.assortedarmaments.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
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
}
