package team.leomc.assortedarmaments.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.registry.AADataAttachments;

import java.util.List;

public class HeavyShieldItem extends TieredItem {
	public static final ResourceLocation HEAVY_SHIELD_ARMOR_ID = AssortedArmaments.id("heavy_shield_armor");

	public HeavyShieldItem(Tier tier, Properties properties) {
		super(tier, properties.component(DataComponents.TOOL, createToolProperties()));
	}

	public static Tool createToolProperties() {
		return new Tool(List.of(), 1.0F, 2);
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(HEAVY_SHIELD_ARMOR_ID, Math.floor((Attributes.ATTACK_DAMAGE.value().getDefaultValue() + attackDamage + tier.getAttackDamageBonus()) / 2), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
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
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return true;
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
		if (attacker.isUsingItem() && attacker.getUseItem().is(this)) {
			target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 0));
		}
	}

	@Override
	public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
		Entity directEntity = damageSource.getDirectEntity();
		if (directEntity != null && directEntity.tickCount - directEntity.getData(AADataAttachments.LAST_HEAVY_SHIELD_BLOCKED_DAMAGE_TIME) < AACommonConfig.heavyShieldFastCounterattackTime) {
			return directEntity.getData(AADataAttachments.LAST_HEAVY_SHIELD_BLOCKED_DAMAGE);
		}
		return super.getAttackDamageBonus(target, damage, damageSource);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(ability) || ability == ItemAbilities.SHIELD_BLOCK;
	}
}
