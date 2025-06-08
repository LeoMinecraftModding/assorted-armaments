package team.leomc.assortedarmaments.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
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
import team.leomc.assortedarmaments.tags.AAItemTags;

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
		if (getWeaponItem().is(AAItemTags.STRONG_SWEEP)) {
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
}
