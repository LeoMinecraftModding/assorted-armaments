package team.leomc.assortedarmaments.trait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import team.leomc.assortedarmaments.AACommonConfig;

public class ShockWeaponTrait extends WeaponTrait {
	@Override
	public Object[] descriptionArgs() {
		return new Object[]{Component.literal(String.valueOf(AACommonConfig.armorBasedAttackDamagePercentage * 100)).withStyle(ChatFormatting.DARK_GREEN)};
	}

	public static void onIncomingDamage(LivingIncomingDamageEvent event, LivingEntity victim) {
		event.setAmount((float) (event.getAmount() + victim.getArmorValue() * AACommonConfig.armorBasedAttackDamagePercentage));
	}
}
