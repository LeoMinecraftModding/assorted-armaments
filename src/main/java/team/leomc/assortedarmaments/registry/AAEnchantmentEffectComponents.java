package team.leomc.assortedarmaments.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;

import java.util.List;

public class AAEnchantmentEffectComponents {
	public static final DeferredRegister<DataComponentType<?>> ENCHANTMENT_EFFECT_COMPONENTS = DeferredRegister.create(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, AssortedArmaments.ID);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> FLAIL_CRITICAL_ATTACK_CHANCE = ENCHANTMENT_EFFECT_COMPONENTS.register("flail_critical_attack_chance", () -> DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder().persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf()).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> FLAIL_BLAST_DAMAGE = ENCHANTMENT_EFFECT_COMPONENTS.register("flail_blast_damage", () -> DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder().persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf()).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> DEEP_WOUND_DAMAGE = ENCHANTMENT_EFFECT_COMPONENTS.register("deep_wound_damage", () -> DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder().persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf()).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> PARRY_DURATION = ENCHANTMENT_EFFECT_COMPONENTS.register("parry_duration", () -> DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder().persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf()).build());
}
