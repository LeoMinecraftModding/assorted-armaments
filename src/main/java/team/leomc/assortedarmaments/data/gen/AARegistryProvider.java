package team.leomc.assortedarmaments.data.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.data.AAEnchantments;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class AARegistryProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
		.add(Registries.ENCHANTMENT, AAEnchantments::bootstrap);

	public AARegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Set.of(AssortedArmaments.ID, "minecraft"));
	}
}