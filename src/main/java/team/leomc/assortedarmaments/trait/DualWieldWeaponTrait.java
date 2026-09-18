package team.leomc.assortedarmaments.trait;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

import java.util.List;

public class DualWieldWeaponTrait extends WeaponTrait{
	public static void offhandAttack (Level level, Player player, InteractionHand hand, Item item) {
		if (hand == InteractionHand.OFF_HAND && player.getMainHandItem().is(item)) {
			HitResult result = pick(player, player.blockInteractionRange(), player.entityInteractionRange());
			if (result instanceof EntityHitResult entityResult && entityResult.getType() != HitResult.Type.MISS) {
				player.setData(AADataAttachments.OFFHAND_ATTACK, true);
				player.attack(entityResult.getEntity());
				player.setData(AADataAttachments.OFFHAND_ATTACK, false);
			}
			player.setData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER, 0);
			player.swing(InteractionHand.OFF_HAND);
			player.awardStat(Stats.ITEM_USED.get(item));
		}
	}

	// copied from GameRenderer
	protected static HitResult pick(Entity entity, double blockInteractionRange, double entityInteractionRange) {
		double maxRange = Math.max(blockInteractionRange, entityInteractionRange);
		double maxRangeSqr = Mth.square(maxRange);
		Vec3 eyePos = entity.getEyePosition();
		HitResult pickResult = entity.pick(maxRange, 1, false);
		double dist = pickResult.getLocation().distanceToSqr(eyePos);
		if (pickResult.getType() != HitResult.Type.MISS) {
			maxRangeSqr = dist;
			maxRange = Math.sqrt(dist);
		}
		Vec3 viewVector = entity.getViewVector(1);
		Vec3 endPos = eyePos.add(viewVector.x * maxRange, viewVector.y * maxRange, viewVector.z * maxRange);
		AABB aabb = entity.getBoundingBox().expandTowards(viewVector.scale(maxRange)).inflate(1.0, 1.0, 1.0);
		EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
			entity, eyePos, endPos, aabb, e -> !e.isSpectator() && e.isPickable(), maxRangeSqr
		);
		return entityHitResult != null && entityHitResult.getLocation().distanceToSqr(eyePos) < dist
			? filterHitResult(entityHitResult, eyePos, entityInteractionRange)
			: filterHitResult(pickResult, eyePos, blockInteractionRange);
	}

	private static HitResult filterHitResult(HitResult hitResult, Vec3 pos, double blockInteractionRange) {
		Vec3 location = hitResult.getLocation();
		if (!location.closerThan(pos, blockInteractionRange)) {
			Direction direction = Direction.getNearest(location.x - pos.x, location.y - pos.y, location.z - pos.z);
			return BlockHitResult.miss(location, direction, BlockPos.containing(location));
		} else {
			return hitResult;
		}
	}

	public static int onModifyPostAttackInvulnerabilityTicks(LivingEntity entity, DamageSource source, float amount, int ticks) {
		if (source.isDirect() && source.getDirectEntity() != null) {
			ItemStack weapon = source.getDirectEntity().getWeaponItem();
			if (weapon != null && WeaponTraitHelper.hasTrait(AAWeaponTraits.DUAL_WIELD, weapon)) {
				return Math.min(15, ticks);
			}
		}
		return ticks;
	}

	private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE = ResourceLocation.withDefaultNamespace("hud/crosshair_attack_indicator_full");
	private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("hud/crosshair_attack_indicator_background");
	private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("hud/crosshair_attack_indicator_progress");
	private static final ResourceLocation HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("hud/hotbar_attack_indicator_background");
	private static final ResourceLocation HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("hud/hotbar_attack_indicator_progress");

	public static void renderOffhandAttackIndicator(GuiGraphics guiGraphics) {
		RenderSystem.enableBlend();
		if (Minecraft.getInstance().player != null) {
			ItemStack mainHand = Minecraft.getInstance().player.getMainHandItem();
			ItemStack offhand = Minecraft.getInstance().player.getOffhandItem();
			if (ItemStack.isSameItem(mainHand, offhand) && WeaponTraitHelper.hasTrait(AAWeaponTraits.DUAL_WIELD, mainHand)) {
				float attackStrengthScale = Mth.clamp(Minecraft.getInstance().player.getData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER) / Minecraft.getInstance().player.getCurrentItemAttackStrengthDelay(), 0.0F, 1.0F);
				if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && Minecraft.getInstance().options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
					RenderSystem.blendFuncSeparate(
						GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
						GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
						GlStateManager.SourceFactor.ONE,
						GlStateManager.DestFactor.ZERO
					);
					boolean full = false;
					if (Minecraft.getInstance().crosshairPickEntity != null && Minecraft.getInstance().crosshairPickEntity instanceof LivingEntity && attackStrengthScale >= 1.0F) {
						full = Minecraft.getInstance().player.getCurrentItemAttackStrengthDelay() > 5.0F;
						full &= Minecraft.getInstance().crosshairPickEntity.isAlive();
					}
					int x = guiGraphics.guiWidth() / 2 - 8;
					// +16 -> +24 we want it to be under the normal crosshair
					int y = guiGraphics.guiHeight() / 2 - 7 + 24;
					if (full) {
						guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE, x, y, 16, 16);
					} else if (attackStrengthScale < 1.0F) {
						int progress = (int) (attackStrengthScale * 17.0F);
						guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE, x, y, 16, 4);
						guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE, 16, 4, 0, 0, x, y, progress, 4);
					}
					RenderSystem.defaultBlendFunc();
				}
				if (Minecraft.getInstance().options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
					HumanoidArm arm = Minecraft.getInstance().player.getMainArm();
					if (attackStrengthScale < 1.0F) {
						int halfWidth = guiGraphics.guiWidth() / 2;
						// we want it to be on the other side of the hotbar
						// offset by 29 so that it won't be under the offhand slot
						int x = halfWidth + 91 + 6 + 29;
						if (arm == HumanoidArm.RIGHT) {
							x = halfWidth - 91 - 22 - 29;
						}
						int y = guiGraphics.guiHeight() - 20;
						int progress = (int) (attackStrengthScale * 19.0F);
						guiGraphics.blitSprite(HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE, x, y, 18, 18);
						guiGraphics.blitSprite(HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE, 18, 18, 0, 18 - progress, x, y + 18 - progress, 18, progress);
					}
				}
			}
		}
		RenderSystem.disableBlend();
	}
}
