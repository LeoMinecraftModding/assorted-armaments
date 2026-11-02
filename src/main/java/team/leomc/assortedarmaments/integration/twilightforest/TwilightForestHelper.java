package team.leomc.assortedarmaments.integration.twilightforest;

import net.minecraft.world.item.Rarity;
import net.neoforged.fml.ModList;
import team.leomc.assortedarmaments.data.gen.tags.AAItemTagsProvider;
import team.leomc.assortedarmaments.integration.*;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.tags.AAItemTags;
import twilightforest.util.TFToolMaterials;

import java.util.List;

public class TwilightForestHelper implements AACompatModule {
	public static final String ID = "twilightforest";
	public static final boolean IS_LOADED = ModList.get().isLoaded(ID);

	private static final AACompatMaterial IRONWOOD = new AACompatMaterial(
		"ironwood",
		() -> TFToolMaterials.IRONWOOD,
		"c:ingots/ironwood",
		"c:rods/wooden",
		"{\"minecraft:enchantments\":{\"minecraft:knockback\":1}}",
		properties -> properties,
		AACompatStyle.of("ironwood").types(AAWeaponType.MACE, AAWeaponType.HEAVY_SHIELD)
	);

	private static final AACompatMaterial STEELEAF = new AACompatMaterial(
		"steeleaf",
		() -> TFToolMaterials.STEELEAF,
		"c:ingots/steeleaf",
		"c:rods/wooden",
		"{\"minecraft:enchantments\":{\"minecraft:looting\":2}}",
		properties -> properties,
		AACompatStyle.of("steeleaf").types(AAWeaponType.MACE, AAWeaponType.JIAN, AAWeaponType.FLAIL)
	);

	private static final AACompatMaterial KNIGHTMETAL = new AACompatMaterial(
		"knightmetal",
		() -> TFToolMaterials.KNIGHTMETAL,
		"c:ingots/knightmetal",
		"c:rods/wooden",
		"",
		properties -> properties,
		AACompatStyle.of("knightmetal").types(AAWeaponType.MACE, AAWeaponType.JIAN, AAWeaponType.JAVELIN),
		List.of(AAWeaponTraits.KNIGHT)
	);

	private static final AACompatMaterial FIERY = new AACompatMaterial(
		"fiery",
		() -> TFToolMaterials.FIERY,
		"c:ingots/fiery",
		"c:rods/blaze",
		"",
		properties -> properties
			.rarity(Rarity.UNCOMMON)
			.fireResistant(),
		AACompatStyle.of("fiery").layer("fiery_layer")
			.types(AAWeaponType.MACE, AAWeaponType.JIAN, AAWeaponType.FLAIL, AAWeaponType.JAVELIN, AAWeaponType.HEAVY_SHIELD),
		List.of(AAWeaponTraits.IGNIT)
	);

