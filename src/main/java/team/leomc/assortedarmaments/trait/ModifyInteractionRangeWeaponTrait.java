package team.leomc.assortedarmaments.trait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;

public class ModifyInteractionRangeWeaponTrait extends WeaponTrait{
	private final float attackRangeAmount;
	private final float interactionRangeAmount;

	public ModifyInteractionRangeWeaponTrait(float interactionRangeAmount, float attackRangeAmount){
		super();
		this.attackRangeAmount = attackRangeAmount;
		this.interactionRangeAmount = interactionRangeAmount;
	}

	protected float attackRangeAmount() {
		return attackRangeAmount;
	}

	protected float interactionRangeAmount() {
		return interactionRangeAmount;
	}

	public List<TraitAttribute> attributes() {
		return List.of(
			new TraitAttribute(Attributes.ENTITY_INTERACTION_RANGE, AttributeModifier.Operation.ADD_VALUE, attackRangeAmount()),
		    new TraitAttribute(Attributes.BLOCK_INTERACTION_RANGE, AttributeModifier.Operation.ADD_VALUE, interactionRangeAmount())
		);
	}

	public Component getDescription() {
		return Component.translatable("weapon_trait.modify_interaction_range.desc", descriptionArgs()).withStyle(ChatFormatting.GRAY);
	}

	public Object[] descriptionArgs() {
		return new Object[]{
			Component.literal(String.valueOf(attackRangeAmount())).withStyle(ChatFormatting.DARK_GREEN),
			Component.literal(String.valueOf(interactionRangeAmount())).withStyle(ChatFormatting.DARK_GREEN)
		};
	}
}
