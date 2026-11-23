package team.leomc.assortedarmaments.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.common.ItemAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.data.AAEnchantments;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.tags.AAItemTags;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;

import javax.annotation.Nonnull;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow
	@Nonnull
	public abstract ItemStack getWeaponItem();

	@Inject(method = "getKnockback", at = @At("RETURN"), cancellable = true)
	private void getKnockback(Entity target, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
		if (getWeaponItem().is(AAItemTags.EXTRA_KNOCKBACK)) {
			cir.setReturnValue(cir.getReturnValue() + 1);
		}
	}

	@Inject(method = "swing*", at = @At("HEAD"))
	private void swing(InteractionHand hand, CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;
		ItemStack stack = entity.getItemInHand(hand);

		aa$grantParryOnSwing(entity, hand, stack);
	}

	@Unique
	private static void aa$grantParryOnSwing(LivingEntity entity, InteractionHand hand, ItemStack stack) {
		if (entity.level().isClientSide) return;
		if (!(entity instanceof Player player) || !WeaponTraitHelper.hasTrait(AAWeaponTraits.PARRY, stack) || !AAUtils.isFullAttack(player, hand)) return;
		AAUtils.grantParry(player, stack, player, player.damageSources().generic());
	}

	@WrapOperation(
		method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 4))
	private boolean extendInvulnerabilityBypass(DamageSource source, TagKey<?> tag, Operation<Boolean> original) {
		if (original.call(source, tag)) {
			return true;
		}
		return source.getWeaponItem() != null && WeaponTraitHelper.hasTrait(AAWeaponTraits.SEE_THROUGH, source.getWeaponItem());
	}

	@Inject(method = "isBlocking", at = @At("RETURN"), cancellable = true)
	private void quickDefenseBlocking(CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue()) return;

		LivingEntity living = (LivingEntity) (Object) this;
		ItemStack useItem = living.getUseItem();

		if (!living.isUsingItem()) return;
		if (!useItem.is(AAItemTags.HEAVY_SHIELDS) || !useItem.canPerformAction(ItemAbilities.SHIELD_BLOCK)) return;

		Holder<Enchantment> enchantment = living.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(AAEnchantments.QUICK_DEFENSE);
		int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, useItem);
		if (level > 0 && living.getTicksUsingItem() >= Math.max(0, 5 - level)) {
			cir.setReturnValue(true);
		}
	}
}
