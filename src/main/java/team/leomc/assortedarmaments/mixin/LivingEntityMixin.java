package team.leomc.assortedarmaments.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AADataComponents;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.tags.AAItemTags;
import team.leomc.assortedarmaments.trait.DualWieldWeaponTrait;
import team.leomc.assortedarmaments.trait.StrongSweepWeaponTrait;
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

		if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STRONG_SWEEP, stack)) {
			if (!entity.getData(AADataAttachments.NO_INTENTIONAL_SWEEP_ATTACK) && entity instanceof Player player && player.getAttackStrengthScale(0.5F) > 0.9f && player.onGround()) {
				if (StrongSweepWeaponTrait.performSweepAttack(player, stack)) {
					stack.hurtAndBreak(1, entity, entity.getEquipmentSlotForItem(stack));
				}
			}
		}
	}

	@WrapOperation(
		method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 4))
	private boolean extendInvulnerabilityBypass(DamageSource source, TagKey<?> tag, Operation<Boolean> original) {
		if (original.call(source, tag)) {
			return true;
		}
		return source.getWeaponItem() != null && WeaponTraitHelper.hasTrait(AAWeaponTraits.SEE_THROUGH, source.getWeaponItem());
	}

	@Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", shift = At.Shift.AFTER))
	private void modifyPostAttackInvulnerabilityTicks(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity living = (LivingEntity) (Object) this;
		living.invulnerableTime = DualWieldWeaponTrait.onModifyPostAttackInvulnerabilityTicks(living, source, amount, living.invulnerableTime);
	}
}
