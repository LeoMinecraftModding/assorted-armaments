package team.leomc.assortedarmaments.item;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import team.leomc.assortedarmaments.registry.AADataComponents;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTrait;
import team.leomc.assortedarmaments.trait.WeaponTraitsComponent;

import java.util.List;

public class ClawItem extends TieredItem {
	public ClawItem(Tier tier, Item.Properties properties) {
		this(tier, properties, List.of());
	}

	public ClawItem(Tier tier, Item.Properties properties, List<Holder<WeaponTrait>> extraTraits) {
		super(tier, properties.component(AADataComponents.WEAPON_TRAITS.get(),
			WeaponTraitsComponent.EMPTY
//				.withTraitAdded(AAWeaponTraits.SHORT_WEAPON)
				.withTraitAdded(AAWeaponTraits.LIGHTWEIGHT)
				.withTraitAdded(AAWeaponTraits.DUAL_WIELD)
				.withTraitAdded(AAWeaponTraits.QUICK_ATTACK)
				.withExtraTraitAdded(extraTraits)));
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return true;
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
	}
}
