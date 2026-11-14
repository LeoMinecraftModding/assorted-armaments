package team.leomc.assortedarmaments.trait;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class ChillWeaponTrait extends WeaponTrait {
	public static void onIncomingDamage(LivingIncomingDamageEvent event) {
		MobEffectInstance effect = event.getEntity().getEffect(MobEffects.MOVEMENT_SLOWDOWN);
		if (effect == null) {
			event.getEntity().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40));
		} else {
			if (effect.getAmplifier() >= 3) {
				event.getEntity().addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20, 1));
			}
			event.getEntity().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, effect.getAmplifier() + 1));
		}
	}
}
