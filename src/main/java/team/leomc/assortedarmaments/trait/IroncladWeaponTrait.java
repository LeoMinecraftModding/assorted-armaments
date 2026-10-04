package team.leomc.assortedarmaments.trait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import team.leomc.assortedarmaments.AACommonConfig;

import java.util.List;

public class IroncladWeaponTrait extends WeaponTrait {
	@Override
	public List<TraitAttribute> attributes() {
		return List.of(new TraitAttribute(Attributes.ARMOR, AttributeModifier.Operation.ADD_VALUE, AACommonConfig.ironcladArmorValue, EquipmentSlotGroup.HAND));
	}

	@Override
	public Object[] descriptionArgs() {
		return new Object[]{Component.literal(String.valueOf(AACommonConfig.ironcladArmorValue)).withStyle(ChatFormatting.DARK_GREEN)};
	}
}
