package team.leomc.assortedarmaments.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.leomc.assortedarmaments.client.event.AAClientEvents;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin {
	@Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
	private void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
		if (WeaponTraitHelper.hasTrait(AAWeaponTraits.TWO_HANDED, livingEntity.getMainHandItem()) && arm != livingEntity.getMainArm()) {
			ci.cancel();
		}
	}

	@Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
	private void aa$captureHandPosition(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
		Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F).mul(poseStack.last().pose());
		Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
		Vec3 handPos = new Vec3(vec.x() + cameraPos.x, vec.y() + cameraPos.y, vec.z() + cameraPos.z);
		(arm == HumanoidArm.RIGHT ? AAClientEvents.MOB_RIGHT_HAND_POS : AAClientEvents.MOB_LEFT_HAND_POS).put(livingEntity.getId(), handPos);
	}
}
