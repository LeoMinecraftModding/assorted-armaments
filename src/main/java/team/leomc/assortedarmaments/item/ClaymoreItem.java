package team.leomc.assortedarmaments.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.event.AACommonEvents;

public class ClaymoreItem extends SwordItem {
	public ClaymoreItem(Tier tier, Properties properties) {
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
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
		if (!entity.getPersistentData().getBoolean(AACommonEvents.TAG_NO_INTENTIONAL_SWEEP_ATTACK) && entity instanceof Player player && !player.getCooldowns().isOnCooldown(this)) {
			if (performSweepAttack(player, stack)) {
				stack.hurtAndBreak(1, entity, entity.getEquipmentSlotForItem(stack));
			}
			player.getCooldowns().addCooldown(this, AACommonConfig.claymoreSweepAttackCooldown);
		}
		return super.onEntitySwing(stack, entity, hand);
	}

	private boolean performSweepAttack(Player player, ItemStack stack) {
		boolean success = false;

		Level level = player.level();
		DamageSource source = level.damageSources().playerAttack(player);
		float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE));

		double reach = player.entityInteractionRange();
		for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(reach))) {
			if (living != player
				&& !player.isAlliedTo(living)
				&& (!(living instanceof ArmorStand) || !((ArmorStand) living).isMarker())
				&& player.distanceToSqr(living) < reach * reach
				&& living.position().subtract(player.position()).normalize().dot(player.getViewVector(1).normalize()) > 0) {
				if (level instanceof ServerLevel serverLevel) {
					damage = EnchantmentHelper.modifyDamage(serverLevel, stack, living, source, damage);
				}
				living.knockback(0.4F, Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)), (-Mth.cos(player.getYRot() * (float) (Math.PI / 180.0))));
				living.hurt(source, damage);
				if (level instanceof ServerLevel serverLevel) {
					EnchantmentHelper.doPostAttackEffects(serverLevel, living, source);
				}
				success = true;
			}
		}

		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
		player.sweepAttack();

		return success;
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return super.canPerformAction(stack, ability) || ability == ItemAbilities.SHIELD_BLOCK;
	}
}
