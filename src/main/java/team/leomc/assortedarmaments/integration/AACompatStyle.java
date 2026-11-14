package team.leomc.assortedarmaments.integration;

import net.minecraft.resources.ResourceLocation;
import team.leomc.assortedarmaments.AssortedArmaments;

import java.util.*;

public record AACompatStyle(String suffix, String layerTexture, Set<AAWeaponType> types, Map<AAWeaponType, String> modelSuffixes) {
	public static final AACompatStyle NONE = new AACompatStyle("", null, Set.of(), Map.of());

	public AACompatStyle {
		types = Set.copyOf(types);
		modelSuffixes = Map.copyOf(modelSuffixes);
	}

	public static AACompatStyle of(String suffix) {
		return new AACompatStyle(suffix, null, Set.of(), Map.of());
	}

	public AACompatStyle layer(String texture) {
		return new AACompatStyle(suffix, texture, types, modelSuffixes);
	}

	public AACompatStyle types(AAWeaponType... types) {
		Set<AAWeaponType> set = EnumSet.noneOf(AAWeaponType.class);
		Collections.addAll(set, types);
		return new AACompatStyle(suffix, layerTexture, set, modelSuffixes);
	}

	public AACompatStyle model(AAWeaponType type, String modelSuffix) {
		Map<AAWeaponType, String> overrides = new EnumMap<>(AAWeaponType.class);
		overrides.putAll(modelSuffixes);
		overrides.put(type, modelSuffix);
		return new AACompatStyle(suffix, layerTexture, types, overrides);
	}

	public boolean supports(AAWeaponType type) {
		return types.contains(type);
	}

	public ResourceLocation parent(String base, AAWeaponType type) {
		return AssortedArmaments.id("item/" + base + "_" + modelSuffixes.getOrDefault(type, suffix));
	}
}
