package team.leomc.assortedarmaments.trait;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.tags.AAItemTags;

public class HeavyBlockingWeaponTrait extends WeaponTrait {
	public static void onShieldBlock(LivingShieldBlockEvent event, LivingEntity blocker, DamageSource source) {
		ItemStack shield = blocker.getUseItem();
		if (blocker.getTicksUsingItem() <= AACommonConfig.heavyShieldFastBlockTime && source.getDirectEntity() instanceof LivingEntity attacker) {
			float damage = event.getDamageContainer().getNewDamage();
			DamageSource damageSource = blocker instanceof Player player ? blocker.damageSources().playerAttack(player) : blocker.damageSources().mobAttack(blocker);
			blocker.setData(AADataAttachments.PARRY_WEAPON, shield);
			if (attacker.hurt(damageSource, (float) (damage * AACommonConfig.heavyShieldFastBlockDamageReflectionPercentage)) && blocker.level() instanceof ServerLevel serverLevel) {
				EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, attacker, damageSource, shield);
			}
			blocker.setData(AADataAttachments.PARRY_WEAPON, ItemStack.EMPTY);
		}
	}

	public static void onTick(LivingEntity living) {
		AttributeInstance speedInstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
		if (speedInstance != null) {
			AttributeModifier modifier = getSpeedModifier();
			if (living.isUsingItem() && living.getUseItem().is(AAItemTags.HEAVY_SHIELDS)) {
				if (!speedInstance.hasModifier(modifier.id())) {
					speedInstance.addPermanentModifier(modifier);
				}
			} else if (speedInstance.hasModifier(modifier.id())) {
				speedInstance.removeModifier(modifier.id());
			}
		}
		AttributeInstance damageInstance = living.getAttribute(Attributes.ATTACK_DAMAGE);
		if (damageInstance != null) {
			AttributeModifier modifier = getDamageModifier();
			if (living.isUsingItem() && living.getUseItem().is(AAItemTags.HEAVY_SHIELDS)) {
				if (!damageInstance.hasModifier(modifier.id())) {
					damageInstance.addPermanentModifier(modifier);
				}
			} else if (damageInstance.hasModifier(modifier.id())) {
				damageInstance.removeModifier(modifier.id());
			}
		}
	}

	public static AttributeModifier getSpeedModifier() {
		return new AttributeModifier(AssortedArmaments.id("heavy_shield_block_speed"), -AACommonConfig.heavyShieldBlockWalkSpeedModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	public static AttributeModifier getDamageModifier() {
		return new AttributeModifier(AssortedArmaments.id("heavy_shield_block_damage"), -AACommonConfig.heavyShieldBlockAttackDamageModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
}
