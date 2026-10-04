package team.leomc.assortedarmaments.integration.aquaculture;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.teammetallurgy.aquaculture.api.AquacultureAPI;
import net.minecraft.Util;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.data.gen.lang.AAEnglishLanguageProvider;
import team.leomc.assortedarmaments.data.gen.model.AAItemModelProvider;
import team.leomc.assortedarmaments.data.gen.tags.AAItemTagsProvider;
import team.leomc.assortedarmaments.integration.MaterialsComponent;
import team.leomc.assortedarmaments.item.*;
import team.leomc.assortedarmaments.registry.AADataComponents;
import team.leomc.assortedarmaments.registry.AAMaterials;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.tags.AAItemTags;

import java.util.*;
import java.util.function.Supplier;

public class AquacultureHelper {
	public static final String ID = "aquaculture";
	public static final boolean IS_LOADED = ModList.get().isLoaded(ID);
	private static final Map<TagKey<Item>, List<Tuple<ResourceLocation, String>>> tags = new HashMap<>();
	private static final List<DeferredItem<Item>> flailItems = new ArrayList<>();
	private static final List<DeferredItem<Item>> javelinItems = new ArrayList<>();

	public static void registerClaymores(DeferredRegister.Items items) {
		register(AAItemTags.CLAYMORES, items, "neptunium_claymore", "c:ingots/neptunium", () -> new ClaymoreItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().attributes(ClaymoreItem.createAttributes(AquacultureAPI.MATS.NEPTUNIUM, 6.0f, -3f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.NEPTUNES_MIGHT)), List.of(AAWeaponTraits.NEPTUNES_MIGHT)));
	}

	public static void registerMace(DeferredRegister.Items items) {
		register(AAItemTags.MACES, items, "neptunium_mace", "c:ingots/neptunium", () -> new MaceItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().attributes(MaceItem.createAttributes(AquacultureAPI.MATS.NEPTUNIUM, 4.5f, -2.9f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.NEPTUNES_MIGHT)), List.of(AAWeaponTraits.NEPTUNES_MIGHT)));
	}

	public static void registerFlail(DeferredRegister.Items items) {
		register(AAItemTags.FLAILS, items, "neptunium_flail", "c:ingots/neptunium", () -> new FlailItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().attributes(FlailItem.createAttributes(AquacultureAPI.MATS.NEPTUNIUM, 5.0f, -3.2f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.NEPTUNES_MIGHT)), List.of(AAWeaponTraits.NEPTUNES_MIGHT)));
	}

	public static void registerJavelin(DeferredRegister.Items items) {
		register(AAItemTags.JAVELINS, items, "neptunium_javelin", "c:ingots/neptunium", () -> new JavelinItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().attributes(JavelinItem.createAttributes(AquacultureAPI.MATS.NEPTUNIUM, 1.0f, -2.0f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.NEPTUNES_MIGHT)), List.of(AAWeaponTraits.NEPTUNES_MIGHT)));
	}

	public static void registerPike(DeferredRegister.Items items) {
		register(AAItemTags.PIKES, items, "neptunium_pike", "c:ingots/neptunium", () -> new PikeItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().attributes(PikeItem.createAttributes(AquacultureAPI.MATS.NEPTUNIUM, 3.0f, -2.8f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.NEPTUNES_MIGHT)), List.of(AAWeaponTraits.NEPTUNES_MIGHT)));
	}

	public static void registerRapier(DeferredRegister.Items items) {
		register(AAItemTags.RAPIERS, items, "neptunium_rapier", "c:ingots/neptunium", () -> new RapierItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().attributes(RapierItem.createAttributes(AquacultureAPI.MATS.NEPTUNIUM, 1.0f, -2.0f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.NEPTUNES_MIGHT)), List.of(AAWeaponTraits.NEPTUNES_MIGHT)));
	}

	public static void registerHalberd(DeferredRegister.Items items) {
		register(AAItemTags.HALBERDS, items, "neptunium_halberd", "c:ingots/neptunium", () -> new HalberdItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().attributes(HalberdItem.createAttributes(AquacultureAPI.MATS.NEPTUNIUM, 6.5f, -3.2f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.NEPTUNES_MIGHT)), List.of(AAWeaponTraits.NEPTUNES_MIGHT)));
	}

	public static void registerHeavyShield(DeferredRegister.Items items) {
		register(AAItemTags.HEAVY_SHIELDS, items, "neptunium_heavy_shield", "c:ingots/neptunium", () -> new HeavyShieldItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().attributes(HeavyShieldItem.createAttributes(AquacultureAPI.MATS.NEPTUNIUM, 1.0f, -2.8f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.NEPTUNES_MIGHT)), List.of(AAWeaponTraits.NEPTUNES_MIGHT)));
	}

	public static void registerClaw(DeferredRegister.Items items) {
		register(AAItemTags.CLAW, items, "neptunium_claw", "c:ingots/neptunium", () -> new ClawItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().attributes(ClawItem.createAttributes(AquacultureAPI.MATS.NEPTUNIUM, 0.0f, -1.6f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.NEPTUNES_MIGHT)), List.of(AAWeaponTraits.NEPTUNES_MIGHT)));
	}

	public static void registerJian(DeferredRegister.Items items) {
		register(AAItemTags.JIANS, items, "neptunium_jian", "c:ingots/neptunium", () -> new JianItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().attributes(JianItem.createAttributes(AquacultureAPI.MATS.NEPTUNIUM, 3.5f, -2.8f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.NEPTUNES_MIGHT)), List.of(AAWeaponTraits.NEPTUNES_MIGHT)));
	}

	@OnlyIn(Dist.CLIENT)
	public static List<ModelResourceLocation> getThrownModels() {
		List<ModelResourceLocation> models = new ArrayList<>(thrownModels(flailItems));
		models.addAll(thrownModels(javelinItems));
		return models;
	}

	private static List<ModelResourceLocation> thrownModels(List<DeferredItem<Item>> items) {
		return items.stream()
			.map(item -> ModelResourceLocation.standalone(AssortedArmaments.id("item/" + item.getId().getPath() + "_thrown")))
			.toList();
	}

	public static Item[] getFlails() {
		return flailItems.stream().map(DeferredItem::get).toArray(Item[]::new);
	}

	private static void register(TagKey<Item> tag, DeferredRegister.Items items, String name, String ingredient, Supplier<Item> supplier) {
		if (IS_LOADED) {
			DeferredItem<Item> item = items.register(name, supplier);
			tags.computeIfAbsent(tag, t -> new LinkedList<>()).add(new Tuple<>(item.getId(), ingredient));
			if (tag == AAItemTags.FLAILS) {
				flailItems.add(item);
			} else if (tag == AAItemTags.JAVELINS) {
				javelinItems.add(item);
			}
		} else {
			tags.computeIfAbsent(tag, t -> new LinkedList<>()).add(new Tuple<>(AssortedArmaments.id(name), ingredient));
		}
	}

	public static void addTags(AAItemTagsProvider provider) {
		tags.forEach((tag, ids) -> {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> appender = provider.tag(tag);
			ids.forEach(tuple -> appender.addOptional(tuple.getA()));
		});
	}

	public static void registerModels(AAItemModelProvider provider) {
		tags.getOrDefault(AAItemTags.CLAYMORES, List.of()).forEach(tuple -> {
			provider.claymore(tuple.getA());
			provider.inventoryHandheld(tuple.getA());
		});
		tags.getOrDefault(AAItemTags.MACES, List.of()).forEach(tuple -> {
			provider.handheldItem(tuple.getA());
		});
		tags.getOrDefault(AAItemTags.FLAILS, List.of()).forEach(tuple -> {
			provider.flail(tuple.getA());
			provider.spinningFlail(tuple.getA());
			provider.thrownFlail(tuple.getA());
		});
		tags.getOrDefault(AAItemTags.JAVELINS, List.of()).forEach(tuple -> {
			provider.javelin(tuple.getA());
			provider.thrownJavelin(tuple.getA());
		});
		tags.getOrDefault(AAItemTags.PIKES, List.of()).forEach(tuple -> {
			provider.pike(tuple.getA());
			provider.inventoryHandheld(tuple.getA());
		});
		tags.getOrDefault(AAItemTags.RAPIERS, List.of()).forEach(tuple -> {
			provider.rapier(tuple.getA());
		});
		tags.getOrDefault(AAItemTags.HALBERDS, List.of()).forEach(tuple -> {
			provider.pike(tuple.getA());
			provider.inventoryHandheld(tuple.getA());
		});
		tags.getOrDefault(AAItemTags.HEAVY_SHIELDS, List.of()).forEach(tuple -> {
			provider.heavyShield(tuple.getA());
			provider.inventoryHandheld(tuple.getA());
		});
		tags.getOrDefault(AAItemTags.CLAW, List.of()).forEach(tuple -> {
			provider.claw(tuple.getA());
		});
		tags.getOrDefault(AAItemTags.JIANS, List.of()).forEach(tuple -> {
			provider.handheldItem(tuple.getA());
		});
	}

	public static void addTranslations(LanguageProvider provider, boolean en) {
		if (en) {
			if (!IS_LOADED) {
				tags.values().stream().flatMap(Collection::stream).forEach(tuple -> provider.add(Util.makeDescriptionId("item", tuple.getA()), AAEnglishLanguageProvider.toTitleCase(tuple.getA().getPath())));
			}
		} else {
			provider.add("item.assorted_armaments.neptunium_claymore", "海王大剑");
			provider.add("item.assorted_armaments.neptunium_mace", "海王钉头锤");
			provider.add("item.assorted_armaments.neptunium_flail", "海王流星锤");
			provider.add("item.assorted_armaments.neptunium_javelin", "海王标枪");
			provider.add("item.assorted_armaments.neptunium_pike", "海王长枪");
			provider.add("item.assorted_armaments.neptunium_rapier", "海王刺剑");
			provider.add("item.assorted_armaments.neptunium_halberd", "海王长戟");
			provider.add("item.assorted_armaments.neptunium_heavy_shield", "海王重型战盾");
			provider.add("item.assorted_armaments.neptunium_claw", "海王利爪");
			provider.add("item.assorted_armaments.neptunium_jian", "海王锏");
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerAdditionalModel(HashMap<ModelResourceLocation, Map<ItemDisplayContext, ModelResourceLocation>> map) {
		tags.getOrDefault(AAItemTags.CLAYMORES, List.of()).forEach(tuple -> {
			ModelResourceLocation inventory = ModelResourceLocation.standalone(AssortedArmaments.id("item/" + tuple.getA().getPath() + "_inventory"));
			map.put(ModelResourceLocation.inventory(tuple.getA()), Map.of(
				ItemDisplayContext.HEAD, inventory,
				ItemDisplayContext.GUI, inventory,
				ItemDisplayContext.GROUND, inventory,
				ItemDisplayContext.FIXED, inventory
			));
		});
		tags.getOrDefault(AAItemTags.FLAILS, List.of()).forEach(tuple -> {
			map.put(ModelResourceLocation.inventory(tuple.getA()), Map.of(
				ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/" + tuple.getA().getPath()))
			));
		});
		tags.getOrDefault(AAItemTags.PIKES, List.of()).forEach(tuple -> {
			ModelResourceLocation inventory = ModelResourceLocation.standalone(AssortedArmaments.id("item/" + tuple.getA().getPath() + "_inventory"));
			map.put(ModelResourceLocation.inventory(tuple.getA()), Map.of(
				ItemDisplayContext.HEAD, inventory,
				ItemDisplayContext.GUI, inventory,
				ItemDisplayContext.GROUND, inventory,
				ItemDisplayContext.FIXED, inventory
			));
		});
		tags.getOrDefault(AAItemTags.HALBERDS, List.of()).forEach(tuple -> {
			ModelResourceLocation inventory = ModelResourceLocation.standalone(AssortedArmaments.id("item/" + tuple.getA().getPath() + "_inventory"));
			map.put(ModelResourceLocation.inventory(tuple.getA()), Map.of(
				ItemDisplayContext.HEAD, inventory,
				ItemDisplayContext.GUI, inventory,
				ItemDisplayContext.GROUND, inventory,
				ItemDisplayContext.FIXED, inventory
			));
		});
		tags.getOrDefault(AAItemTags.HEAVY_SHIELDS, List.of()).forEach(tuple -> {
			ModelResourceLocation inventory = ModelResourceLocation.standalone(AssortedArmaments.id("item/" + tuple.getA().getPath() + "_inventory"));
			map.put(ModelResourceLocation.inventory(tuple.getA()), Map.of(
				ItemDisplayContext.HEAD, inventory,
				ItemDisplayContext.GUI, inventory,
				ItemDisplayContext.GROUND, inventory,
				ItemDisplayContext.FIXED, inventory
			));
		});
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerItemProperties() {
		tags.getOrDefault(AAItemTags.CLAYMORES, List.of()).forEach(tuple -> {
			ItemProperties.register(BuiltInRegistries.ITEM.get(tuple.getA()), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
		});
		tags.getOrDefault(AAItemTags.FLAILS, List.of()).forEach(tuple -> {
			ItemProperties.register(BuiltInRegistries.ITEM.get(tuple.getA()), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
		});
		tags.getOrDefault(AAItemTags.PIKES, List.of()).forEach(tuple -> {
			ItemProperties.register(BuiltInRegistries.ITEM.get(tuple.getA()), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(BuiltInRegistries.ITEM.get(tuple.getA()), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
		});
		tags.getOrDefault(AAItemTags.RAPIERS, List.of()).forEach(tuple -> {
			ItemProperties.register(BuiltInRegistries.ITEM.get(tuple.getA()), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
		});
		tags.getOrDefault(AAItemTags.HALBERDS, List.of()).forEach(tuple -> {
			ItemProperties.register(BuiltInRegistries.ITEM.get(tuple.getA()), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(BuiltInRegistries.ITEM.get(tuple.getA()), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
		});
		tags.getOrDefault(AAItemTags.HEAVY_SHIELDS, List.of()).forEach(tuple -> {
			ItemProperties.register(BuiltInRegistries.ITEM.get(tuple.getA()), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
		});
	}

	public static void buildRecipes(Map<ResourceLocation, JsonObject> jsons) {
		Gson gson = new Gson();
		tags.getOrDefault(AAItemTags.CLAYMORES, List.of()).forEach(tuple -> {
			jsons.put(tuple.getA(), gson.fromJson("""
				{
				  "neoforge:conditions": [
				    {
				      "type": "neoforge:mod_loaded",
				      "modid": "%s"
				    }
				  ],
				  "type": "minecraft:crafting_shaped",
				  "category": "equipment",
				  "key": {
				    "#": {
				      "tag": "c:rods/wooden"
				    },
				    "X": {
				      "tag": "%s"
				    }
				  },
				  "pattern": [
				    " XX",
				    "XXX",
				    "#X "
				  ],
				  "result": {
				    "count": 1,
				    "id": "%s"
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA()), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.MACES, List.of()).forEach(tuple -> {
			jsons.put(tuple.getA(), gson.fromJson("""
				{
				  "neoforge:conditions": [
				    {
				      "type": "neoforge:mod_loaded",
				      "modid": "%s"
				    }
				  ],
				  "type": "minecraft:crafting_shaped",
				  "category": "equipment",
				  "key": {
				    "#": {
				      "tag": "c:rods/wooden"
				    },
				    "X": {
				      "tag": "%s"
				    }
				  },
				  "pattern": [
				    " XX",
				    " XX",
				    "#  "
				  ],
				  "result": {
				    "count": 1,
				    "id": "%s"
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA()), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.FLAILS, List.of()).forEach(tuple -> {
			jsons.put(tuple.getA(), gson.fromJson("""
				{
				  "neoforge:conditions": [
				    {
				      "type": "neoforge:mod_loaded",
				      "modid": "%s"
				    }
				  ],
				  "type": "minecraft:crafting_shaped",
				  "category": "equipment",
				  "key": {
				    "#": {
				      "tag": "c:rods/wooden"
				    },
				    "X": {
				      "tag": "%s"
				    },
				    "C": {
				      "tag": "c:chains"
				    }
				  },
				  "pattern": [
				    " CC",
				    "XXC",
				    "XX#"
				  ],
				  "result": {
				    "count": 1,
				    "id": "%s"
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA()), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.JAVELINS, List.of()).forEach(tuple -> {
			jsons.put(tuple.getA(), gson.fromJson("""
				{
				  "neoforge:conditions": [
				    {
				      "type": "neoforge:mod_loaded",
				      "modid": "%s"
				    }
				  ],
				  "type": "minecraft:crafting_shaped",
				  "category": "equipment",
				  "key": {
				    "X": {
				      "tag": "%s"
				    }
				  },
				  "pattern": [
				    "  X",
				    " X ",
				    "X  "
				  ],
				  "result": {
				    "count": 1,
				    "id": "%s"
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA()), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.PIKES, List.of()).forEach(tuple -> {
			jsons.put(tuple.getA(), gson.fromJson("""
				{
				  "neoforge:conditions": [
				    {
				      "type": "neoforge:mod_loaded",
				      "modid": "%s"
				    }
				  ],
				  "type": "minecraft:crafting_shaped",
				  "category": "equipment",
				  "key": {
				    "#": {
				      "tag": "c:rods/wooden"
				    },
				    "X": {
				      "tag": "%s"
				    }
				  },
				  "pattern": [
				    "  X",
				    " # ",
				    "#  "
				  ],
				  "result": {
				    "count": 1,
				    "id": "%s"
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA()), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.RAPIERS, List.of()).forEach(tuple -> {
			jsons.put(tuple.getA(), gson.fromJson("""
				{
				  "neoforge:conditions": [
				    {
				      "type": "neoforge:mod_loaded",
				      "modid": "%s"
				    }
				  ],
				  "type": "minecraft:crafting_shaped",
				  "category": "equipment",
				  "key": {
				    "#": {
				      "tag": "c:rods/wooden"
				    },
				    "X": {
				      "tag": "%s"
				    }
				  },
				  "pattern": [
				    "  X",
				    " X ",
				    "## "
				  ],
				  "result": {
				    "count": 1,
				    "id": "%s"
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA()), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.HALBERDS, List.of()).forEach(tuple -> {
			jsons.put(tuple.getA(), gson.fromJson("""
				{
				  "neoforge:conditions": [
				    {
				      "type": "neoforge:mod_loaded",
				      "modid": "%s"
				    }
				  ],
				  "type": "minecraft:crafting_shaped",
				  "category": "equipment",
				  "key": {
				    "#": {
				      "tag": "c:rods/wooden"
				    },
				    "X": {
				      "tag": "%s"
				    }
				  },
				  "pattern": [
				    "  X",
				    " #X",
				    "#  "
				  ],
				  "result": {
				    "count": 1,
				    "id": "%s"
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA()), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.HEAVY_SHIELDS, List.of()).forEach(tuple -> {
			jsons.put(tuple.getA(), gson.fromJson("""
				{
				  "neoforge:conditions": [
				    {
				      "type": "neoforge:mod_loaded",
				      "modid": "%s"
				    }
				  ],
				  "type": "minecraft:crafting_shaped",
				  "category": "equipment",
				  "key": {
				    "#": {
				      "item": "minecraft:shield"
				    },
				    "X": {
				      "tag": "%s"
				    }
				  },
				  "pattern": [
				    "XXX",
				    "X#X",
				    " X "
				  ],
				  "result": {
				    "count": 1,
				    "id": "%s"
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA()), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.CLAW, List.of()).forEach(tuple -> {
			jsons.put(tuple.getA(), gson.fromJson("""
				{
				  "neoforge:conditions": [
				    {
				      "type": "neoforge:mod_loaded",
				      "modid": "%s"
				    }
				  ],
				  "type": "minecraft:crafting_shaped",
				  "category": "equipment",
				  "key": {
				    "#": {
				      "tag": "c:rods/wooden"
				    },
				    "X": {
				      "tag": "%s"
				    }
				  },
				  "pattern": [
				    "X X",
				    "###",
				    "   "
				  ],
				  "result": {
				    "count": 1,
				    "id": "%s"
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA()), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.JIANS, List.of()).forEach(tuple -> {
			jsons.put(tuple.getA(), gson.fromJson("""
				{
				  "neoforge:conditions": [
				    {
				      "type": "neoforge:mod_loaded",
				      "modid": "%s"
				    }
				  ],
				  "type": "minecraft:crafting_shaped",
				  "category": "equipment",
				  "key": {
				    "#": {
				      "tag": "c:rods/wooden"
				    },
				    "X": {
				      "tag": "%s"
				    }
				  },
				  "pattern": [
				    "  X",
				    " X ",
				    "#  "
				  ],
				  "result": {
				    "count": 1,
				    "id": "%s"
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA()), JsonObject.class));
		});
	}
}
