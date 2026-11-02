package team.leomc.assortedarmaments.mixin.client;

import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.leomc.assortedarmaments.tags.AAItemTags;

@OnlyIn(Dist.CLIENT)
@Mixin(AbstractZombieModel.class)
public abstract class AbstractZombieModelMixin {
	@Inject(method = "setupAnim", at = @At("RETURN"))
	private void aa$raiseFlailArm(Monster entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if (!entity.isUsingItem() || !entity.getUseItem().is(AAItemTags.FLAILS)) {
			return;
		}
		HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;
		boolean rightArm = (entity.getUsedItemHand() == InteractionHand.MAIN_HAND) == (entity.getMainArm() == HumanoidArm.RIGHT);
		ModelPart arm = rightArm ? model.rightArm : model.leftArm;
		arm.xRot = -Mth.PI;
		arm.yRot = 0.0F;
		arm.zRot = 0.0F;
	}
}
