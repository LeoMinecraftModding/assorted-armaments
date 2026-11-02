package team.leomc.assortedarmaments.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
	@WrapOperation(method = "swing", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;resetAttackStrengthTicker()V"))
	private void keepMainHandAttackStrengthOnOffhandSwing(ServerPlayer instance, Operation<Void> original, @Local(argsOnly = true, ordinal = 0) InteractionHand hand) {
		if (hand == InteractionHand.OFF_HAND
			&& (WeaponTraitHelper.hasTrait(AAWeaponTraits.DUAL_WIELD, instance.getMainHandItem())
			|| WeaponTraitHelper.hasTrait(AAWeaponTraits.DUAL_WIELD, instance.getOffhandItem()))) {
			return;
		}
		original.call(instance);
	}
}
