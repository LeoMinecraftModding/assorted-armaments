package team.leomc.assortedarmaments.trait;

import net.neoforged.neoforge.event.entity.living.LivingSwapItemsEvent;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

public class TwoHandedWeaponTrait extends WeaponTrait {
	public static void onSwapHands(LivingSwapItemsEvent.Hands event) {
		if (WeaponTraitHelper.hasTrait(AAWeaponTraits.TWO_HANDED, event.getItemSwappedToOffHand())) {
			event.setCanceled(true);
		}
	}
}
