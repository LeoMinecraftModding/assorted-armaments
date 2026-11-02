package team.leomc.assortedarmaments.integration.aether;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.data.gen.tags.AAItemTagsProvider;
import team.leomc.assortedarmaments.integration.*;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

import java.util.List;

public class AetherHelper implements AACompatModule {
	public static final String ID = "aether";
	public static final boolean IS_LOADED = ModList.get().isLoaded(ID);

	public static final TagKey<Item> TREATED_AS_AETHER_ITEM = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ID, "treated_as_aether_item"));
	public static final TagKey<EntityType<?>> TREATED_AS_AETHER_ENTITY = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ID, "treated_as_aether_entity"));
	public static final TagKey<Item> NO_SKYROOT_DOUBLE_DROPS_ITEM = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ID, "no_skyroot_double_drops"));
	public static final TagKey<EntityType<?>> NO_SKYROOT_DOUBLE_DROPS_ENTITY = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ID, "no_skyroot_double_drops"));
	public static final TagKey<EntityType<?>> NO_AMBROSIUM_DROPS_ENTITY = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ID, "no_ambrosium_drops"));
	public static final ResourceLocation AMBROSIUM_SHARD = ResourceLocation.fromNamespaceAndPath(ID, "ambrosium_shard");
	public static final TagKey<EntityType<?>> UNLAUNCHABLE_ENTITY = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ID, "unlaunchable"));

	private static final AACompatMaterial SKYROOT = new AACompatMaterial(
		"skyroot",
		() -> AetherItemTiers.SKYROOT,
		"aether:skyroot_tool_crafting",
		"aether:skyroot_stick",
		"",
		properties -> properties,
		AACompatStyle.of("skyroot")
			.types(AAWeaponType.MACE, AAWeaponType.JIAN, AAWeaponType.FLAIL, AAWeaponType.JAVELIN, AAWeaponType.HEAVY_SHIELD)
			.model(AAWeaponType.HEAVY_SHIELD, "ironwood"),
		List.of(AAWeaponTraits.GRACE)
	);

	private static final AACompatMaterial HOLYSTONE = new AACompatMaterial(
		"holystone",
		() -> AetherItemTiers.HOLYSTONE,
		"aether:holystone",
		"aether:skyroot_stick",
		"",
		properties -> properties,
		AACompatStyle.of("holystone").types(AAWeaponType.MACE, AAWeaponType.JAVELIN, AAWeaponType.HEAVY_SHIELD),
		List.of(AAWeaponTraits.CRYSTALLIZE)
	);

	private static final AACompatMaterial ZANITE = new AACompatMaterial(
		"zanite",
		() -> AetherItemTiers.ZANITE,
		"aether:gems/zanite",
		"aether:skyroot_stick",
		"",
		properties -> properties,
		AACompatStyle.of("zanite").types(AAWeaponType.MACE, AAWeaponType.FLAIL, AAWeaponType.JAVELIN, AAWeaponType.HEAVY_SHIELD),
		List.of(AAWeaponTraits.SHARPEN)
	);

	private static final AACompatMaterial GRAVITITE = new AACompatMaterial(
		"gravitite",
		() -> AetherItemTiers.GRAVITITE,
		"aether:processed/gravitite",
		"aether:skyroot_stick",
		"",
		properties -> properties,
		AACompatStyle.of("gravitite").types(AAWeaponType.MACE, AAWeaponType.JIAN),
		List.of(AAWeaponTraits.SHIFT_GRAVITY)
	);

	private static final List<AAWeaponDefinition> WEAPONS = List.of(
		// skyroot
		weapon(AAWeaponType.CLAYMORE, SKYROOT, 4.5f, -3f, "skyroot_claymore", "天根木大剑"),
		weapon(AAWeaponType.MACE, SKYROOT, 4.0f, -2.9f, "skyroot_mace", "天根木钉头锤"),
		weapon(AAWeaponType.FLAIL, SKYROOT, 4.5f, -3.2f, "skyroot_flail", "天根木流星锤"),
		weapon(AAWeaponType.JAVELIN, SKYROOT, 2.0f, -2f, "skyroot_javelin", "天根木标枪"),
		weapon(AAWeaponType.PIKE, SKYROOT, 3.0f, -2.8f, "skyroot_pike", "天根木长枪"),
		weapon(AAWeaponType.RAPIER, SKYROOT, 2.0f, -2f, "skyroot_rapier", "天根木刺剑"),
		weapon(AAWeaponType.HALBERD, SKYROOT, 5.0f, -3.2f, "skyroot_halberd", "天根木长戟"),
		weapon(AAWeaponType.HEAVY_SHIELD, SKYROOT, 2.0f, -2.8f, "skyroot_heavy_shield", "天根木重型战盾"),
		weapon(AAWeaponType.CLAW, SKYROOT, 1.5f, -1.6f, "skyroot_claw", "天根木利爪"),
		weapon(AAWeaponType.JIAN, SKYROOT, 3.5f, -2.8f, "skyroot_jian", "天根木锏"),
		// holystone
		weapon(AAWeaponType.CLAYMORE, HOLYSTONE, 5.0f, -3f, "holystone_claymore", "圣石大剑"),
		weapon(AAWeaponType.MACE, HOLYSTONE, 4.0f, -2.9f, "holystone_mace", "圣石钉头锤"),
		weapon(AAWeaponType.FLAIL, HOLYSTONE, 4.5f, -3.2f, "holystone_flail", "圣石流星锤"),
		weapon(AAWeaponType.JAVELIN, HOLYSTONE, 2.0f, -2f, "holystone_javelin", "圣石标枪"),
		weapon(AAWeaponType.PIKE, HOLYSTONE, 3.0f, -2.8f, "holystone_pike", "圣石长枪"),
		weapon(AAWeaponType.RAPIER, HOLYSTONE, 2.0f, -2f, "holystone_rapier", "圣石刺剑"),
		weapon(AAWeaponType.HALBERD, HOLYSTONE, 5.5f, -3.2f, "holystone_halberd", "圣石长戟"),
		weapon(AAWeaponType.HEAVY_SHIELD, HOLYSTONE, 1.5f, -2.8f, "holystone_heavy_shield", "圣石重型战盾"),
		weapon(AAWeaponType.CLAW, HOLYSTONE, 1.0f, -1.6f, "holystone_claw", "圣石利爪"),
		weapon(AAWeaponType.JIAN, HOLYSTONE, 3.5f, -2.8f, "holystone_jian", "圣石锏"),
		// zanite
		weapon(AAWeaponType.CLAYMORE, ZANITE, 5.5f, -3f, "zanite_claymore", "紫晶大剑"),
		weapon(AAWeaponType.MACE, ZANITE, 4.0f, -2.9f, "zanite_mace", "紫晶钉头锤"),
		weapon(AAWeaponType.FLAIL, ZANITE, 4.5f, -3.2f, "zanite_flail", "紫晶流星锤"),
		weapon(AAWeaponType.JAVELIN, ZANITE, 1.5f, -2f, "zanite_javelin", "紫晶标枪"),
		weapon(AAWeaponType.PIKE, ZANITE, 3.0f, -2.8f, "zanite_pike", "紫晶长枪"),
		weapon(AAWeaponType.RAPIER, ZANITE, 1.5f, -2f, "zanite_rapier", "紫晶刺剑"),
		weapon(AAWeaponType.HALBERD, ZANITE, 6.0f, -3.2f, "zanite_halberd", "紫晶长戟"),
		weapon(AAWeaponType.HEAVY_SHIELD, ZANITE, 1.5f, -2.8f, "zanite_heavy_shield", "紫晶重型战盾"),
		weapon(AAWeaponType.CLAW, ZANITE, 0.5f, -1.6f, "zanite_claw", "紫晶利爪"),
		weapon(AAWeaponType.JIAN, ZANITE, 3.5f, -2.8f, "zanite_jian", "紫晶锏"),
		// gravitite
		weapon(AAWeaponType.CLAYMORE, GRAVITITE, 6.0f, -3f, "gravitite_claymore", "重力晶大剑"),
		weapon(AAWeaponType.MACE, GRAVITITE, 4.5f, -2.9f, "gravitite_mace", "重力晶钉头锤"),
		weapon(AAWeaponType.FLAIL, GRAVITITE, 5.0f, -3.2f, "gravitite_flail", "重力晶流星锤"),
		weapon(AAWeaponType.JAVELIN, GRAVITITE, 1.0f, -2f, "gravitite_javelin", "重力晶标枪"),
		weapon(AAWeaponType.PIKE, GRAVITITE, 3.0f, -2.8f, "gravitite_pike", "重力晶长枪"),
		weapon(AAWeaponType.RAPIER, GRAVITITE, 1.0f, -2f, "gravitite_rapier", "重力晶刺剑"),
		weapon(AAWeaponType.HALBERD, GRAVITITE, 6.5f, -3.2f, "gravitite_halberd", "重力晶长戟"),
		weapon(AAWeaponType.HEAVY_SHIELD, GRAVITITE, 1.0f, -2.8f, "gravitite_heavy_shield", "重力晶重型战盾"),
		weapon(AAWeaponType.CLAW, GRAVITITE, 0.0f, -1.6f, "gravitite_claw", "重力晶利爪"),
		weapon(AAWeaponType.JIAN, GRAVITITE, 3.5f, -2.8f, "gravitite_jian", "重力晶锏")
	);

	private static AAWeaponDefinition weapon(AAWeaponType type, AACompatMaterial material, float damage, float speed, String name, String zhName) {
		return new AAWeaponDefinition(type, material, damage, speed, name, zhName);
	}

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
		var appender = provider.tag(TREATED_AS_AETHER_ITEM);
		for (AAWeaponDefinition weapon : WEAPONS) {
			appender.addOptional(AssortedArmaments.id(weapon.name()));
		}
	}
}
