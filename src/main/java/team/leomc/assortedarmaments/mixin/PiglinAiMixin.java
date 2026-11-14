package team.leomc.assortedarmaments.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
	@Inject(method = "isWearingGold", at = @At("HEAD"), cancellable = true)
	private static void aa$glitteringMakesPiglinsNeutral(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
		if (WeaponTraitHelper.hasTrait(AAWeaponTraits.GLITTERING, livingEntity.getMainHandItem())
			|| WeaponTraitHelper.hasTrait(AAWeaponTraits.GLITTERING, livingEntity.getOffhandItem())) {
			cir.setReturnValue(true);
		}
	}
}
