package team.leomc.assortedarmaments.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.client.sound.FlailSpinSoundInstance;
import team.leomc.assortedarmaments.entity.ThrownFlail;
import team.leomc.assortedarmaments.network.RemoveJavelinPayload;
import team.leomc.assortedarmaments.tags.AAItemTags;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = AssortedArmaments.ID, value = Dist.CLIENT)
public class AAClientEvents {
	private static final ResourceLocation FLAIL_CHAIN_TEXTURE = AssortedArmaments.id("textures/entity/flail_chain.png");

	public static final Int2ObjectArrayMap<Vec3> PLAYER_LEFT_HAND_POS = new Int2ObjectArrayMap<>();
	public static final Int2ObjectArrayMap<Vec3> PLAYER_RIGHT_HAND_POS = new Int2ObjectArrayMap<>();
	public static final Int2IntArrayMap FLAIL_LIGHT = new Int2IntArrayMap();

	@Nullable
	private static FlailSpinSoundInstance flailSpinSound;

	@SubscribeEvent
	private static void onClientTick(ClientTickEvent.Post event) {
		Minecraft minecraft = Minecraft.getInstance();
		if (AAClientSetupEvents.KEY_MAPPING_REMOVE_JAVELIN.consumeClick()) {
			if (minecraft.crosshairPickEntity != null) {
				PacketDistributor.sendToServer(new RemoveJavelinPayload(minecraft.crosshairPickEntity.getId()));
			}
		}
		updateFlailSpinSound(minecraft);
	}

	private static void updateFlailSpinSound(Minecraft minecraft) {
		LocalPlayer player = minecraft.player;
		boolean spinning = player != null && player.isUsingItem() && player.getUseItem().is(AAItemTags.FLAILS);
		if (spinning) {
			if (flailSpinSound == null || flailSpinSound.isStopped()) {
				flailSpinSound = new FlailSpinSoundInstance(player);
				minecraft.getSoundManager().play(flailSpinSound);
			}
		} else if (flailSpinSound != null) {
			minecraft.getSoundManager().stop(flailSpinSound);
			flailSpinSound = null;
		}
	}

	@SubscribeEvent
	private static void onRenderLevelStage(RenderLevelStageEvent event) {
		ClientLevel level = Minecraft.getInstance().level;
		EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
		PoseStack stack = event.getPoseStack();
		MultiBufferSource buffer = event.getLevelRenderer().renderBuffers.bufferSource();
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		Vec3 cameraPos = camera.getPosition();

		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
			PLAYER_LEFT_HAND_POS.clear();
			PLAYER_RIGHT_HAND_POS.clear();
			FLAIL_LIGHT.clear();
		}

		if (level != null && event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
			stack.pushPose();
			stack.translate(-cameraPos.x(), -cameraPos.y(), -cameraPos.z());
			float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
			for (Entity entity : level.entitiesForRendering()) {
				if (entity instanceof ThrownFlail flail) {
					Player owner = flail.getPlayerOwner();
					if (owner != null) {
						int hand = owner.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
						if (!owner.getMainHandItem().is(AAItemTags.FLAILS) && owner.getOffhandItem().is(AAItemTags.FLAILS)) {
							hand = -hand;
						}
						boolean rightHand = (owner.getMainArm() == HumanoidArm.RIGHT && owner.getMainHandItem().is(AAItemTags.FLAILS)) || (owner.getMainArm() == HumanoidArm.LEFT && owner.getOffhandItem().is(AAItemTags.FLAILS));
						Vec3 handPos = (rightHand ? PLAYER_RIGHT_HAND_POS : PLAYER_LEFT_HAND_POS).get(owner.getId());
						if (entityRenderDispatcher.options.getCameraType().isFirstPerson() && owner == Minecraft.getInstance().player) {
							Vec3 vec3 = entityRenderDispatcher.camera.getNearPlane().getPointOnPlane(hand * 1.2f, -0.6f).scale(960.0 / (double) entityRenderDispatcher.options.fov().get());
							handPos = owner.getEyePosition(partialTicks).add(vec3);
						}
						if (handPos == null) {
							handPos = new Vec3(Mth.lerp(partialTicks, owner.xo, owner.getX()), Mth.lerp(partialTicks, owner.yo, owner.getY()) + owner.getBbHeight() / 2, Mth.lerp(partialTicks, owner.zo, owner.getZ()));
						}
						Vec3 flailPos = new Vec3(Mth.lerp(partialTicks, entity.xo, entity.getX()), Mth.lerp(partialTicks, entity.yo, entity.getY()) + flail.getBbHeight() / 2, Mth.lerp(partialTicks, entity.zo, entity.getZ()));

						Vec3 offset = handPos.subtract(flailPos);
						float length = (float) (offset.length() + 0.1);
						float width = 0.2F;

						stack.pushPose();
						stack.translate(flailPos.x, flailPos.y, flailPos.z);

						offset = offset.normalize();
						stack.mulPose(Axis.YP.rotationDegrees((float) ((Mth.PI / 2 - Math.atan2(offset.z, offset.x)) * Mth.RAD_TO_DEG)));
						stack.mulPose(Axis.XP.rotationDegrees((float) (Math.acos(offset.y) * Mth.RAD_TO_DEG)));

						VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, RenderType.entityCutoutNoCull(FLAIL_CHAIN_TEXTURE), false, flail.getItem().hasFoil());
						PoseStack.Pose pose = stack.last();
						int light = FLAIL_LIGHT.getOrDefault(flail.getId(), LightTexture.FULL_BRIGHT);

						float start = -1f;
						float end = length / (width * 11f / 4f) - 1;

						width = width / Mth.SQRT_OF_TWO;

						flailVertex(vertexConsumer, pose, -width, length, -width, 1, end, light);
						flailVertex(vertexConsumer, pose, -width, 0, -width, 1, start, light);
						flailVertex(vertexConsumer, pose, width, 0, width, 0, start, light);
						flailVertex(vertexConsumer, pose, width, length, width, 0, end, light);

						flailVertex(vertexConsumer, pose, width, length, -width, 1, end, light);
						flailVertex(vertexConsumer, pose, width, 0, -width, 1, start, light);
						flailVertex(vertexConsumer, pose, -width, 0, width, 0, start, light);
						flailVertex(vertexConsumer, pose, -width, length, width, 0, end, light);

						stack.popPose();
					}
				}
			}
			stack.popPose();
		}
	}

	private static void flailVertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, float x, float y, float z, float uvX, float uvY, int light) {
		vertexConsumer.addVertex(pose, x, y, z).setColor(-1).setUv(uvX, uvY).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0.0F, 1.0F, 0.0F);
	}
}
