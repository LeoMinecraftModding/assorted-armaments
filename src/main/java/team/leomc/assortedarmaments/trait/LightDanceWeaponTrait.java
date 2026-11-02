package team.leomc.assortedarmaments.trait;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public class LightDanceWeaponTrait extends WeaponTrait {
	@Override
	public void onItemAttributeModifier(ItemAttributeModifierEvent event) {
		event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(getId(), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND);
	}
}
