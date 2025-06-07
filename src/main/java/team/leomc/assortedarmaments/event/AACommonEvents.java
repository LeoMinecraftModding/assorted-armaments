package team.leomc.assortedarmaments.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.SweepAttackEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.entity.ConcentratedAttacker;
import team.leomc.assortedarmaments.integration.MaterialsComponent;
import team.leomc.assortedarmaments.network.UpdateBlockAbilityPayload;
import team.leomc.assortedarmaments.tags.AAEntityTypeTags;
import team.leomc.assortedarmaments.tags.AAItemTags;

import java.util.Optional;

@EventBusSubscriber(modid = AssortedArmaments.ID)
public class AACommonEvents {
	public static final String TAG_BLOCKING_ABILITY_DISABLED = "blocking_ability_disabled";
	public static final String TAG_BLOCKING_DISABLED_TIME = "blocking_disabled_time";
	public static final String TAG_NO_INTENTIONAL_SWEEP_ATTACK = "no_intentional_sweep_attack";

	@SubscribeEvent
	private static void onJoinLevel(FinalizeSpawnEvent event) {
		Mob living = event.getEntity();
		ServerLevelAccessor level = event.getLevel();
		RandomSource random = level.getRandom();
		DifficultyInstance difficulty = level.getCurrentDifficultyAt(living.blockPosition());
		double enchantChance = 0.25 * difficulty.getSpecialMultiplier();
		if (living.getType().is(AAEntityTypeTags.ZOMBIES) && living.getRandom().nextFloat() < AACommonConfig.zombieUseWeaponChance) {
			Optional<Holder<Item>> weapon = BuiltInRegistries.ITEM.getRandomElementOf(AAItemTags.ZOMBIES_CAN_USE, living.getRandom());
			if (weapon.isPresent() && weapon.get().isBound()) {
				ItemStack stack = weapon.get().value().getDefaultInstance();
				if (!stack.isEmpty() && random.nextFloat() < enchantChance) {
					EnchantmentHelper.enchantItemFromProvider(stack, level.registryAccess(), VanillaEnchantmentProviders.MOB_SPAWN_EQUIPMENT, difficulty, random);
				}
				living.setItemInHand(InteractionHand.MAIN_HAND, stack);
			}
		}
		if (living.getType().is(AAEntityTypeTags.PIGLINS) && living.getRandom().nextFloat() < AACommonConfig.piglinUseWeaponChance) {
			Optional<Holder<Item>> weapon = BuiltInRegistries.ITEM.getRandomElementOf(AAItemTags.PIGLINS_CAN_USE, living.getRandom());
			if (weapon.isPresent() && weapon.get().isBound()) {
				ItemStack stack = weapon.get().value().getDefaultInstance();
				if (!stack.isEmpty() && random.nextFloat() < enchantChance) {
					EnchantmentHelper.enchantItemFromProvider(stack, level.registryAccess(), VanillaEnchantmentProviders.MOB_SPAWN_EQUIPMENT, difficulty, random);
				}
				living.setItemInHand(InteractionHand.MAIN_HAND, stack);
			}
		}
	}

	@SubscribeEvent
	private static void onIncomingDamage(LivingIncomingDamageEvent event) {
		LivingEntity victim = event.getEntity();
		if (event.getSource().getDirectEntity() instanceof LivingEntity living) {
			if (living.getWeaponItem().is(AAItemTags.ARMOR_BASED_DAMAGE)) {
				event.setAmount((float) (event.getAmount() + victim.getArmorValue() * AACommonConfig.armorBasedAttackDamagePercentage));
			}
			if (living.getWeaponItem().is(AAItemTags.SPEED_BASED_DAMAGE) && living.isSprinting() && living.onGround()) {
				event.setAmount((float) (event.getAmount() + living.getKnownMovement().length() * AACommonConfig.speedBasedAttackDamageModifier));
			}
			MaterialsComponent.applyMaterials(living.getWeaponItem(), material -> material.onIncomingDamage(event));
		}
	}

	@SubscribeEvent
	private static void onSweepAttack(SweepAttackEvent event) {
		if (event.isSweeping()) {
			event.getEntity().getPersistentData().putBoolean(TAG_NO_INTENTIONAL_SWEEP_ATTACK, true);
		}
	}

