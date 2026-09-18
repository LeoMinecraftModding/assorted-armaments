package team.leomc.assortedarmaments.trait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class WeaponDescriptiveWeaponTrait extends WeaponTrait{
	public Component getName() {
		return Component.literal("<").withStyle(ChatFormatting.DARK_GREEN).append(super.getName().copy()).withStyle(ChatFormatting.GREEN).append(">").withStyle(ChatFormatting.DARK_GREEN);
	}
}
