package team.leomc.assortedarmaments.trait;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class HeatWeaponTrait extends WeaponTrait {
	public static void onIncomingDamage(LivingIncomingDamageEvent event) {
		if (event.getEntity().getRemainingFireTicks() < 100) {
			event.getEntity().setRemainingFireTicks(100);
		}
	}
}