	private static final List<AAWeaponDefinition> WEAPONS = List.of(
		// claymores
		new AAWeaponDefinition(AAWeaponType.CLAYMORE, IRONWOOD, 5.5f, -3f, "ironwood_claymore", "铁木大剑"),
		new AAWeaponDefinition(AAWeaponType.CLAYMORE, STEELEAF, 6.0f, -3f, "steeleaf_claymore", "钢叶大剑"),
		new AAWeaponDefinition(AAWeaponType.CLAYMORE, KNIGHTMETAL, 6.0f, -3f, "knightmetal_claymore", "骑士大剑"),
		new AAWeaponDefinition(AAWeaponType.CLAYMORE, FIERY, 6.0f, -3f, "fiery_claymore", "炽铁大剑"),
		// maces
		new AAWeaponDefinition(AAWeaponType.MACE, IRONWOOD, 4.0f, -2.9f, "ironwood_mace", "铁木钉头锤"),
		new AAWeaponDefinition(AAWeaponType.MACE, STEELEAF, 4.5f, -2.9f, "steeleaf_mace", "钢叶钉头锤"),
		new AAWeaponDefinition(AAWeaponType.MACE, KNIGHTMETAL, 4.5f, -2.9f, "knightmetal_mace", "骑士钉头锤"),
		new AAWeaponDefinition(AAWeaponType.MACE, FIERY, 4.5f, -2.9f, "fiery_mace", "炽铁钉头锤"),
		// flails
		new AAWeaponDefinition(AAWeaponType.FLAIL, IRONWOOD, 4.5f, -3.2f, "ironwood_flail", "铁木流星锤"),
		new AAWeaponDefinition(AAWeaponType.FLAIL, STEELEAF, 5.0f, -3.2f, "steeleaf_flail", "钢叶流星锤"),
		new AAWeaponDefinition(AAWeaponType.FLAIL, KNIGHTMETAL, 5.0f, -3.2f, "knightmetal_flail", "骑士流星锤"),
		new AAWeaponDefinition(AAWeaponType.FLAIL, FIERY, 5.0f, -3.2f, "fiery_flail", "炽铁流星锤"),
		// javelins
		new AAWeaponDefinition(AAWeaponType.JAVELIN, IRONWOOD, 1.5f, -2.0f, "ironwood_javelin", "铁木标枪"),
		new AAWeaponDefinition(AAWeaponType.JAVELIN, STEELEAF, 1.0f, -2.0f, "steeleaf_javelin", "钢叶标枪"),
		new AAWeaponDefinition(AAWeaponType.JAVELIN, KNIGHTMETAL, 1.0f, -2.0f, "knightmetal_javelin", "骑士标枪"),
		new AAWeaponDefinition(AAWeaponType.JAVELIN, FIERY, 1.0f, -2.0f, "fiery_javelin", "炽铁标枪"),
		// pikes
		new AAWeaponDefinition(AAWeaponType.PIKE, IRONWOOD, 3.0f, -2.8f, "ironwood_pike", "铁木长枪"),
		new AAWeaponDefinition(AAWeaponType.PIKE, STEELEAF, 3.0f, -2.8f, "steeleaf_pike", "钢叶长枪"),
		new AAWeaponDefinition(AAWeaponType.PIKE, KNIGHTMETAL, 3.0f, -2.8f, "knightmetal_pike", "骑士长枪"),
		new AAWeaponDefinition(AAWeaponType.PIKE, FIERY, 3.0f, -2.8f, "fiery_pike", "炽铁长枪"),
		// rapiers
		new AAWeaponDefinition(AAWeaponType.RAPIER, IRONWOOD, 1.5f, -2.0f, "ironwood_rapier", "铁木刺剑"),
		new AAWeaponDefinition(AAWeaponType.RAPIER, STEELEAF, 1.0f, -2.0f, "steeleaf_rapier", "钢叶刺剑"),
		new AAWeaponDefinition(AAWeaponType.RAPIER, KNIGHTMETAL, 1.0f, -2.0f, "knightmetal_rapier", "骑士刺剑"),
		new AAWeaponDefinition(AAWeaponType.RAPIER, FIERY, 1.0f, -2.0f, "fiery_rapier", "炽铁刺剑"),
		// halberds
		new AAWeaponDefinition(AAWeaponType.HALBERD, IRONWOOD, 5.5f, -3.2f, "ironwood_halberd", "铁木长戟"),
		new AAWeaponDefinition(AAWeaponType.HALBERD, STEELEAF, 6.5f, -3.2f, "steeleaf_halberd", "钢叶长戟"),
		new AAWeaponDefinition(AAWeaponType.HALBERD, KNIGHTMETAL, 6.5f, -3.2f, "knightmetal_halberd", "骑士长戟"),
		new AAWeaponDefinition(AAWeaponType.HALBERD, FIERY, 7.0f, -3.2f, "fiery_halberd", "炽铁长戟"),
		// heavy shields
		new AAWeaponDefinition(AAWeaponType.HEAVY_SHIELD, IRONWOOD, 2.0f, -2.8f, "ironwood_heavy_shield", "铁木重型战盾"),
		new AAWeaponDefinition(AAWeaponType.HEAVY_SHIELD, STEELEAF, 1.0f, -2.8f, "steeleaf_heavy_shield", "钢叶重型战盾"),
		new AAWeaponDefinition(AAWeaponType.HEAVY_SHIELD, KNIGHTMETAL, 1.0f, -2.8f, "knightmetal_heavy_shield", "骑士重型战盾"),
		new AAWeaponDefinition(AAWeaponType.HEAVY_SHIELD, FIERY, 1.0f, -2.8f, "fiery_heavy_shield", "炽铁重型战盾"),
		// claws
		new AAWeaponDefinition(AAWeaponType.CLAW, IRONWOOD, 0.5f, -1.6f, "ironwood_claw", "铁木利爪"),
		new AAWeaponDefinition(AAWeaponType.CLAW, STEELEAF, 0.0f, -1.6f, "steeleaf_claw", "钢叶利爪"),
		new AAWeaponDefinition(AAWeaponType.CLAW, KNIGHTMETAL, 0.0f, -1.6f, "knightmetal_claw", "骑士利爪"),
		new AAWeaponDefinition(AAWeaponType.CLAW, FIERY, 0.0f, -1.6f, "fiery_claw", "炽铁利爪"),
		// jians
		new AAWeaponDefinition(AAWeaponType.JIAN, IRONWOOD, 3.5f, -2.8f, "ironwood_jian", "铁木锏"),
		new AAWeaponDefinition(AAWeaponType.JIAN, STEELEAF, 3.5f, -2.8f, "steeleaf_jian", "钢叶锏"),
		new AAWeaponDefinition(AAWeaponType.JIAN, KNIGHTMETAL, 3.5f, -2.8f, "knightmetal_jian", "骑士锏"),
		new AAWeaponDefinition(AAWeaponType.JIAN, FIERY, 4.0f, -2.8f, "fiery_jian", "炽铁锏")
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

	@Override
	public void addExtraTags(AAItemTagsProvider provider) {
		provider.tag(AAItemTags.KNIGHTMETAL_WEAPON)
			.addOptional(team.leomc.assortedarmaments.AssortedArmaments.id("knightmetal_rapier"))
			.addOptional(team.leomc.assortedarmaments.AssortedArmaments.id("knightmetal_pike"))
			.addOptional(team.leomc.assortedarmaments.AssortedArmaments.id("knightmetal_javelin"))
			.addOptional(team.leomc.assortedarmaments.AssortedArmaments.id("knightmetal_claw"));
		provider.tag(AAItemTags.KNIGHTMETAL_TOOL)
			.addOptional(team.leomc.assortedarmaments.AssortedArmaments.id("knightmetal_claymore"))
			.addOptional(team.leomc.assortedarmaments.AssortedArmaments.id("knightmetal_mace"))
			.addOptional(team.leomc.assortedarmaments.AssortedArmaments.id("knightmetal_flail"))
			.addOptional(team.leomc.assortedarmaments.AssortedArmaments.id("knightmetal_halberd"))
			.addOptional(team.leomc.assortedarmaments.AssortedArmaments.id("knightmetal_heavy_shield"))
			.addOptional(team.leomc.assortedarmaments.AssortedArmaments.id("knightmetal_jian"));
	}
}
