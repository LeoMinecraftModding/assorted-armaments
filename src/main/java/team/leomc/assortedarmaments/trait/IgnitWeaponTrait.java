package team.leomc.assortedarmaments.trait;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class IgnitWeaponTrait extends WeaponTrait {
	@Override
	public boolean hidden() {
		return true;
	}

	public static void onIncomingDamage(LivingIncomingDamageEvent event) {
		if (event.getEntity().getRemainingFireTicks() < 300) {
			event.getEntity().setRemainingFireTicks(300);
		}
	}
}
