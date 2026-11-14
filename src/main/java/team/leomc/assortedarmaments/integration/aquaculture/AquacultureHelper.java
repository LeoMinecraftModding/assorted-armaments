package team.leomc.assortedarmaments.integration.aquaculture;

import com.teammetallurgy.aquaculture.api.AquacultureAPI;
import net.neoforged.fml.ModList;
import team.leomc.assortedarmaments.integration.*;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

import java.util.List;

public class AquacultureHelper implements AACompatModule {
	public static final String ID = "aquaculture";
	public static final boolean IS_LOADED = ModList.get().isLoaded(ID);

	private static final AACompatMaterial NEPTUNIUM = new AACompatMaterial(
		"neptunium",
		() -> AquacultureAPI.MATS.NEPTUNIUM,
		"c:ingots/neptunium",
		"c:rods/wooden",
		"",
		properties -> properties,
		AACompatStyle.of("neptunium").types(AAWeaponType.MACE, AAWeaponType.JIAN),
		List.of(AAWeaponTraits.NEPTUNES_MIGHT)
	);

	private static final List<AAWeaponDefinition> WEAPONS = List.of(
		new AAWeaponDefinition(AAWeaponType.CLAYMORE, NEPTUNIUM, 6.0f, -3f, "neptunium_claymore", "海王大剑"),
		new AAWeaponDefinition(AAWeaponType.MACE, NEPTUNIUM, 4.5f, -2.9f, "neptunium_mace", "海王钉头锤"),
		new AAWeaponDefinition(AAWeaponType.FLAIL, NEPTUNIUM, 5.0f, -3.2f, "neptunium_flail", "海王流星锤"),
		new AAWeaponDefinition(AAWeaponType.JAVELIN, NEPTUNIUM, 1.0f, -2.0f, "neptunium_javelin", "海王标枪"),
		new AAWeaponDefinition(AAWeaponType.PIKE, NEPTUNIUM, 3.0f, -2.8f, "neptunium_pike", "海王长枪"),
		new AAWeaponDefinition(AAWeaponType.RAPIER, NEPTUNIUM, 1.0f, -2.0f, "neptunium_rapier", "海王刺剑"),
		new AAWeaponDefinition(AAWeaponType.HALBERD, NEPTUNIUM, 6.5f, -3.2f, "neptunium_halberd", "海王长戟"),
		new AAWeaponDefinition(AAWeaponType.HEAVY_SHIELD, NEPTUNIUM, 1.0f, -2.8f, "neptunium_heavy_shield", "海王重型战盾"),
		new AAWeaponDefinition(AAWeaponType.CLAW, NEPTUNIUM, 0.0f, -1.6f, "neptunium_claw", "海王利爪"),
		new AAWeaponDefinition(AAWeaponType.JIAN, NEPTUNIUM, 3.5f, -2.8f, "neptunium_jian", "海王锏")
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
