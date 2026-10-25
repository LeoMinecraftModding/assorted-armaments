package team.leomc.assortedarmaments.item;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.data.AAEnchantments;
import team.leomc.assortedarmaments.entity.ThrownFlail;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AADataComponents;
import team.leomc.assortedarmaments.registry.AASounds;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTrait;
import team.leomc.assortedarmaments.trait.WeaponTraitsComponent;

import java.util.List;

public class FlailItem extends TieredItem {
	public FlailItem(Tier tier, Properties properties) {
		this(tier, properties.component(DataComponents.TOOL, createToolProperties(tier)), List.of());
	}

	public FlailItem(Tier tier, Item.Properties properties, List<Holder<WeaponTrait>> extraTraits) {
		super(tier, properties.component(AADataComponents.WEAPON_TRAITS.get(),
			WeaponTraitsComponent.EMPTY
				.withTraitAdded(AAWeaponTraits.FLAIL_SPIN)
				.withTraitAdded(AAWeaponTraits.TWO_HANDED)
				.withTraitAdded(AAWeaponTraits.LARGE_WEAPON)
				.withTraitAdded(AAWeaponTraits.SHOCK)
				.withTraitAdded(AAWeaponTraits.KNOCK)
				.withExtraTraitAdded(extraTraits)));
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
		if (!livingEntity.level().isClientSide && livingEntity instanceof Player player && !player.hasData(AADataAttachments.FLAIL)) {
			player.stopUsingItem();
			if (player.hasData(AADataAttachments.FLAIL_KINETIC_POWER) && player.getData(AADataAttachments.FLAIL_KINETIC_POWER) >= 2.5F) {
				ThrownFlail flail = new ThrownFlail(level, player);
				flail.setKineticPower(player.getData(AADataAttachments.FLAIL_KINETIC_POWER));
				flail.setItem(stack);
				flail.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.8f, 0.5f);
				level.addFreshEntity(flail);
				level.playSound(null, player.getX(), player.getY(), player.getZ(), AASounds.FLAIL_THROWN.get(), player.getSoundSource(), 1.0F, 1.0F);
			}
			player.removeData(AADataAttachments.FLAIL_KINETIC_POWER);
		}

		super.releaseUsing(stack, level, livingEntity, timeLeft);
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
		if (livingEntity instanceof Player player && !level.isClientSide) {
			if (!player.hasData(AADataAttachments.FLAIL)) {
				float kineticPower = player.getData(AADataAttachments.FLAIL_KINETIC_POWER);
				if (kineticPower <= 5) {
					if (kineticPower == 0.0F) {
						int initialVelocity = stack.getEnchantmentLevel(level.registryAccess().holderOrThrow(AAEnchantments.INITIAL_VELOCITY));
						kineticPower += 0.5F * initialVelocity;
					}
					float previousKineticPower = kineticPower;
					kineticPower += 0.05F;
					player.setData(AADataAttachments.FLAIL_KINETIC_POWER, kineticPower);
					if (previousKineticPower < 5.0F && kineticPower >= 5.0F) {
						level.playSound(null, player.getX(), player.getY(), player.getZ(), AASounds.FLAIL_FULLY_CHARGE.get(), player.getSoundSource(), 1.0F, 1.0F);
					}
				}
				if (kineticPower >= 2.5F) {
					for (LivingEntity living : livingEntity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, livingEntity, livingEntity.getBoundingBox().inflate(2))) {
						DamageSource source = living.damageSources().playerAttack(player);

						float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) * AACommonConfig.flailSpinDamageFactor);
						float knockback = (float) (player.getKnockback(living, source) * AACommonConfig.flailSpinKnockbackFactor);

						if (player.level() instanceof ServerLevel serverLevel) {
							damage = EnchantmentHelper.modifyDamage(serverLevel, player.getWeaponItem(), living, source, damage);
							knockback = EnchantmentHelper.modifyKnockback(serverLevel, player.getWeaponItem(), living, source, knockback);
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
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltips, flags);
		String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
		AAUtils.addKnightMetalTooltip(stack, tooltips);
		AAUtils.addFieryTooltip(path, tooltips);
	}
}
