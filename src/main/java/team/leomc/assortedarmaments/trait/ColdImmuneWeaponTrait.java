package team.leomc.assortedarmaments.trait;

import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class ColdImmuneWeaponTrait extends WeaponTrait {
	@Override
	public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
		if (event.getEffectInstance().is(MobEffects.MOVEMENT_SLOWDOWN) || event.getEffectInstance().is(MobEffects.DIG_SLOWDOWN)) {
			event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
		}
	}
}
