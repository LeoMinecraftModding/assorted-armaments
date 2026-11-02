package team.leomc.assortedarmaments.trait;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class QuickAttackWeaponTrait extends WeaponTrait {
	public static void onIncomingDamage(LivingIncomingDamageEvent event) {
		event.setInvulnerabilityTicks(3);
	}
}
