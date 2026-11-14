package team.leomc.assortedarmaments.trait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.registry.AADataAttachments;

public class ParryWeaponTrait extends WeaponTrait {
	@Override
	public Object[] descriptionArgs() {
		return new Object[]{Component.literal(String.valueOf(AACommonConfig.parryMeleeDamageReduction * 100)).withStyle(ChatFormatting.DARK_GREEN)};
	}

	public static void onIncomingDamage(LivingIncomingDamageEvent event, LivingEntity attacker, LivingEntity victim, ItemStack weapon) {
		if (AAUtils.isFullAttack(attacker)) {
			AAUtils.grantParry(attacker, weapon, victim, event.getSource());
		}
	}

	public static boolean isParrying(LivingEntity entity) {
		Long parryExpire = entity.getExistingDataOrNull(AADataAttachments.PARRY_EXPIRE);
		return parryExpire != null && entity.level().getGameTime() < parryExpire;
	}

	public static void onProjectileImpact(ProjectileImpactEvent event) {
		if (!(event.getRayTraceResult() instanceof EntityHitResult entityHit) || !(entityHit.getEntity() instanceof LivingEntity entity)) {
			return;
		}
		Projectile projectile = event.getProjectile();
		if (!canSplitAirBlock(entity, projectile)) {
			return;
		}
		event.setCanceled(true);
		projectile.deflect(ProjectileDeflection.REVERSE, entity, projectile.getOwner(), false);
		projectile.setDeltaMovement(projectile.getDeltaMovement().scale(0.2));
	}

	private static boolean canSplitAirBlock(LivingEntity entity, Projectile projectile) {
		if (!isParrying(entity) || !AAUtils.hasSplitAir(entity)) {
			return false;
		}
		if (projectile instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0) {
			return false;
		}
		Vec3 viewVector = entity.calculateViewVector(0.0F, entity.getYHeadRot());
		Vec3 toEntity = projectile.position().vectorTo(entity.position());
		toEntity = new Vec3(toEntity.x, 0.0, toEntity.z).normalize();
		return toEntity.dot(viewVector) < 0.0;
	}

	public static void onKnockback(LivingKnockBackEvent event) {
		if (isParrying(event.getEntity())) {
			event.setCanceled(true);
		}
	}
}
