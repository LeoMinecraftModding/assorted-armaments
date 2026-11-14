package team.leomc.assortedarmaments.integration.eternalstarlight;

import cn.leolezury.eternalstarlight.common.item.combat.ESItemTiers;
import net.neoforged.fml.ModList;
import team.leomc.assortedarmaments.integration.*;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

import java.util.List;

public class EternalStarlightHelper implements AACompatModule {
	public static final String ID = "eternal_starlight";
	public static final boolean IS_LOADED = ModList.get().isLoaded(ID);

	private static final AACompatMaterial DEEPSILVER = new AACompatMaterial(
		"deepsilver",
		() -> ESItemTiers.DEEPSILVER,
		"c:ingots/deepsilver",
		"c:rods/wooden",
		"",
		properties -> properties,
		AACompatStyle.NONE,
		List.of(AAWeaponTraits.LIGHT_DANCE, AAWeaponTraits.BANE_UNDEAD, AAWeaponTraits.POISON_IMMUNE)
	);

	private static final AACompatMaterial THERMAL_SPRINGSTONE = new AACompatMaterial(
		"thermal_springstone",
		() -> ESItemTiers.THERMAL_SPRINGSTONE,
		"c:ingots/thermal_springstone",
		"c:rods/wooden",
		"",
		properties -> properties,
		AACompatStyle.NONE,
		List.of(AAWeaponTraits.HEAT, AAWeaponTraits.FIRE_IMMUNE)
	);

	private static final AACompatMaterial GLACITE = new AACompatMaterial(
		"glacite",
		() -> ESItemTiers.GLACITE,
		"c:gems/glacite",
		"c:rods/wooden",
		"",
		properties -> properties,
		AACompatStyle.NONE,
		List.of(AAWeaponTraits.CHILL, AAWeaponTraits.COLD_IMMUNE)
	);

	private static final List<AAWeaponDefinition> WEAPONS = List.of(
		new AAWeaponDefinition(AAWeaponType.CLAYMORE, DEEPSILVER, 5.5f, -3f, "deepsilver_claymore", "深银大剑"),
		new AAWeaponDefinition(AAWeaponType.CLAYMORE, THERMAL_SPRINGSTONE, 5.5f, -3f, "thermal_springstone_claymore", "热泉石大剑"),
		new AAWeaponDefinition(AAWeaponType.CLAYMORE, GLACITE, 5.5f, -3f, "glacite_claymore", "永冻石大剑")
	);

	@Override
	public String id() {
		return ID;
	}

	@Override
	public boolean isLoaded() {
		return IS_LOADED;
	}

	@Override
	public List<AAWeaponDefinition> weapons() {
		return WEAPONS;
	}
}
