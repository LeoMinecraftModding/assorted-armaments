package team.leomc.assortedarmaments.trait;

import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class BaneUndeadWeaponTrait extends WeaponTrait {
	public static void onIncomingDamage(LivingIncomingDamageEvent event) {
		if (event.getEntity().getType().is(EntityTypeTags.UNDEAD)) {
			event.setAmount(event.getAmount() * 1.25F);
		}
	}
}
