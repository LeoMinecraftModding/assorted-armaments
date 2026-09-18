package team.leomc.assortedarmaments.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.tags.AAItemTags;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;

@Mixin(Player.class)
public abstract class PlayerMixin {
	@Shadow
	public abstract ItemStack getWeaponItem();

	@Unique
	private float aa$originalAttackDamage;

	@Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"))
	private void captureOriginalDamage(Entity target, CallbackInfo ci, @Local(ordinal = 0) float originalDamage) {
		aa$originalAttackDamage = originalDamage;
	}

	@ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getEnchantedDamage(Lnet/minecraft/world/entity/Entity;FLnet/minecraft/world/damagesource/DamageSource;)F", ordinal = 1), index = 1)
	private float modifySweepDamage(float damage) {
		if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STRONG_SWEEP, getWeaponItem())) {
			return (float) (aa$originalAttackDamage * (1 + ((Player) (Object) this).getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) * 0.1));
		}
		return damage;
	}

	@WrapOperation(method = "blockUsingShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;canDisableShield()Z"))
	private boolean canDisableShield(LivingEntity instance, Operation<Boolean> original) {
		if (getWeaponItem().is(AAItemTags.HEAVY_SHIELDS)) {
			return false;
		}
		return original.call(instance);
	}

	@Inject(method = "getWeaponItem", at = @At("RETURN"), cancellable = true)
	private void getWeaponItem(CallbackInfoReturnable<ItemStack> cir) {
		Player player = (Player) (Object) this;
		if (player.getData(AADataAttachments.OFFHAND_ATTACK)) {
			cir.setReturnValue(player.getOffhandItem());
		}
	}

	@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack useOffhandWeapon(Player instance, InteractionHand hand, Operation<ItemStack> original) {
		if (hand == InteractionHand.MAIN_HAND && instance.getData(AADataAttachments.OFFHAND_ATTACK)) {
			return original.call(instance, InteractionHand.OFF_HAND);
		}
		return original.call(instance, hand);
	}

	@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttackStrengthScale(F)F"))
	private float useOffhandAttackStrengthTimer(Player instance, float f, Operation<Float> original) {
		if (instance.getData(AADataAttachments.OFFHAND_ATTACK)) {
			return Mth.clamp((instance.getData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER) + f) / instance.getCurrentItemAttackStrengthDelay(), 0.0F, 1.0F);
		}
		return original.call(instance, f);
	}

	@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;resetAttackStrengthTicker()V"))
	private void resetOffhandAttackStrengthTimer(Player instance, Operation<Void> original) {
		if (instance.getData(AADataAttachments.OFFHAND_ATTACK)) {
			instance.setData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER, 0);
		} else {
			original.call(instance);
		}
	}
}