	@SubscribeEvent
	private static void onShieldBlock(LivingShieldBlockEvent event) {
		if (event.getOriginalBlock()) {
			LivingEntity blocker = event.getEntity();
			if (blocker.isUsingItem() && blocker.getUseItem().is(AAItemTags.CAN_BLOCK)) {
				double damage = 0;
				AttributeInstance damageInstance = blocker.getAttribute(Attributes.ATTACK_DAMAGE);
				if (damageInstance != null) {
					damage = damageInstance.getValue();
				}
				event.setBlockedDamage(event.getDamageSource().getDirectEntity() instanceof LivingEntity ? (float) (damage / 2) : 0);
			}
			if (event.getDamageSource().getDirectEntity() instanceof LivingEntity living && blocker instanceof ServerPlayer serverPlayer) {
				if (living.getWeaponItem().is(AAItemTags.DISABLES_BLOCKING_ON_ATTACK)) {
					blocker.stopUsingItem();
					blocker.getPersistentData().putBoolean(TAG_BLOCKING_ABILITY_DISABLED, true);
					blocker.getPersistentData().putInt(TAG_BLOCKING_DISABLED_TIME, 40);
					blocker.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 40, 1));
					PacketDistributor.sendToPlayer(serverPlayer, new UpdateBlockAbilityPayload(true));
				}
			}
		}
	}

	public static AttributeModifier getBlockSpeedModifier() {
		return new AttributeModifier(AssortedArmaments.id("block_speed"), -AACommonConfig.blockWalkSpeedModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@SubscribeEvent
	private static void onPostEntityTick(EntityTickEvent.Post event) {
		if (event.getEntity() instanceof LivingEntity living && !living.level().isClientSide) {
			if (living instanceof ServerPlayer serverPlayer) {
				if (living.getPersistentData().getInt(TAG_BLOCKING_DISABLED_TIME) == 1) {
					living.getPersistentData().putBoolean(TAG_BLOCKING_ABILITY_DISABLED, false);
					PacketDistributor.sendToPlayer(serverPlayer, new UpdateBlockAbilityPayload(false));
				}
				living.getPersistentData().putInt(TAG_BLOCKING_DISABLED_TIME, Math.max(living.getPersistentData().getInt(TAG_BLOCKING_DISABLED_TIME) - 1, 0));
				living.getPersistentData().putBoolean(TAG_NO_INTENTIONAL_SWEEP_ATTACK, false);
			}
			AttributeInstance speedInstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
			if (speedInstance != null) {
				if (living.isUsingItem() && living.getUseItem().is(AAItemTags.CAN_BLOCK)) {
					if (!speedInstance.hasModifier(getBlockSpeedModifier().id())) {
						speedInstance.addPermanentModifier(getBlockSpeedModifier());
					}
				} else {
					if (speedInstance.hasModifier(getBlockSpeedModifier().id())) {
						speedInstance.removeModifier(getBlockSpeedModifier().id());
					}
				}
			}
			if (living instanceof ConcentratedAttacker concentrated && concentrated.getConcentrationLevel() > 0 && (living.tickCount - concentrated.getLastConcentratedAttackTime() > 40 || living.getWeaponItem() != concentrated.getConcentratedWeapon())) {
				concentrated.clearConcentrationData();
			}
		}
	}

	@SubscribeEvent
	private static void onItemTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (FMLLoader.getDist() == Dist.CLIENT) {
			if (AssortedArmaments.ClientHelper.isShiftKeyDown()) {
				for (TagKey<Item> key : AAItemTags.TOOLTIP_TAGS) {
					if (stack.is(key)) {
						event.getToolTip().add(Component.translatable("desc." + AssortedArmaments.ID + "." + key.location().getPath()).withStyle(ChatFormatting.BLUE));
					}
				}
			} else if (AAItemTags.TOOLTIP_TAGS.stream().anyMatch(stack::is)) {
				event.getToolTip().add(Component.translatable("desc." + AssortedArmaments.ID + ".shift").withStyle(ChatFormatting.BLUE));
			}
		}
	}

	@SubscribeEvent
	public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
		MaterialsComponent.applyMaterials(event.getItemStack(), material -> material.onItemAttributeModifier(event));
	}

	@SubscribeEvent
	public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
		MaterialsComponent.applyMaterials(event.getEntity().getWeaponItem(), material -> material.onMobEffectApplicable(event));
	}

	@SubscribeEvent
	public static void onEntityInvulnerabilityCheck(EntityInvulnerabilityCheckEvent event) {
		if (!(event.getEntity() instanceof LivingEntity living)) return;
		MaterialsComponent.applyMaterials(living.getWeaponItem(), material -> material.onEntityInvulnerabilityCheck(event));
	}
}
