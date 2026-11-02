package team.leomc.assortedarmaments.trait;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

public class ConcentrationWeaponTrait extends WeaponTrait {
	public static void onHit(Player attacker, LivingEntity target, ItemStack weapon) {
		if (target == attacker.getData(AADataAttachments.CONCENTRATED_TARGET) && weapon == attacker.getData(AADataAttachments.CONCENTRATED_WEAPON)) {
			attacker.setData(AADataAttachments.LAST_CONCENTRATED_ATTACK_TIME, attacker.tickCount);
			attacker.setData(AADataAttachments.CONCENTRATION_LEVEL, Math.min(attacker.getData(AADataAttachments.CONCENTRATION_LEVEL) + 1, 4));
		} else {
			attacker.setData(AADataAttachments.CONCENTRATED_TARGET, target);
			attacker.setData(AADataAttachments.CONCENTRATED_WEAPON, weapon);
			attacker.setData(AADataAttachments.LAST_CONCENTRATED_ATTACK_TIME, attacker.tickCount);
			attacker.setData(AADataAttachments.CONCENTRATION_LEVEL, 0);
		}
	}

	public static void onTick(LivingEntity living) {
		int level = living.getData(AADataAttachments.CONCENTRATION_LEVEL);
		if (level <= 0) {
			return;
		}
		AttributeInstance attackDamageInstance = living.getAttribute(Attributes.ATTACK_DAMAGE);
		ResourceLocation attackDamageLocation = ResourceLocation.fromNamespaceAndPath(AAWeaponTraits.CONCENTRATION.getId().getNamespace(), "weapon_trait/" + AAWeaponTraits.CONCENTRATION.getId().getPath() + "/" + Attributes.ATTACK_DAMAGE.unwrapKey().orElseThrow().location().getPath());
		if (attackDamageInstance != null) {
			attackDamageInstance.removeModifier(attackDamageLocation);
			attackDamageInstance.addTransientModifier(new AttributeModifier(attackDamageLocation, 0.5 * level, AttributeModifier.Operation.ADD_VALUE));
		}
		AttributeInstance attackSpeedInstance = living.getAttribute(Attributes.ATTACK_SPEED);
		ResourceLocation attackSpeedLocation = ResourceLocation.fromNamespaceAndPath(AAWeaponTraits.CONCENTRATION.getId().getNamespace(), "weapon_trait/" + AAWeaponTraits.CONCENTRATION.getId().getPath() + "/" + Attributes.ATTACK_SPEED.unwrapKey().orElseThrow().location().getPath());
		if (attackSpeedInstance != null) {
			attackSpeedInstance.removeModifier(attackSpeedLocation);
			attackSpeedInstance.addTransientModifier(new AttributeModifier(attackSpeedLocation, 0.1 * level, AttributeModifier.Operation.ADD_VALUE));
		}
		if (living.tickCount - living.getData(AADataAttachments.LAST_CONCENTRATED_ATTACK_TIME) > 100 || living.getWeaponItem() != living.getData(AADataAttachments.CONCENTRATED_WEAPON)) {
			living.removeData(AADataAttachments.CONCENTRATED_TARGET);
			living.removeData(AADataAttachments.CONCENTRATED_WEAPON);
			living.removeData(AADataAttachments.LAST_CONCENTRATED_ATTACK_TIME);
			living.removeData(AADataAttachments.CONCENTRATION_LEVEL);
			if (attackDamageInstance != null) {
				attackDamageInstance.removeModifier(attackDamageLocation);
			}
			if (attackSpeedInstance != null) {
				attackSpeedInstance.removeModifier(attackSpeedLocation);
			}
		}
	}
}
