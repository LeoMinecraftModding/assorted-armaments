package team.leomc.assortedarmaments.trait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

public class CanBlockWeaponTrait extends WeaponTrait {
	@Override
	public Object[] descriptionArgs() {
		return new Object[]{Component.literal(String.valueOf(AACommonConfig.canBlockDamagePercentage * 100)).withStyle(ChatFormatting.DARK_GREEN)};
	}

	public static void onShieldBlock(LivingShieldBlockEvent event, LivingEntity blocker) {
		double damage = 0;
		AttributeInstance damageInstance = blocker.getAttribute(Attributes.ATTACK_DAMAGE);
		if (damageInstance != null) {
			damage = damageInstance.getValue();
		}
		event.setBlockedDamage((float) (damage * AACommonConfig.canBlockDamagePercentage));
	}

	public static void onTick(LivingEntity living) {
		AttributeInstance speedInstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
		if (speedInstance == null) {
			return;
		}
		AttributeModifier modifier = getSpeedModifier();
		if (living.isUsingItem() && WeaponTraitHelper.hasTrait(AAWeaponTraits.CAN_BLOCK, living.getUseItem())) {
			if (!speedInstance.hasModifier(modifier.id())) {
				speedInstance.addPermanentModifier(modifier);
			}
		} else if (speedInstance.hasModifier(modifier.id())) {
			speedInstance.removeModifier(modifier.id());
		}
	}

	public static AttributeModifier getSpeedModifier() {
		return new AttributeModifier(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(AAWeaponTraits.CAN_BLOCK.getId().getNamespace(), "weapon_trait/" + AAWeaponTraits.CAN_BLOCK.getId().getPath() + "/" + Attributes.MOVEMENT_SPEED.unwrapKey().orElseThrow().location().getPath()), -AACommonConfig.blockWalkSpeedModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
}
