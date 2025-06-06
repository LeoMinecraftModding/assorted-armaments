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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.leomc.assortedarmaments.entity.ThrownJavelin;

@OnlyIn(Dist.CLIENT)
public class ThrownJavelinRenderer extends EntityRenderer<ThrownJavelin> {
	private final ItemRenderer itemRenderer;

	public ThrownJavelinRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(ThrownJavelin entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
		float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
		float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
		float yawOffset = 0;
		float offset = 0;
		if (entity.isInTarget()) {
			Entity target = entity.level().getEntity(entity.getTargetId());
			if (target != null) {
				float wantedX = (float) Mth.lerp(partialTicks, target.xOld, target.getX());
				float wantedY = (float) Mth.lerp(partialTicks, target.yOld, target.getY()) + target.getBbHeight() / 2;
				float wantedZ = (float) Mth.lerp(partialTicks, target.zOld, target.getZ());
				poseStack.translate(wantedX - x, wantedY - y, wantedZ - z);
				yawOffset = Mth.lerp(partialTicks, target.yRotO, target.getYRot());
				offset = target.getBbWidth() / 2.25f;
				if (target instanceof LivingEntity living) {
					yawOffset = living.getPreciseBodyRotation(partialTicks);
				}
			}
		}
		poseStack.translate(0, entity.getBbHeight() / 2, 0);
		poseStack.mulPose(Axis.YP.rotationDegrees(entity.isInTarget() ? entity.getFixedYaw() - yawOffset : Mth.lerp(partialTicks, entity.yRotO, entity.getYRot())));
		poseStack.mulPose(Axis.XP.rotationDegrees(entity.isInTarget() ? -entity.getFixedPitch() : -Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
		poseStack.translate(0, 0.4375, -offset);
		ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(entity.getItem().getItem());
		ModelResourceLocation model = ModelResourceLocation.standalone(itemId.withPrefix("item/").withSuffix("_thrown"));
		if (Minecraft.getInstance().getModelManager().getModelBakery().getBakedTopLevelModels().containsKey(model)) {
			this.itemRenderer.render(entity.getItem(), ItemDisplayContext.GROUND, false, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, Minecraft.getInstance().getModelManager().getModelBakery().getBakedTopLevelModels().get(model));
		}
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	public boolean shouldRender(ThrownJavelin javelin, Frustum camera, double camX, double camY, double camZ) {
		return javelin.position().distanceTo(new Vec3(camX, camY, camZ)) < 1024;
	}

	@Override
	public ResourceLocation getTextureLocation(ThrownJavelin thrownJavelin) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
