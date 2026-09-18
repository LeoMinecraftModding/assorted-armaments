package team.leomc.assortedarmaments.trait;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

public class StrongSweepWeaponTrait extends WeaponTrait{
	public static boolean performSweepAttack(Player player, ItemStack stack) {
		boolean success = false;

		Level level = player.level();
		DamageSource source = level.damageSources().playerAttack(player);
		float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE));

		double reach = player.entityInteractionRange();
		for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(reach))) {
			if (living != player
				&& !player.isAlliedTo(living)
				&& (!(living instanceof ArmorStand) || !((ArmorStand) living).isMarker())
				&& player.distanceToSqr(living) < reach * reach
				&& living.position().subtract(player.position()).normalize().dot(player.getViewVector(1).normalize()) > 0) {
				if (level instanceof ServerLevel serverLevel) {
					damage = EnchantmentHelper.modifyDamage(serverLevel, stack, living, source, damage);
				}
				if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STRONG_SWEEP, stack)) {
					damage = (float) (damage * (1 + (player.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) * 0.1)));
				}
				living.knockback(0.4F + (player.isSprinting() ? 1.0F : 0.0F), Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)), (-Mth.cos(player.getYRot() * (float) (Math.PI / 180.0))));
				living.hurt(source, damage);
				if (level instanceof ServerLevel serverLevel) {
					EnchantmentHelper.doPostAttackEffects(serverLevel, living, source);
				}
				stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
				success = true;
			}
		}

		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
		player.sweepAttack();

		return success;
	}
}
