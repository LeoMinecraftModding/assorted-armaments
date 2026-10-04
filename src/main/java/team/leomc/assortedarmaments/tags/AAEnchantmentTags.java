package team.leomc.assortedarmaments.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import team.leomc.assortedarmaments.AssortedArmaments;

public class AAEnchantmentTags {
	public static final TagKey<Enchantment> EXCLUSIVE_SET_DEEP_WOUND = create("exclusive_set/deep_wound");

	private static TagKey<Enchantment> create(String id) {
		return TagKey.create(Registries.ENCHANTMENT, AssortedArmaments.id(id));
	}
}
