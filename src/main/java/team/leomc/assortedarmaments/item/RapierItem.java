package team.leomc.assortedarmaments.item;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import team.leomc.assortedarmaments.registry.AADataComponents;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTrait;
import team.leomc.assortedarmaments.trait.WeaponTraitsComponent;

import java.util.List;

public class RapierItem extends SwordItem {
	public RapierItem(Tier tier, Properties properties) {
		this(tier, properties, List.of());
	}

	public RapierItem(Tier tier, Item.Properties properties, List<Holder<WeaponTrait>> extraTraits) {
		super(tier, properties.component(AADataComponents.WEAPON_TRAITS.get(),
			WeaponTraitsComponent.EMPTY
				.withTraitAdded(AAWeaponTraits.CONCENTRATION)
				.withTraitAdded(AAWeaponTraits.STAB)
				.withTraitAdded(AAWeaponTraits.SEE_THROUGH)
				.withExtraTraitAdded(extraTraits)));
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}
}
