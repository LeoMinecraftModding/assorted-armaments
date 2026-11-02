package team.leomc.assortedarmaments.trait;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public class SharpenWeaponTrait extends WeaponTrait {
	@Override
	public void onItemAttributeModifier(ItemAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		if (!stack.isDamageableItem() || stack.getMaxDamage() <= 0) {
			return;
		}
		double baseDamage = 0.0;
		for (ItemAttributeModifiers.Entry entry : event.getDefaultModifiers().modifiers()) {
			if (entry.attribute().value() == Attributes.ATTACK_DAMAGE.value() && entry.slot() == EquipmentSlotGroup.MAINHAND) {
				baseDamage += entry.modifier().amount();
			}
		}
		if (baseDamage <= 0.0) {
			return;
		}
		double ratio = (double) stack.getDamageValue() / (double) stack.getMaxDamage();
		int bonus = (int) Math.round(Math.max(baseDamage * (2.0 * ratio + 0.5) - baseDamage, 0.0));
		if (bonus > 0) {
			event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(getId(), bonus, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		}
	}
}
