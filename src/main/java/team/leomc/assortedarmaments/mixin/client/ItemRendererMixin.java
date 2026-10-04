package team.leomc.assortedarmaments.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.leomc.assortedarmaments.data.AAEnchantments;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.tags.AAItemTags;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
	private void render(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel bakedModel, CallbackInfo ci) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null && itemStack.is(AAItemTags.FLAILS) && player.isUsingItem() && player.getUseItem() == itemStack && (displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)) {
		float power = player.getData(AADataAttachments.FLAIL_KINETIC_POWER);
		int initialVelocity = 0;
		Level level = Minecraft.getInstance().level;
		if (level != null) {
			initialVelocity = itemStack.getEnchantmentLevel(level.registryAccess().holderOrThrow(AAEnchantments.INITIAL_VELOCITY));
		}
		float initialBonus = 0.5F * initialVelocity;
		power = Math.max(power, initialBonus);
		float ticks = player.getTicksUsingItem() + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()) + initialBonus * 20.0F;
		float angle = ticks <= 100.0F ? (Mth.PI / 10F) * ticks * (0.5F + 0.25F * power) : (Mth.PI / 10F) * (3.0F * ticks - 125.0F);
		poseStack.mulPose(new Quaternionf().rotateY(angle));
		}
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void renderHead(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel bakedModel, CallbackInfo ci) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null
			&& itemStack.is(AAItemTags.FLAILS)
			&& (player.getMainHandItem() == itemStack || player.getOffhandItem() == itemStack)
			&& player.hasData(AADataAttachments.FLAIL)
			&& displayContext != ItemDisplayContext.GUI) {
			ci.cancel();
		}
	}
}
