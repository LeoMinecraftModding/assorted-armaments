package team.leomc.assortedarmaments.trait;

import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class PoisonImmuneWeaponTrait extends WeaponTrait {
	@Override
	public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
		if (event.getEffectInstance().is(MobEffects.POISON)) {
			event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
		}
	}
}
