package team.leomc.assortedarmaments.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.item.enchantment.effects.DisableTargeting;

public class AAEnchantmentEntityEffects {
	public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENCHANTMENT_ENTITY_EFFECTS = DeferredRegister.create(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, AssortedArmaments.ID);

	public static final DeferredHolder<MapCodec<? extends EnchantmentEntityEffect>, MapCodec<DisableTargeting>> DISABLE_TARGETING = ENCHANTMENT_ENTITY_EFFECTS.register("disable_targeting", () -> DisableTargeting.CODEC);
}
