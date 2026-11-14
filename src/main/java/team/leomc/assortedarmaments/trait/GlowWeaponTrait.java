package team.leomc.assortedarmaments.trait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

public class GlowWeaponTrait extends WeaponTrait {
	public static final int GLOW_DURATION = 60;
	private static final int STUCK_REFRESH_INTERVAL = 10;
	private static final int STUCK_GLOW_DURATION = GLOW_DURATION + STUCK_REFRESH_INTERVAL;

	@Override
	public Object[] descriptionArgs() {
		return new Object[]{Component.literal(String.valueOf(AACommonConfig.glowStuckDamageBonus * 100)).withStyle(ChatFormatting.DARK_GREEN)};
	}

	public static boolean hasTrait(ItemStack stack) {
		return WeaponTraitHelper.hasTrait(AAWeaponTraits.GLOW, stack);
	}

	public static boolean hasStuckJavelin(LivingEntity target) {
		return target.getData(AADataAttachments.GLOW_STUCK_JAVELINS) > 0;
	}

	public static void onStuckStart(LivingEntity target) {
		target.setData(AADataAttachments.GLOW_STUCK_JAVELINS, target.getData(AADataAttachments.GLOW_STUCK_JAVELINS) + 1);
	}

	public static void onStuckEnd(LivingEntity target) {
		target.setData(AADataAttachments.GLOW_STUCK_JAVELINS, Math.max(0, target.getData(AADataAttachments.GLOW_STUCK_JAVELINS) - 1));
	}

	public static void onHit(LivingEntity target) {
		applyGlow(target, GLOW_DURATION);
	}

	public static void onStuckTick(ItemStack stack, LivingEntity target, int tickCount) {
		if (tickCount % STUCK_REFRESH_INTERVAL == 0 && hasTrait(stack)) {
			applyGlow(target, STUCK_GLOW_DURATION);
		}
	}

	private static void applyGlow(LivingEntity target, int duration) {
		if (target.isAlive()) {
			target.addEffect(new MobEffectInstance(MobEffects.GLOWING, duration, 0, false, false));
		}
	}
}
