package team.leomc.assortedarmaments.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.tags.AAItemTags;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;

import java.util.EnumSet;

public class FlailSpinGoal extends Goal {
	private static final double MAX_START_DISTANCE_SQR = 14.0 * 14.0;
	private static final double THROW_MIN_DISTANCE = 2.5;
	private static final double THROW_MAX_DISTANCE = 10.0;
	private static final float THROW_KINETIC_POWER = 5.0F;
	private static final double CHASE_SPEED = 1.0;

	private final Mob mob;

	public FlailSpinGoal(Mob mob) {
		this.mob = mob;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		LivingEntity target = this.mob.getTarget();
		if (target == null || !target.isAlive()) return false;
		if (this.mob.isUsingItem() || this.mob.hasData(AADataAttachments.FLAIL)) return false;
		if (!this.mob.hasLineOfSight(target)) return false;

		ItemStack stack = this.mob.getMainHandItem();
		if (!stack.is(AAItemTags.FLAILS) || !WeaponTraitHelper.hasTrait(AAWeaponTraits.FLAIL_SPIN, stack)) return false;

		return this.mob.distanceToSqr(target) <= MAX_START_DISTANCE_SQR;
	}

	@Override
	public void start() {
		ItemStack stack = this.mob.getMainHandItem();
		stack.hurtAndBreak(3, this.mob, EquipmentSlot.MAINHAND);
		if (!stack.isEmpty()) {
			this.mob.startUsingItem(InteractionHand.MAIN_HAND);
		}
	}

	@Override
	public boolean canContinueToUse() {
		if (!this.mob.isUsingItem() || this.mob.hasData(AADataAttachments.FLAIL)) return false;
		if (!this.mob.getMainHandItem().is(AAItemTags.FLAILS)) return false;

		LivingEntity target = this.mob.getTarget();
		return target != null && target.isAlive();
	}

	@Override
	public void tick() {
		LivingEntity target = this.mob.getTarget();
		if (target == null) return;

		this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
		double distance = this.mob.distanceTo(target);
		if (distance > THROW_MIN_DISTANCE) {
			this.mob.getNavigation().moveTo(target, CHASE_SPEED);
		} else {
			this.mob.getNavigation().stop();
		}

		float kineticPower = this.mob.getData(AADataAttachments.FLAIL_KINETIC_POWER);
		if (kineticPower >= THROW_KINETIC_POWER && distance <= THROW_MAX_DISTANCE) {
			aimAtTarget(target);
			this.mob.releaseUsingItem();
		}
	}

	private void aimAtTarget(LivingEntity target) {
		Vec3 eye = this.mob.getEyePosition();
		double dx = target.getX() - eye.x;
		double dy = target.getY() + target.getBbHeight() * 0.5 - eye.y;
		double dz = target.getZ() - eye.z;
		double horizontal = Math.sqrt(dx * dx + dz * dz);
		this.mob.setYRot((float) (Mth.atan2(dz, dx) * Mth.RAD_TO_DEG) - 90.0F);
		this.mob.setXRot((float) (-(Mth.atan2(dy, horizontal) * Mth.RAD_TO_DEG)));
		this.mob.setYHeadRot(this.mob.getYRot());
	}

	@Override
	public void stop() {
		if (this.mob.isUsingItem()) {
			this.mob.stopUsingItem();
		}
		this.mob.removeData(AADataAttachments.FLAIL_KINETIC_POWER);
		this.mob.getNavigation().stop();
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}
}
