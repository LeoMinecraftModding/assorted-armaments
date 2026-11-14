package team.leomc.assortedarmaments.trait;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.SweepAttackEvent;
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

public class StrongSweepWeaponTrait extends WeaponTrait {
	public static void onSweepAttack(SweepAttackEvent event) {
		event.setSweeping(false);
	}

	public static void performSweepAttack(LivingEntity attacker) {
		ItemStack stack = WeaponTraitHelper.getAttackingWeapon(attacker);
		if (!WeaponTraitHelper.hasTrait(AAWeaponTraits.STRONG_SWEEP, stack)
			|| !AAUtils.isFullAttack(attacker)
			|| !attacker.onGround()
			|| attacker.getData(AADataAttachments.SWEEP_ATTACK_ACTIVE)) {
			return;
		}
		attacker.setData(AADataAttachments.SWEEP_ATTACK_ACTIVE, true);

		Level level = attacker.level();
		DamageSource source;
		if (attacker instanceof Player player) {
			source = level.damageSources().playerAttack(player);
		} else {
			source = level.damageSources().mobAttack(attacker);
		}

		AttributeInstance attackDamage = attacker.getAttribute(Attributes.ATTACK_DAMAGE);
		AttributeInstance sweepingDamageRatio = attacker.getAttribute(Attributes.SWEEPING_DAMAGE_RATIO);
		float damage = (float) ((attackDamage == null ? 0.0 : attackDamage.getValue()) * (0.75 + (sweepingDamageRatio == null ? 0.0 : sweepingDamageRatio.getValue())));

		AttributeInstance entityInteractionRange = attacker.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
		double reach = entityInteractionRange == null ? 3.0 : entityInteractionRange.getValue();

		for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(reach))) {
			if (living != attacker
				&& !attacker.isAlliedTo(living)
				&& (!(living instanceof ArmorStand) || !((ArmorStand) living).isMarker())
				&& attacker.distanceToSqr(living) < reach * reach
				&& living.position().subtract(attacker.position()).normalize().dot(attacker.getViewVector(1).normalize()) > 0) {
				if (level instanceof ServerLevel serverLevel) {
					damage = EnchantmentHelper.modifyDamage(serverLevel, stack, living, source, damage);
				}
				living.knockback(0.4F + (attacker.isSprinting() ? 1.0F : 0.0F), Mth.sin(attacker.getYRot() * (float) (Math.PI / 180.0)), (-Mth.cos(attacker.getYRot() * (float) (Math.PI / 180.0))));
				living.hurt(source, damage);
				if (level instanceof ServerLevel serverLevel) {
					EnchantmentHelper.doPostAttackEffects(serverLevel, living, source);
				}
				stack.hurtAndBreak(1, attacker, attacker.getEquipmentSlotForItem(stack));
			}
		}

		level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, attacker.getSoundSource(), 1.0F, 1.0F);
		if (attacker instanceof Player player) {
			player.sweepAttack();
		} else if (level instanceof ServerLevel serverLevel) {
			double dx = -Mth.sin(attacker.getYRot() * (float) (Math.PI / 180.0));
			double dz = Mth.cos(attacker.getYRot() * (float) (Math.PI / 180.0));
			serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, attacker.getX() + dx, attacker.getY(0.5), attacker.getZ() + dz, 0, dx, 0.0, dz, 0.0);
		}

		attacker.setData(AADataAttachments.SWEEP_ATTACK_ACTIVE, false);
	}
}
