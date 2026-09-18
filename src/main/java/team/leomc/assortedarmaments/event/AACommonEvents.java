package team.leomc.assortedarmaments.event;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
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
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.SweepAttackEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.data.AAEnchantments;
import team.leomc.assortedarmaments.entity.ThrownJavelin;
import team.leomc.assortedarmaments.integration.MaterialsComponent;
import team.leomc.assortedarmaments.item.*;
import team.leomc.assortedarmaments.network.UpdateBlockAbilityPayload;
import team.leomc.assortedarmaments.registry.*;
import team.leomc.assortedarmaments.tags.AAEntityTypeTags;
import team.leomc.assortedarmaments.tags.AAItemTags;
import team.leomc.assortedarmaments.trait.*;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = AssortedArmaments.ID)
public class AACommonEvents {
	public static final ResourceLocation TWO_HANDED_SPEED_ID = AssortedArmaments.id("two_handed_speed");

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
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.SHOCK, living.getWeaponItem())) {
				event.setAmount((float) (event.getAmount() + victim.getArmorValue() * AACommonConfig.armorBasedAttackDamagePercentage));
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STAB, living.getWeaponItem()) && living.isSprinting()) {
				event.setAmount((float) (event.getAmount() * (1 + AACommonConfig.sprintExtraAttackDamagePercentage)));
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.QUICK_ATTACK, living.getWeaponItem()) && living.isSprinting()) {
				event.setInvulnerabilityTicks(5);
			}
			MaterialsComponent.applyMaterials(living.getWeaponItem(), material -> material.onIncomingDamage(event));
		}
		event.getContainer().setPostAttackInvulnerabilityTicks(DualWieldWeaponTrait.onModifyPostAttackInvulnerabilityTicks(event.getEntity(), event.getSource(), event.getAmount(), event.getContainer().getPostAttackInvulnerabilityTicks()));
	}

	@SubscribeEvent
	public static void onPreEntityHurt(LivingDamageEvent.Pre event) {
		LivingEntity target = event.getEntity();
		DamageSource source = event.getSource();
		LivingEntity attacker = (LivingEntity) source.getEntity();
		ItemStack weaponItem = source.getWeaponItem();
		if (weaponItem != null) {
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STRONG_SWEEP, weaponItem)) {
				if (attacker != null && ((Player) attacker).getAttackStrengthScale(0.5F) > 0.9f && attacker.onGround()) {
					StrongSweepWeaponTrait.performSweepAttack((Player) attacker, attacker.getWeaponItem());
				}
			}
		}
	}
	@SubscribeEvent
	public static void onPostEntityHurt(LivingDamageEvent.Post event) {
		LivingEntity target = event.getEntity();
		DamageSource source = event.getSource();
		LivingEntity attacker = (LivingEntity) source.getEntity();
		ItemStack weaponItem = source.getWeaponItem();
		if (attacker != null) {
			if (weaponItem != null) {
				if (((Player) attacker).getAttackStrengthScale(0.5F) > 0.9f && attacker.onGround()) {
					if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STRONG_SWEEP, weaponItem)) {
						StrongSweepWeaponTrait.performSweepAttack((Player) attacker, attacker.getWeaponItem());
					}
					if (WeaponTraitHelper.hasTrait(AAWeaponTraits.CONCENTRATION, weaponItem)) {

						if (target == attacker.getData(AADataAttachments.CONCENTRATED_TARGET) && weaponItem == attacker.getData(AADataAttachments.CONCENTRATED_WEAPON)) {
							attacker.setData(AADataAttachments.LAST_CONCENTRATED_ATTACK_TIME, attacker.tickCount);
							attacker.setData(AADataAttachments.CONCENTRATION_LEVEL, Math.min(attacker.getData(AADataAttachments.CONCENTRATION_LEVEL) + 1, 4));
						} else {
							attacker.setData(AADataAttachments.CONCENTRATED_TARGET, target);
							attacker.setData(AADataAttachments.CONCENTRATED_WEAPON, weaponItem);
							attacker.setData(AADataAttachments.LAST_CONCENTRATED_ATTACK_TIME, attacker.tickCount);
							attacker.setData(AADataAttachments.CONCENTRATION_LEVEL, 0);
						}
					}
				}
				if (weaponItem.getItem() instanceof net.minecraft.world.item.MaceItem && WeaponTraitHelper.hasTrait(AAWeaponTraits.THUMP, weaponItem)) {
					target.setData(AADataAttachments.NO_TARGET_TIME, Math.max(target.getData(AADataAttachments.NO_TARGET_TIME), 20));
				}
			}
		}
	}

	@SubscribeEvent
	private static void onSweepAttack(SweepAttackEvent event) {
		Player player = event.getEntity();
		if (event.isSweeping()) {
			player.setData(AADataAttachments.NO_INTENTIONAL_SWEEP_ATTACK, true);
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STRONG_SWEEP, player.getWeaponItem())) {
				event.setSweeping(false);
			}
		}
	}

	@SubscribeEvent
	private static void onCriticalHit(CriticalHitEvent event) {
		Player player = event.getEntity();
		Entity target = event.getTarget();
		if (event.isCriticalHit()) {
			AttributeInstance multiplier = player.getAttribute(AAAttributes.CRITICAL_ATTACK_DAMAGE_MULTIPLIER);
			if (multiplier != null) {
				event.setDamageMultiplier(event.getDamageMultiplier() * (float) multiplier.getValue());
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.KNOCK, player.getWeaponItem()) && target instanceof LivingEntity living) {
				float thumpEffectiveness = 0;
				if (event.getEntity().level() instanceof ServerLevel serverLevel) {
					thumpEffectiveness = AAEnchantments.modifyThumpEffectiveness(serverLevel, player.getWeaponItem(), target, player.damageSources().playerAttack(player), thumpEffectiveness);
				}
				if (thumpEffectiveness > 0) {
					living.stopUsingItem();
					living.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, (int) (40 + thumpEffectiveness * 60), 1));
					living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) thumpEffectiveness * 100, 0));
					if (living instanceof ServerPlayer serverPlayer) {
						living.setData(AADataAttachments.BLOCKING_DISABLED_TIME, Math.max(living.getData(AADataAttachments.BLOCKING_DISABLED_TIME), 40));
						PacketDistributor.sendToPlayer(serverPlayer, new UpdateBlockAbilityPayload(true));
					}
				}
			}
		}
	}

	@SubscribeEvent
	private static void onEntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, AAAttributes.CRITICAL_ATTACK_DAMAGE_MULTIPLIER);
	}

	@SubscribeEvent
	private static void onChangeTarget(LivingChangeTargetEvent event) {
		if (event.getEntity().getData(AADataAttachments.NO_TARGET_TIME) > 0) {
			event.setNewAboutToBeSetTarget(null);
		}
	}

	@SubscribeEvent
	private static void onShieldBlock(LivingShieldBlockEvent event) {
		if (event.getOriginalBlock()) {
			LivingEntity blocker = event.getEntity();
			DamageSource source = event.getDamageSource();
			if (blocker.isUsingItem() && WeaponTraitHelper.hasTrait(AAWeaponTraits.CAN_BLOCK, blocker.getUseItem())) {
				double damage = 0;
				AttributeInstance damageInstance = blocker.getAttribute(Attributes.ATTACK_DAMAGE);
				if (damageInstance != null) {
					damage = damageInstance.getValue();
				}
				event.setBlockedDamage(source.getDirectEntity() instanceof LivingEntity ? (float) (damage / 2) : 0);
			}
			if (blocker.isUsingItem() && blocker.getUseItem().is(AAItemTags.HEAVY_SHIELDS)) {
				if (blocker.getTicksUsingItem() <= AACommonConfig.heavyShieldFastBlockTime && source.getDirectEntity() instanceof LivingEntity living) {
					double damage = 0;
					AttributeInstance damageInstance = blocker.getAttribute(Attributes.ATTACK_DAMAGE);
					if (damageInstance != null) {
						damage = damageInstance.getValue();
					}
					DamageSource damageSource = blocker instanceof Player player ? blocker.damageSources().playerAttack(player) : blocker.damageSources().mobAttack(living);
					if (blocker.level() instanceof ServerLevel serverLevel) {
						damage = EnchantmentHelper.modifyDamage(serverLevel, blocker.getWeaponItem(), living, damageSource, (float) damage);
					}
					if (living.hurt(damageSource, (float) (damage * AACommonConfig.heavyShieldFastBlockDamageReflectionPercentage)) && blocker.level() instanceof ServerLevel serverLevel) {
						EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, living, damageSource, blocker.getWeaponItem());
					}
				}
				blocker.setData(AADataAttachments.LAST_HEAVY_SHIELD_BLOCKED_DAMAGE, event.getDamageContainer().getNewDamage());
				blocker.setData(AADataAttachments.LAST_HEAVY_SHIELD_BLOCKED_DAMAGE_TIME, blocker.tickCount);
			}
			if (event.getDamageSource().getDirectEntity() instanceof LivingEntity living && WeaponTraitHelper.hasTrait(AAWeaponTraits.KNOCK, living.getWeaponItem()) && blocker instanceof ServerPlayer serverPlayer) {
				float thumpEffectiveness = 0;
				if (event.getEntity().level() instanceof ServerLevel serverLevel) {
					thumpEffectiveness = AAEnchantments.modifyThumpEffectiveness(serverLevel, living.getWeaponItem(), blocker, event.getDamageSource(), thumpEffectiveness);
				}
				blocker.stopUsingItem();
				blocker.setData(AADataAttachments.BLOCKING_DISABLED_TIME, Math.max(blocker.getData(AADataAttachments.BLOCKING_DISABLED_TIME), 40));
				blocker.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, (int) (40 + thumpEffectiveness * 60), 1));
				blocker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) thumpEffectiveness * 100, 0));
				PacketDistributor.sendToPlayer(serverPlayer, new UpdateBlockAbilityPayload(true));
			}
		}
	}

	public static AttributeModifier getBlockSpeedModifier() {
		return new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AAWeaponTraits.CAN_BLOCK.getId().getNamespace(), "weapon_trait/" + AAWeaponTraits.CAN_BLOCK.getId().getPath() + "/" + Attributes.MOVEMENT_SPEED.unwrapKey().orElseThrow().location().getPath()), -AACommonConfig.blockWalkSpeedModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	public static AttributeModifier getHeavyShieldBlockSpeedModifier() {
		return new AttributeModifier(AssortedArmaments.id("heavy_shield_block_speed"), -AACommonConfig.heavyShieldBlockWalkSpeedModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	public static AttributeModifier getHeavyShieldBlockDamageModifier() {
		return new AttributeModifier(AssortedArmaments.id("heavy_shield_block_damage"), -AACommonConfig.heavyShieldBlockAttackDamageModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	public static AttributeModifier getLightweightSpeedModifier() {
		return new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AAWeaponTraits.LIGHTWEIGHT.getId().getNamespace(), "weapon_trait/" + AAWeaponTraits.LIGHTWEIGHT.getId().getPath() + "/" + Attributes.MOVEMENT_SPEED.unwrapKey().orElseThrow().location().getPath()), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@SubscribeEvent
	private static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
		if (WeaponTraitHelper.hasTrait(AAWeaponTraits.DUAL_WIELD, event.getItemStack())) {
			if (event.getEntity() instanceof Player player) {
				DualWieldWeaponTrait.offhandAttack(event.getEntity().level(), player, event.getHand(), event.getItemStack().getItem());
			}
		}
	}

	@SubscribeEvent
	private static void onPostEntityTick(EntityTickEvent.Post event) {
		Entity entity = event.getEntity();
		if (entity instanceof LivingEntity living && !living.level().isClientSide) {
			if (living instanceof Player player) {
				player.setData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER, player.getData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER) + 1);
				if (!ItemStack.matches(player.getData(AADataAttachments.LAST_OFFHAND_ITEM), player.getOffhandItem())) {
					if (!ItemStack.isSameItem(player.getData(AADataAttachments.LAST_OFFHAND_ITEM), player.getOffhandItem())) {
						player.setData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER, 0);
					}
					player.setData(AADataAttachments.LAST_OFFHAND_ITEM, living.getOffhandItem().copy());
				}
			}
			if (living instanceof ServerPlayer serverPlayer) {
				if (living.getData(AADataAttachments.BLOCKING_DISABLED_TIME) == 1) {
					PacketDistributor.sendToPlayer(serverPlayer, new UpdateBlockAbilityPayload(false));
				}
				living.setData(AADataAttachments.BLOCKING_DISABLED_TIME, Math.max(living.getData(AADataAttachments.BLOCKING_DISABLED_TIME) - 1, 0));
				living.setData(AADataAttachments.NO_INTENTIONAL_SWEEP_ATTACK, false);
			}
			int noTargetTime = living.getData(AADataAttachments.NO_TARGET_TIME);
			if (noTargetTime > 0) {
				living.setData(AADataAttachments.NO_TARGET_TIME, noTargetTime - 1);
				if (living instanceof Mob mob && mob.getTarget() != null) {
					mob.setTarget(null);
					living.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
					mob.getNavigation().stop();
					mob.setLastHurtByMob(null);
				}
			}
			AttributeInstance speedInstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
			if (speedInstance != null) {
				AttributeModifier blockSpeedModifier = getBlockSpeedModifier();
				if (living.isUsingItem() && WeaponTraitHelper.hasTrait(AAWeaponTraits.CAN_BLOCK, living.getUseItem())) {
					if (!speedInstance.hasModifier(blockSpeedModifier.id())) {
						speedInstance.addPermanentModifier(blockSpeedModifier);
					}
				} else {
					if (speedInstance.hasModifier(blockSpeedModifier.id())) {
						speedInstance.removeModifier(blockSpeedModifier.id());
					}
				}
				AttributeModifier heavyShieldBlockSpeedModifier = getHeavyShieldBlockSpeedModifier();
				if (living.isUsingItem() && living.getUseItem().is(AAItemTags.HEAVY_SHIELDS)) {
					if (!speedInstance.hasModifier(heavyShieldBlockSpeedModifier.id())) {
						speedInstance.addPermanentModifier(heavyShieldBlockSpeedModifier);
					}
				} else {
					if (speedInstance.hasModifier(heavyShieldBlockSpeedModifier.id())) {
						speedInstance.removeModifier(heavyShieldBlockSpeedModifier.id());
					}
				}
				AttributeModifier lightweightSpeedModifier = getLightweightSpeedModifier();
				if ((WeaponTraitHelper.hasTrait(AAWeaponTraits.LIGHTWEIGHT, living.getMainHandItem()) || WeaponTraitHelper.hasTrait(AAWeaponTraits.LIGHTWEIGHT, living.getOffhandItem())) && living.isSprinting()) {
					if (!speedInstance.hasModifier(lightweightSpeedModifier.id())) {
						speedInstance.addPermanentModifier(lightweightSpeedModifier);
					}
				} else {
					if (speedInstance.hasModifier(lightweightSpeedModifier.id())) {
						speedInstance.removeModifier(lightweightSpeedModifier.id());
					}
				}
			}
			AttributeInstance damageInstance = living.getAttribute(Attributes.ATTACK_DAMAGE);
			if (damageInstance != null) {
				AttributeModifier heavyShieldBlockDamageModifier = getHeavyShieldBlockDamageModifier();
				if (living.isUsingItem() && living.getUseItem().is(AAItemTags.HEAVY_SHIELDS)) {
					if (!damageInstance.hasModifier(heavyShieldBlockDamageModifier.id())) {
						damageInstance.addPermanentModifier(heavyShieldBlockDamageModifier);
					}
				} else {
					if (damageInstance.hasModifier(heavyShieldBlockDamageModifier.id())) {
						damageInstance.removeModifier(heavyShieldBlockDamageModifier.id());
					}
				}
			}
			if (living.getData(AADataAttachments.CONCENTRATION_LEVEL) > 0) {
				int level = living.getData(AADataAttachments.CONCENTRATION_LEVEL);
				AttributeInstance attackDamageInstance = living.getAttribute(Attributes.ATTACK_DAMAGE);
				ResourceLocation attackDamageLocation = ResourceLocation.fromNamespaceAndPath(AAWeaponTraits.CONCENTRATION.getId().getNamespace(), "weapon_trait/" + AAWeaponTraits.CONCENTRATION.getId().getPath() + "/" + Attributes.ATTACK_DAMAGE.unwrapKey().orElseThrow().location().getPath());
				if (attackDamageInstance != null) {
					attackDamageInstance.removeModifier(attackDamageLocation);
					attackDamageInstance.addTransientModifier(new AttributeModifier(attackDamageLocation, 0.5 * level, AttributeModifier.Operation.ADD_VALUE));
				}
				AttributeInstance attackSpeedInstance = living.getAttribute(Attributes.ATTACK_SPEED);
				ResourceLocation attackSpeedLocation = ResourceLocation.fromNamespaceAndPath(AAWeaponTraits.CONCENTRATION.getId().getNamespace(), "weapon_trait/" + AAWeaponTraits.CONCENTRATION.getId().getPath() + "/" + Attributes.ATTACK_SPEED.unwrapKey().orElseThrow().location().getPath());
				if (attackSpeedInstance != null) {
					attackSpeedInstance.removeModifier(attackSpeedLocation);
					attackSpeedInstance.addPermanentModifier(new AttributeModifier(attackSpeedLocation, 0.1 * level, AttributeModifier.Operation.ADD_VALUE));
				}
				if (living.tickCount - living.getData(AADataAttachments.LAST_CONCENTRATED_ATTACK_TIME) > 40 || living.getWeaponItem() != living.getData(AADataAttachments.CONCENTRATED_WEAPON)) {
					living.removeData(AADataAttachments.CONCENTRATED_TARGET);
					living.removeData(AADataAttachments.CONCENTRATED_WEAPON);
					living.removeData(AADataAttachments.LAST_CONCENTRATED_ATTACK_TIME);
					living.removeData(AADataAttachments.CONCENTRATION_LEVEL);
					if (attackDamageInstance != null) {
						attackDamageInstance.removeModifier(attackDamageLocation);
					}
					if (attackSpeedInstance != null) {
						attackSpeedInstance.removeModifier(attackSpeedLocation);
					}
				}
				if (!living.getWeaponItem().is(AAItemTags.FLAILS)) {
					living.removeData(AADataAttachments.FLAIL_KINETIC_POWER);
				}
			}
		}
		if (entity.hasData(AADataAttachments.FLAIL) && entity.getData(AADataAttachments.FLAIL).isRemoved()) {
			entity.removeData(AADataAttachments.FLAIL);
		}
	}

	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
		Player player = event.getEntity();
		Entity target = event.getTarget();
		if (player.level().isClientSide) {
			return;
		}

		List<ThrownJavelin> javelins = player.level().getEntitiesOfClass(
			ThrownJavelin.class,
			target.getBoundingBox().inflate(0.25f),
			javelin -> javelin.isInTarget() && javelin.getOwner().is(player) && javelin.getTarget().is(target)
		);
		if (!javelins.isEmpty()) {
			javelins.getFirst().removeFromTarget(player);
		}
	}

	@SubscribeEvent
	private static void onItemTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (FMLLoader.getDist() == Dist.CLIENT) {
			stack.addToTooltip(AADataComponents.WEAPON_TRAITS, event.getContext(), event.getToolTip()::add, event.getFlags());
		}
	}

	@SubscribeEvent
	public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		if (!stack.isEmpty()) {
			if (WeaponTraitHelper.hasAnyTrait(stack)) {
				for (Holder<WeaponTrait> holder : WeaponTraitHelper.getTraits(stack).traits()) {
					WeaponTrait trait = holder.value();
					ResourceLocation traitId = trait.getId();

					for (WeaponTrait.TraitAttribute attr : trait.attributes()) {
						ResourceLocation modifierId = attr.makeId(traitId);

						event.removeModifier(attr.attribute(), modifierId);
						event.addModifier(attr.attribute(), attr.createModifier(traitId), EquipmentSlotGroup.MAINHAND);
					}
				}
			}
		}

		MaterialsComponent.applyMaterials(stack, material -> material.onItemAttributeModifier(event));
	}

	@SubscribeEvent
	public static void onItemDefaultComponents(ModifyDefaultComponentsEvent event) {
		event.modifyMatching(item -> item instanceof ClaymoreItem, builder ->
			builder.set(AADataComponents.WEAPON_TRAITS.get(),
				WeaponTraitsComponent.EMPTY
					.withTraitAdded(AAWeaponTraits.TWO_HANDED)
					.withTraitAdded(AAWeaponTraits.STRONG_SWEEP)
					.withTraitAdded(AAWeaponTraits.LARGE_WEAPON)
					.withTraitAdded(AAWeaponTraits.CAN_BLOCK)
			)
		);
		event.modifyMatching(item -> item instanceof MaceItem, builder ->
			builder.set(AADataComponents.WEAPON_TRAITS.get(),
				WeaponTraitsComponent.EMPTY
					.withTraitAdded(AAWeaponTraits.SHOCK)
					.withTraitAdded(AAWeaponTraits.KNOCK)
					.withTraitAdded(AAWeaponTraits.THUMP)
			)
		);
		event.modifyMatching(item -> item instanceof FlailItem, builder ->
			builder.set(AADataComponents.WEAPON_TRAITS.get(),
				WeaponTraitsComponent.EMPTY
					.withTraitAdded(AAWeaponTraits.FLAIL_SPIN)
					.withTraitAdded(AAWeaponTraits.TWO_HANDED)
					.withTraitAdded(AAWeaponTraits.LARGE_WEAPON)
					.withTraitAdded(AAWeaponTraits.SHOCK)
					.withTraitAdded(AAWeaponTraits.KNOCK)
			)
		);
		event.modifyMatching(item -> item instanceof RapierItem, builder ->
			builder.set(AADataComponents.WEAPON_TRAITS.get(),
				WeaponTraitsComponent.EMPTY
					.withTraitAdded(AAWeaponTraits.CONCENTRATION)
					.withTraitAdded(AAWeaponTraits.STAB)
					.withTraitAdded(AAWeaponTraits.SEE_THROUGH)
			)
		);
		event.modifyMatching(item -> item instanceof PikeItem, builder ->
			builder.set(AADataComponents.WEAPON_TRAITS.get(),
				WeaponTraitsComponent.EMPTY
					.withTraitAdded(AAWeaponTraits.TWO_HANDED)
					.withTraitAdded(AAWeaponTraits.LONG_WEAPON)
					.withTraitAdded(AAWeaponTraits.STAB)
					.withTraitAdded(AAWeaponTraits.CAN_BLOCK)
			)
		);
		event.modifyMatching(item -> item instanceof JavelinItem, builder ->
			builder.set(AADataComponents.WEAPON_TRAITS.get(),
				WeaponTraitsComponent.EMPTY
					.withTraitAdded(AAWeaponTraits.JAVELIN_THROW)
			)
		);
		event.modifyMatching(item -> item instanceof ClawItem, builder ->
			builder.set(AADataComponents.WEAPON_TRAITS.get(),
				WeaponTraitsComponent.EMPTY
					.withTraitAdded(AAWeaponTraits.SHORT_WEAPON)
					.withTraitAdded(AAWeaponTraits.LIGHTWEIGHT)
					.withTraitAdded(AAWeaponTraits.DUAL_WIELD)
					.withTraitAdded(AAWeaponTraits.QUICK_ATTACK)
			)
		);
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
