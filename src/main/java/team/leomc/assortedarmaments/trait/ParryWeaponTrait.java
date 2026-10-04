package team.leomc.assortedarmaments.trait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import team.leomc.assortedarmaments.AACommonConfig;

public class ParryWeaponTrait extends WeaponTrait {
	@Override
	public Object[] descriptionArgs() {
		return new Object[]{Component.literal(String.valueOf(AACommonConfig.parryMeleeDamageReduction * 100)).withStyle(ChatFormatting.DARK_GREEN)};
	}
}
