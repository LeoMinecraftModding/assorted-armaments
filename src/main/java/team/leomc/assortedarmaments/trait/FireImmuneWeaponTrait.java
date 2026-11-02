package team.leomc.assortedarmaments.trait;

import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;

public class FireImmuneWeaponTrait extends WeaponTrait {
	@Override
	public void onEntityInvulnerabilityCheck(EntityInvulnerabilityCheckEvent event) {
		if (!event.isInvulnerable()) {
			event.setInvulnerable(event.getSource().is(DamageTypeTags.IS_FIRE));
		}
	}
}
