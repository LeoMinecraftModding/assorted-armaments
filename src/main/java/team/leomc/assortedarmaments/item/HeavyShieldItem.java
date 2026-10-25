package team.leomc.assortedarmaments.item;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AADataComponents;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTrait;
import team.leomc.assortedarmaments.trait.WeaponTraitsComponent;

import java.util.List;

public class HeavyShieldItem extends TieredItem implements Equipable {
	public static final ResourceLocation HEAVY_SHIELD_ARMOR_TOUGHNESS_ID = AssortedArmaments.id("heavy_shield_armor_toughness");

	public HeavyShieldItem(Tier tier, Properties properties) {
		this(tier, properties.component(DataComponents.TOOL, createToolProperties()), List.of());
	}

	public HeavyShieldItem(Tier tier, Item.Properties properties, List<Holder<WeaponTrait>> extraTraits) {
		super(tier, properties.component(AADataComponents.WEAPON_TRAITS.get(),
			WeaponTraitsComponent.EMPTY
				.withTraitAdded(AAWeaponTraits.HEAVY_BLOCKING)
				.withTraitAdded(AAWeaponTraits.SHIELD_PARRY)
				.withTraitAdded(AAWeaponTraits.IRONCLAD)
				.withExtraTraitAdded(extraTraits)));
	}

	public static Tool createToolProperties() {
		return new Tool(List.of(), 1.0F, 2);
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(HEAVY_SHIELD_ARMOR_TOUGHNESS_ID, Math.floor(attackDamage + tier.getAttackDamageBonus()) + 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)
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

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltips, flags);
		String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
		AAUtils.addKnightMetalTooltip(stack, tooltips);
		AAUtils.addFieryTooltip(path, tooltips);
	}

	@NotNull
	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.OFFHAND;
	}
}
