package team.leomc.assortedarmaments.trait;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.integration.aether.AetherHelper;

public class CrystallizeWeaponTrait extends WeaponTrait {
	private static final int CHANCE = 25;

	public static void onHit(LivingEntity attacker, LivingEntity target) {
		if (!AAUtils.isFullAttack(attacker) || target.getType().is(AetherHelper.NO_AMBROSIUM_DROPS_ENTITY)) {
			return;
		}
		if (attacker.getRandom().nextInt(CHANCE) != 0) {
			return;
		}
		BuiltInRegistries.ITEM.getOptional(AetherHelper.AMBROSIUM_SHARD).ifPresent(target::spawnAtLocation);
	}
}
