package team.leomc.assortedarmaments.integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.Util;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.data.gen.lang.AAEnglishLanguageProvider;
import team.leomc.assortedarmaments.data.gen.model.AAItemModelProvider;
import team.leomc.assortedarmaments.data.gen.tags.AAItemTagsProvider;
import team.leomc.assortedarmaments.integration.aether.AetherHelper;
import team.leomc.assortedarmaments.integration.aquaculture.AquacultureHelper;
import team.leomc.assortedarmaments.integration.twilightforest.TwilightForestHelper;

import java.util.*;

public final class AACompatManager {
	private static final List<AACompatModule> MODS = List.of(
//		new EternalStarlightHelper(),
		new TwilightForestHelper(),
		new AquacultureHelper(),
		new AetherHelper()
	);

	private static final List<RegisteredWeapon> ENTRIES = new ArrayList<>();

	public static void registerItems(DeferredRegister.Items items, AAWeaponType type) {
		for (AACompatModule mod : MODS) {
			for (AAWeaponDefinition weapon : mod.weapons()) {
				if (weapon.type() != type) {
					continue;
				}
				DeferredItem<Item> item = null;
				ResourceLocation id;
				if (mod.isLoaded()) {
					item = items.register(weapon.name(), weapon.factory());
					id = item.getId();
				} else {
					id = AssortedArmaments.id(weapon.name());
				}
				ENTRIES.add(new RegisteredWeapon(mod, weapon, id, item));
			}
		}
	}

	public static void addTags(AAItemTagsProvider provider) {
		Map<TagKey<Item>, List<ResourceLocation>> byTag = new LinkedHashMap<>();
		for (RegisteredWeapon entry : ENTRIES) {
			byTag.computeIfAbsent(entry.weapon().type().tag(), tag -> new ArrayList<>()).add(entry.id());
		}
		byTag.forEach((tag, ids) -> {
			var appender = provider.tag(tag);
			ids.forEach(appender::addOptional);
		});
		for (AACompatModule mod : MODS) {
			mod.addExtraTags(provider);
		}
	}

	public static void registerModels(AAItemModelProvider provider) {
		for (RegisteredWeapon entry : ENTRIES) {
			entry.weapon().type().registerModels(provider, entry.id(), entry.weapon().material().style());
		}
	}

	public static void addTranslations(LanguageProvider provider, boolean en) {
		for (RegisteredWeapon entry : ENTRIES) {
			if (en) {
				if (!entry.mod().isLoaded()) {
					provider.add(Util.makeDescriptionId("item", entry.id()), AAEnglishLanguageProvider.toTitleCase(entry.id().getPath()));
				}
			} else {
				provider.add(Util.makeDescriptionId("item", entry.id()), entry.weapon().zhName());
			}
		}
	}

	public static void buildRecipes(Map<ResourceLocation, JsonObject> jsons) {
		for (RegisteredWeapon entry : ENTRIES) {
			jsons.put(entry.id(), recipe(entry));
		}
	}

	private static JsonObject recipe(RegisteredWeapon entry) {
		AAWeaponDefinition weapon = entry.weapon();
		AACompatMaterial material = weapon.material();

		JsonObject root = new JsonObject();
		JsonArray conditions = new JsonArray();
		JsonObject condition = new JsonObject();
		condition.addProperty("type", "neoforge:mod_loaded");
		condition.addProperty("modid", entry.mod().id());
		conditions.add(condition);
		root.add("neoforge:conditions", conditions);

		root.addProperty("type", "minecraft:crafting_shaped");
		root.addProperty("category", "equipment");

		JsonObject key = new JsonObject();
		for (AAWeaponType.KeySpec spec : weapon.type().keys()) {
			JsonObject value = new JsonObject();
			switch (spec.type()) {
				case INGOT -> value.addProperty("tag", material.ingotTag());
				case ROD -> value.addProperty("tag", material.rodTag());
				case CHAINS -> value.addProperty("tag", "c:chains");
				case SHIELD -> value.addProperty("item", "minecraft:shield");
			}
			key.add(String.valueOf(spec.symbol()), value);
		}
		root.add("key", key);

		JsonArray pattern = new JsonArray();
		for (String line : weapon.type().pattern()) {
			pattern.add(line);
		}
		root.add("pattern", pattern);

		JsonObject result = new JsonObject();
		result.addProperty("count", 1);
		result.addProperty("id", entry.id().toString());
		if (!material.components().isEmpty()) {
			result.add("components", JsonParser.parseString(material.components()));
		}
		root.add("result", result);
		return root;
	}

	public static Item[] getFlails() {
		return ENTRIES.stream()
			.filter(entry -> entry.weapon().type() == AAWeaponType.FLAIL && entry.item() != null)
			.map(entry -> entry.item().get())
			.toArray(Item[]::new);
	}

	@OnlyIn(Dist.CLIENT)
	public static List<ModelResourceLocation> getThrownModels() {
		return ENTRIES.stream()
			.filter(entry -> entry.item() != null)
			.filter(entry -> entry.weapon().type() == AAWeaponType.FLAIL || entry.weapon().type() == AAWeaponType.JAVELIN)
			.map(entry -> ModelResourceLocation.standalone(AssortedArmaments.id("item/" + entry.id().getPath() + "_thrown")))
			.toList();
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerAdditionalModel(HashMap<ModelResourceLocation, Map<ItemDisplayContext, ModelResourceLocation>> map) {
		for (RegisteredWeapon entry : ENTRIES) {
			ResourceLocation id = entry.id();
			switch (entry.weapon().type()) {
				case CLAYMORE, MACE, PIKE, HALBERD, HEAVY_SHIELD, JIAN -> {
					ModelResourceLocation inventory = ModelResourceLocation.standalone(AssortedArmaments.id("item/" + id.getPath() + "_inventory"));
					map.put(ModelResourceLocation.inventory(id), Map.of(
						ItemDisplayContext.HEAD, inventory,
						ItemDisplayContext.GUI, inventory,
						ItemDisplayContext.GROUND, inventory,
						ItemDisplayContext.FIXED, inventory
					));
				}
				case FLAIL -> map.put(ModelResourceLocation.inventory(id), Map.of(
					ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/" + id.getPath()))
				));
				default -> {}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerItemProperties() {
		for (RegisteredWeapon entry : ENTRIES) {
			if (entry.item() == null) continue;
			ResourceLocation id = entry.id();
			switch (entry.weapon().type()) {
				case CLAYMORE, HEAVY_SHIELD -> registerBlocking(id);
				case FLAIL -> registerSpinning(id);
				case PIKE, HALBERD -> {
					registerBlocking(id);
					registerSprinting(id);
				}
				case RAPIER -> registerSprinting(id);
				default -> {}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	private static void registerBlocking(ResourceLocation id) {
		ItemProperties.register(BuiltInRegistries.ITEM.get(id), AssortedArmaments.id("blocking"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
	}

	@OnlyIn(Dist.CLIENT)
	private static void registerSpinning(ResourceLocation id) {
		ItemProperties.register(BuiltInRegistries.ITEM.get(id), AssortedArmaments.id("spinning"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
	}

	@OnlyIn(Dist.CLIENT)
	private static void registerSprinting(ResourceLocation id) {
		ItemProperties.register(BuiltInRegistries.ITEM.get(id), AssortedArmaments.id("sprinting"), (stack, level, entity, seed) -> entity != null && entity.getMainHandItem() == stack && entity.isSprinting() ? 1 : 0);
	}

	private record RegisteredWeapon(AACompatModule mod, AAWeaponDefinition weapon, ResourceLocation id, DeferredItem<Item> item) {
	}
}
