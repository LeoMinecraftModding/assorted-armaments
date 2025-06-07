package team.leomc.assortedarmaments.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.entity.FlailOwner;
import team.leomc.assortedarmaments.entity.ThrownFlail;

import java.util.List;

public class FlailItem extends TieredItem {
	public FlailItem(Tier tier, Properties properties) {
		super(tier, properties.component(DataComponents.TOOL, createToolProperties(tier)));
	}

	public static Tool createToolProperties(Tier tier) {
		return new Tool(List.of(Tool.Rule.deniesDrops(tier.getIncorrectBlocksForDrops()), Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, Math.max(tier.getSpeed() / 2f, 1.0f))), 1.0F, 1);
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
		return InteractionResultHolder.consume(stack);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
		int time = this.getUseDuration(stack, livingEntity) - timeLeft;
		if (!livingEntity.level().isClientSide && livingEntity instanceof Player player && time >= AACommonConfig.flailTimePerPowerLevel && !(player instanceof FlailOwner owner && owner.getFlail() != null)) {
			player.stopUsingItem();
			ThrownFlail flail = new ThrownFlail(level, player);
			flail.setPower(Math.min(time / AACommonConfig.flailTimePerPowerLevel, 5));
			flail.setItem(stack);
			flail.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.8f, 0.5f);
			level.addFreshEntity(flail);
		}
		super.releaseUsing(stack, level, livingEntity, timeLeft);
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
		if (livingEntity instanceof Player player && !level.isClientSide) {
			for (LivingEntity living : livingEntity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, livingEntity, livingEntity.getBoundingBox().inflate(2))) {
				DamageSource source = living.damageSources().playerAttack(player);

				float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) * AACommonConfig.flailSpinDamageFactor);
				float knockback = (float) (player.getKnockback(living, source) * AACommonConfig.flailSpinKnockbackFactor);

				if (player.level() instanceof ServerLevel serverLevel) {
					damage = EnchantmentHelper.modifyDamage(serverLevel, player.getWeaponItem(), living, source, damage);
				}

				if (living.hurt(source, damage) && player.level() instanceof ServerLevel serverLevel) {
					EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, living, source, stack);
				}

				if (knockback > 0.0F) {
					living.knockback(knockback * 0.5F, Mth.sin(player.getYRot() * Mth.DEG_TO_RAD), -Mth.cos(player.getYRot() * Mth.DEG_TO_RAD));
				}
			}
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return true;
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(ability);
	}
}
