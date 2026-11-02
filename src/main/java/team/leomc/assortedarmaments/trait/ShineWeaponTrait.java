package team.leomc.assortedarmaments.trait;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.leomc.assortedarmaments.registry.AAEffects;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

public class ShineWeaponTrait extends WeaponTrait {
	private static final int REFRESH_INTERVAL = 20;
	private static final int EFFECT_DURATION = 40;

	public static boolean hasTrait(ItemStack stack) {
		return WeaponTraitHelper.hasTrait(AAWeaponTraits.SHINE, stack);
	}

	public static void onStuckTick(ItemStack stack, LivingEntity target, int tickCount) {
		if (tickCount % REFRESH_INTERVAL == 0 && hasTrait(stack) && target.isAlive()) {
			target.addEffect(new MobEffectInstance(AAEffects.EXTRACT, EFFECT_DURATION, 0, false, false));
		}
	}
}
