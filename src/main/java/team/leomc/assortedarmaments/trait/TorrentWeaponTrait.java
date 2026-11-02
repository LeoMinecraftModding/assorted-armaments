package team.leomc.assortedarmaments.trait;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

public class TorrentWeaponTrait extends WeaponTrait {
	public static boolean hasTrait(ItemStack stack) {
		return WeaponTraitHelper.hasTrait(AAWeaponTraits.TORRENT, stack);
	}

	public static boolean isEnvironmentActive(LivingEntity entity) {
		return entity.isInWaterOrRain();
	}

	public static boolean isActive(Player player) {
		return player.isUsingItem() && hasTrait(player.getUseItem()) && isEnvironmentActive(player);
	}
}
