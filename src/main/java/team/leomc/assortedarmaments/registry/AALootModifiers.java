package team.leomc.assortedarmaments.registry;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.loot.GraceLootModifier;

public class AALootModifiers {
	public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
		DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, AssortedArmaments.ID);

	public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<GraceLootModifier>> GRACE =
		LOOT_MODIFIER_SERIALIZERS.register("grace", () -> GraceLootModifier.CODEC);
}
