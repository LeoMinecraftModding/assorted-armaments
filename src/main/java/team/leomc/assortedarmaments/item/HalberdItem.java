package team.leomc.assortedarmaments.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import team.leomc.assortedarmaments.AssortedArmaments;

public class HalberdItem extends SwordItem {
	public HalberdItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed, float interactionRange) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(AssortedArmaments.id("base_interaction_range"), interactionRange, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(stack);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return super.canPerformAction(stack, ability) || ability == ItemAbilities.SHIELD_BLOCK;
	}
}
