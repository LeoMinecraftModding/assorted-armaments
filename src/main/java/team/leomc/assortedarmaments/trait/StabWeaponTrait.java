package team.leomc.assortedarmaments.trait;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import team.leomc.assortedarmaments.AACommonConfig;

public class StabWeaponTrait extends WeaponTrait {
	public static void onIncomingDamage(LivingIncomingDamageEvent event, LivingEntity attacker) {
		if (attacker.isSprinting()) {
			event.setAmount((float) (event.getAmount() * (1 + AACommonConfig.sprintExtraAttackDamagePercentage)));
		}
	}
}
