package team.leomc.assortedarmaments.trait;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

public class LightweightWeaponTrait extends WeaponTrait {
	public static void onTick(LivingEntity living) {
		AttributeInstance speedInstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
		if (speedInstance == null) {
			return;
		}
		AttributeModifier modifier = getSpeedModifier();
		if ((WeaponTraitHelper.hasTrait(AAWeaponTraits.LIGHTWEIGHT, living.getMainHandItem()) || WeaponTraitHelper.hasTrait(AAWeaponTraits.LIGHTWEIGHT, living.getOffhandItem())) && living.isSprinting()) {
			if (!speedInstance.hasModifier(modifier.id())) {
				speedInstance.addPermanentModifier(modifier);
			}
		} else if (speedInstance.hasModifier(modifier.id())) {
			speedInstance.removeModifier(modifier.id());
		}
	}

	public static AttributeModifier getSpeedModifier() {
		return new AttributeModifier(AssortedArmaments.id("lightweight_speed"), AACommonConfig.lightweightSprintSpeedModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
}
