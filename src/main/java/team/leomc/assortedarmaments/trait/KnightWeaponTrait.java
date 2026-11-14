package team.leomc.assortedarmaments.trait;

import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import team.leomc.assortedarmaments.tags.AAItemTags;

public class KnightWeaponTrait extends WeaponTrait {
	@Override
	public boolean hidden() {
		return true;
	}

	public static void onIncomingDamage(LivingIncomingDamageEvent event) {
		LivingEntity target = event.getEntity();
		ItemStack weapon = event.getSource().getWeaponItem();
		if (weapon == null) {
			return;
		}
		DamageContainer container = event.getContainer();
		if (weapon.is(AAItemTags.KNIGHTMETAL_WEAPON) && target.getArmorValue() > 0) {
			if (target.getArmorCoverPercentage() > 0.0F) {
				int moreBonus = (int) (2.0F * target.getArmorCoverPercentage());
				container.setNewDamage(container.getNewDamage() + (float) moreBonus);
			} else {
				container.setNewDamage(container.getNewDamage() + 2.0F);
			}
			((ServerLevel) target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
		} else if (weapon.is(AAItemTags.KNIGHTMETAL_TOOL) && target.getArmorValue() == 0) {
			container.setNewDamage(container.getOriginalDamage() + 2.0F);
			((ServerLevel) target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
		}
	}
}
