package team.leomc.assortedarmaments.item;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.registry.AADataComponents;
import team.leomc.assortedarmaments.registry.AAEffects;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTrait;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;
import team.leomc.assortedarmaments.trait.WeaponTraitsComponent;

import java.util.List;

public class MaceItem extends net.minecraft.world.item.MaceItem {
	private final Tier tier;

	public MaceItem(Tier tier, Item.Properties properties) {
		this(tier, properties, List.of());
	}

	public MaceItem(Tier tier, Item.Properties properties, List<Holder<WeaponTrait>> extraTraits) {
		super(properties
			.component(DataComponents.TOOL, createToolProperties(tier))
			.durability(tier.getUses())
			.component(AADataComponents.WEAPON_TRAITS.get(),
				WeaponTraitsComponent.EMPTY
					.withTraitAdded(AAWeaponTraits.SHOCK)
					.withTraitAdded(AAWeaponTraits.KNOCK)
					.withTraitAdded(AAWeaponTraits.THUMP)
					.withExtraTraitAdded(extraTraits)));
		this.tier = tier;
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

	public Tier getTier() {
		return this.tier;
	}

	@Override
	public int getEnchantmentValue() {
		return this.tier.getEnchantmentValue();
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return this.tier.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
		if (!(damageSource.getDirectEntity() instanceof LivingEntity attacker)) {
			return damage;
		}
		if (!(attacker.level() instanceof ServerLevel serverLevel)) {
			return 0.0F;
		}

		boolean smash = canSmashAttack(attacker);
		boolean superHeavy = !smash && canSmashAttackWithSuperHeavy(attacker);
		if (!smash && !superHeavy) {
			return 0.0F;
		}


		if (superHeavy && !(target instanceof LivingEntity)) {
			return 0.0F;
		}

		boolean hasSuperHeavy = WeaponTraitHelper.hasTrait(AAWeaponTraits.SUPER_HEAVY, attacker.getWeaponItem());
		if (target instanceof LivingEntity livingTarget) {
			livingTarget.addEffect(new MobEffectInstance(AAEffects.SYNCOPE, hasSuperHeavy ? 60 : 20));
		}

		if (superHeavy && attacker instanceof ServerPlayer serverPlayer) {
			if (target.onGround()) {
				serverPlayer.setSpawnExtraParticlesOnFall(true);
				serverLevel.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.MACE_SMASH_GROUND, serverPlayer.getSoundSource(), 1.0F, 1.0F);
			} else {
				serverLevel.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.MACE_SMASH_AIR, serverPlayer.getSoundSource(), 1.0F, 1.0F);
			}
		}

		float fallDistance = attacker.fallDistance;
		float fallBonus = hasSuperHeavy ? getVanillaFallBonus(fallDistance) : 0.0F;
		return fallBonus + EnchantmentHelper.modifyFallBasedDamage(serverLevel, attacker.getWeaponItem(), target, damageSource, 0.0F) * fallDistance;
	}

	private static float getVanillaFallBonus(float fallDistance) {
		if (fallDistance <= 3.0F) {
			return 4.0F * fallDistance;
		} else if (fallDistance <= 8.0F) {
			return 12.0F + 2.0F * (fallDistance - 3.0F);
		} else {
			return 22.0F + fallDistance - 8.0F;
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltips, flags);
		String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
		AAUtils.addKnightMetalTooltip(stack, tooltips);
		AAUtils.addFieryTooltip(path, tooltips);
	}

	public boolean canSmashAttackWithSuperHeavy(LivingEntity entity) {
		return entity.fallDistance > 0.1F && !entity.isFallFlying() && entity instanceof LivingEntity && WeaponTraitHelper.hasTrait(AAWeaponTraits.SUPER_HEAVY, this.getDefaultInstance());
	}
}
