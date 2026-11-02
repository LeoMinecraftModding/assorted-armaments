package team.leomc.assortedarmaments.event;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.SweepAttackEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.data.AAEnchantments;
import team.leomc.assortedarmaments.effect.ExtractEffect;
import team.leomc.assortedarmaments.effect.SyncopeEffect;
import team.leomc.assortedarmaments.entity.ThrownFlail;
import team.leomc.assortedarmaments.entity.ThrownJavelin;
import team.leomc.assortedarmaments.network.UpdateBlockAbilityPayload;
import team.leomc.assortedarmaments.registry.*;
import team.leomc.assortedarmaments.tags.AAEntityTypeTags;
import team.leomc.assortedarmaments.tags.AAItemTags;
import team.leomc.assortedarmaments.trait.*;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = AssortedArmaments.ID)
public class AACommonEvents {
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
		ItemStack mainHand = living.getMainHandItem();
		if (WeaponTraitHelper.hasTrait(AAWeaponTraits.DUAL_WIELD, mainHand)) {
			living.setItemInHand(InteractionHand.OFF_HAND, mainHand.copy());
		}
	}

	@SubscribeEvent
	private static void onIncomingDamage(LivingIncomingDamageEvent event) {
		if (event.getSource().is(DamageTypes.DRAGON_BREATH)) {
			return;
		}
		LivingEntity victim = event.getEntity();
		if (event.getSource().getDirectEntity() instanceof LivingEntity living) {
			ItemStack attackerWeapon = WeaponTraitHelper.getAttackingWeapon(living);
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.SHOCK, attackerWeapon)) {
				ShockWeaponTrait.onIncomingDamage(event, victim);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STAB, attackerWeapon)) {
				StabWeaponTrait.onIncomingDamage(event, living);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.QUICK_ATTACK, attackerWeapon) || WeaponTraitHelper.hasTrait(AAWeaponTraits.DUAL_WIELD, attackerWeapon)) {
				QuickAttackWeaponTrait.onIncomingDamage(event);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.PARRY, attackerWeapon)) {
				ParryWeaponTrait.onIncomingDamage(event, living, victim, attackerWeapon);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.DRAGON_FIRE, attackerWeapon)) {
				DragonFireWeaponTrait.onIncomingDamage(victim);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.BANE_UNDEAD, attackerWeapon)) {
				BaneUndeadWeaponTrait.onIncomingDamage(event);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.CHILL, attackerWeapon)) {
				ChillWeaponTrait.onIncomingDamage(event);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.HEAT, attackerWeapon)) {
				HeatWeaponTrait.onIncomingDamage(event);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.IGNIT, attackerWeapon)) {
				IgnitWeaponTrait.onIncomingDamage(event);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.KNIGHT, attackerWeapon)) {
				KnightWeaponTrait.onIncomingDamage(event);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.NEPTUNES_MIGHT, attackerWeapon)) {
				NeptunesMightWeaponTrait.onIncomingDamage(event);
			}
		}
		if (ParryWeaponTrait.isParrying(victim) && event.getSource().getDirectEntity() instanceof LivingEntity) {
			event.setAmount(event.getAmount() * (float) (1.0 - AACommonConfig.parryMeleeDamageReduction));
		}
	}

	@SubscribeEvent
	private static void onProjectileImpact(ProjectileImpactEvent event) {
		ParryWeaponTrait.onProjectileImpact(event);
	}

	@SubscribeEvent
	private static void onLivingKnockBack(LivingKnockBackEvent event) {
		ParryWeaponTrait.onKnockback(event);
	}

	@SubscribeEvent
	private static void onPerforationDamage(LivingDamageEvent.Pre event) {
		LivingEntity entity = event.getEntity();
		if (entity.hasEffect(AAEffects.PERFORATION) && event.getNewDamage() > 0.0F) {
			event.setNewDamage(event.getNewDamage() + 1.0F);
		}
		if (GlowWeaponTrait.hasStuckJavelin(entity)) {
			event.setNewDamage(event.getNewDamage() * (float) (1.0 + AACommonConfig.glowStuckDamageBonus));
		}
	}

	@SubscribeEvent
	private static void onLivingDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity.hasEffect(AAEffects.EXTRACT)) {
			ExtractEffect.onDeath(entity);
		}
	}

	@SubscribeEvent
	public static void onPostEntityHurt(LivingDamageEvent.Post event) {
		LivingEntity target = event.getEntity();
		DamageSource source = event.getSource();
		ItemStack weaponItem = source.getWeaponItem();
		if (source.getEntity() instanceof Player attacker && weaponItem != null) {
			if (AAUtils.isFullAttack(attacker) && attacker.onGround()) {
				if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STRONG_SWEEP, weaponItem)) {
					StrongSweepWeaponTrait.performSweepAttack(attacker, attacker.getWeaponItem());
				}
				if (WeaponTraitHelper.hasTrait(AAWeaponTraits.CONCENTRATION, weaponItem)) {
					ConcentrationWeaponTrait.onHit(attacker, target, weaponItem);
				}
			}
		}
		if (source.getDirectEntity() instanceof LivingEntity attacker) {
			ItemStack weapon = WeaponTraitHelper.getAttackingWeapon(attacker);
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.MUSHROOM_GIFT, weapon)) {
				MushroomGiftWeaponTrait.onHit(attacker, target);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.GLOW, weapon)) {
				GlowWeaponTrait.onHit(target);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.CRYSTALLIZE, weapon)) {
				CrystallizeWeaponTrait.onHit(attacker, target);
			}
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.SHIFT_GRAVITY, weapon)) {
				ShiftGravityWeaponTrait.onHit(attacker, target);
			}
		} else if (source.getDirectEntity() instanceof ThrownFlail flail) {
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.SHIFT_GRAVITY, flail.getItem())) {
				ShiftGravityWeaponTrait.launch(target);
			}
		} else if (source.getDirectEntity() instanceof ThrownJavelin javelin) {
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.SHIFT_GRAVITY, javelin.getWeaponItem())) {
				ShiftGravityWeaponTrait.launch(target);
			}
		}
	}

	@SubscribeEvent
	private static void onSweepAttack(SweepAttackEvent event) {
		Player player = event.getEntity();
		if (event.isSweeping()) {
			player.setData(AADataAttachments.NO_INTENTIONAL_SWEEP_ATTACK, true);
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STRONG_SWEEP, player.getWeaponItem())) {
				StrongSweepWeaponTrait.onSweepAttack(event);
			}
		}
	}

	@SubscribeEvent
	private static void onCriticalHit(CriticalHitEvent event) {
		Player player = event.getEntity();
		if (event.isCriticalHit()) {
			ItemStack weapon = player.getWeaponItem();
			Holder<Enchantment> crit = player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(AAEnchantments.CRIT);
			int level = EnchantmentHelper.getItemEnchantmentLevel(crit, weapon);
			if (level > 0) {
				event.setDamageMultiplier(event.getDamageMultiplier() * (1 + level * 0.25F));
			}
		}
	}

	@SubscribeEvent
	private static void onEntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, AAAttributes.CRITICAL_ATTACK_DAMAGE_MULTIPLIER);
	}

	@SubscribeEvent
	private static void onSyncopeApplicable(MobEffectEvent.Applicable event) {
		if (!event.getEntity().level().isClientSide && event.getEffectInstance().is(AAEffects.SYNCOPE) && (event.getEntity().getType().is(AAEntityTypeTags.SYNCOPE_IMMUNE) || event.getEntity().hasEffect(AAEffects.SYNCOPE))) {
			event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
		}
	}

	@SubscribeEvent
	private static void onShieldBlock(LivingShieldBlockEvent event) {
		if (event.getOriginalBlock()) {
			LivingEntity blocker = event.getEntity();
			DamageSource source = event.getDamageSource();
			if (blocker.isUsingItem() && WeaponTraitHelper.hasTrait(AAWeaponTraits.CAN_BLOCK, blocker.getUseItem())) {
				CanBlockWeaponTrait.onShieldBlock(event, blocker);
			}
			if (blocker.isUsingItem() && blocker.getUseItem().is(AAItemTags.HEAVY_SHIELDS)) {
				HeavyBlockingWeaponTrait.onShieldBlock(event, blocker, source);
			}
			Entity directEntity = source.getDirectEntity();
			ItemStack weapon = directEntity instanceof LivingEntity living ? living.getWeaponItem()
				: directEntity instanceof ThrownFlail flail ? flail.getItem()
				: directEntity instanceof ThrownJavelin javelin ? javelin.getItem()
				: ItemStack.EMPTY;
			if (!weapon.isEmpty() && WeaponTraitHelper.hasTrait(AAWeaponTraits.KNOCK, weapon) && blocker instanceof ServerPlayer serverPlayer) {
				KnockWeaponTrait.onShieldBlock(blocker, serverPlayer);
			}
		}
	}

	@SubscribeEvent
	private static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
		if (WeaponTraitHelper.hasTrait(AAWeaponTraits.DUAL_WIELD, event.getItemStack())) {
			if (event.getEntity() instanceof Player player) {
				DualWieldWeaponTrait.offhandAttack(event.getEntity().level(), player, event.getHand(), event.getItemStack().getItem());
			}
		}
	}

	private static boolean hasBlockInKnockbackDirection(LivingEntity living, Vec3 direction) {
		Vec3 center = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
		double horizontalHalf = (Math.abs(direction.x) + Math.abs(direction.z)) * living.getBbWidth() * 0.5;
		Vec3 end = center.add(direction.scale(horizontalHalf + 0.5));
		BlockHitResult hit = living.level().clip(new ClipContext(center, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, living));
		return hit.getType() == HitResult.Type.BLOCK && hit.getDirection().getAxis().isHorizontal();
	}

	@SubscribeEvent
	private static void onPostEntityTick(EntityTickEvent.Post event) {
		Entity entity = event.getEntity();
		if (entity instanceof LivingEntity living && !living.level().isClientSide) {
			MobEffectInstance syncope = living.getEffect(AAEffects.SYNCOPE);
			if (syncope != null && (syncope.isInfiniteDuration() || syncope.getDuration() > SyncopeEffect.MAX_DURATION)) {
				int syncopeAmplifier = syncope.getAmplifier();
				living.removeEffect(AAEffects.SYNCOPE);
				living.addEffect(new MobEffectInstance(AAEffects.SYNCOPE, SyncopeEffect.MAX_DURATION, syncopeAmplifier));
			}
			DragonFireWeaponTrait.onTick(living);
			if (living.hasData(AADataAttachments.PIN_UP_EXPIRE)) {
				int pinUpExpire = living.getData(AADataAttachments.PIN_UP_EXPIRE);
				Vec3 pinUpDirection = living.getData(AADataAttachments.PIN_UP_KNOCKBACK_DIRECTION);
				if (living.tickCount > pinUpExpire) {
					living.removeData(AADataAttachments.PIN_UP_EXPIRE);
					living.removeData(AADataAttachments.PIN_UP_KNOCKBACK_DIRECTION);
				} else if (living.horizontalCollision && pinUpDirection.lengthSqr() > 1.0E-7 && hasBlockInKnockbackDirection(living, pinUpDirection)) {
					living.addEffect(new MobEffectInstance(AAEffects.SYNCOPE, AACommonConfig.pinUpSyncopeDuration));
					living.removeData(AADataAttachments.PIN_UP_EXPIRE);
					living.removeData(AADataAttachments.PIN_UP_KNOCKBACK_DIRECTION);
				}
			}
			if (living instanceof Player player) {
				player.setData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER, player.getData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER) + 1);
				if (!ItemStack.matches(player.getData(AADataAttachments.LAST_OFFHAND_ITEM), player.getOffhandItem())) {
					if (!ItemStack.isSameItem(player.getData(AADataAttachments.LAST_OFFHAND_ITEM), player.getOffhandItem())) {
						player.setData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER, 0);
					}
					player.setData(AADataAttachments.LAST_OFFHAND_ITEM, living.getOffhandItem().copy());
				}
			}
			if (living.getData(AADataAttachments.GRAVITY_LAUNCH)) {
				living.setData(AADataAttachments.GRAVITY_LAUNCH, false);
				living.push(0.0, 1.0, 0.0);
				if (living instanceof ServerPlayer serverPlayer) {
					serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
				}
			}
			if (living instanceof ServerPlayer serverPlayer) {
				if (living.getData(AADataAttachments.BLOCKING_DISABLED_TIME) == 1) {
					PacketDistributor.sendToPlayer(serverPlayer, new UpdateBlockAbilityPayload(false));
				}
				living.setData(AADataAttachments.BLOCKING_DISABLED_TIME, Math.max(living.getData(AADataAttachments.BLOCKING_DISABLED_TIME) - 1, 0));
				living.setData(AADataAttachments.NO_INTENTIONAL_SWEEP_ATTACK, false);
			}
			CanBlockWeaponTrait.onTick(living);
			HeavyBlockingWeaponTrait.onTick(living);
			LightweightWeaponTrait.onTick(living);
			if (living.getData(AADataAttachments.CONCENTRATION_LEVEL) > 0) {
				ConcentrationWeaponTrait.onTick(living);
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
						event.addModifier(attr.attribute(), attr.createModifier(traitId), attr.slotGroup());
					}
				}
			}
		}

		WeaponTraitHelper.forEachTrait(stack, trait -> trait.onItemAttributeModifier(event));
	}

	@SubscribeEvent
	public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
		WeaponTraitHelper.forEachTrait(event.getEntity().getWeaponItem(), trait -> trait.onMobEffectApplicable(event));
	}

	@SubscribeEvent
	public static void onEntityInvulnerabilityCheck(EntityInvulnerabilityCheckEvent event) {
		if (!(event.getEntity() instanceof LivingEntity living)) return;
		WeaponTraitHelper.forEachTrait(living.getWeaponItem(), trait -> trait.onEntityInvulnerabilityCheck(event));
	}

	@SubscribeEvent
	public static void onSwapHands(LivingSwapItemsEvent.Hands event) {
		TwoHandedWeaponTrait.onSwapHands(event);
	}
}
