package team.leomc.assortedarmaments.trait;

import net.minecraft.world.entity.LivingEntity;
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.integration.aether.AetherHelper;
import team.leomc.assortedarmaments.registry.AADataAttachments;

public class ShiftGravityWeaponTrait extends WeaponTrait {
	public static void onHit(LivingEntity attacker, LivingEntity target) {
		if (AAUtils.isFullAttack(attacker)) {
			launch(target);
		}
	}

	public static void launch(LivingEntity target) {
		if (target.getType().is(AetherHelper.UNLAUNCHABLE_ENTITY)) {
			return;
		}
		if (!target.onGround() && !target.isInFluidType()) {
			return;
		}
		target.setData(AADataAttachments.GRAVITY_LAUNCH, true);
	}
}
