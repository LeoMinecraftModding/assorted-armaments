package team.leomc.assortedarmaments.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.leomc.assortedarmaments.client.event.AAClientEvents;
import team.leomc.assortedarmaments.entity.ThrownFlail;

@OnlyIn(Dist.CLIENT)
public class ThrownFlailRenderer extends EntityRenderer<ThrownFlail> {
	private final ItemRenderer itemRenderer;

	public ThrownFlailRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(ThrownFlail entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		poseStack.translate(0, entity.getBbHeight() / 2, 0);
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 90.0F));
		ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(entity.getItem().getItem());
		ModelResourceLocation model = ModelResourceLocation.standalone(itemId.withPrefix("item/").withSuffix("_thrown"));
		if (Minecraft.getInstance().getModelManager().getModelBakery().getBakedTopLevelModels().containsKey(model)) {
			this.itemRenderer.render(entity.getItem(), ItemDisplayContext.GROUND, false, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, Minecraft.getInstance().getModelManager().getModelBakery().getBakedTopLevelModels().get(model));
		}
		poseStack.popPose();
		AAClientEvents.FLAIL_LIGHT.put(entity.getId(), packedLight);
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	public boolean shouldRender(ThrownFlail flail, Frustum camera, double camX, double camY, double camZ) {
		return flail.position().distanceTo(new Vec3(camX, camY, camZ)) < 1024;
	}

	@Override
	public ResourceLocation getTextureLocation(ThrownFlail thrownFlail) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
