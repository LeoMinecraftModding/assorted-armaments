package team.leomc.assortedarmaments.integration.eternalstarlight;

import cn.leolezury.eternalstarlight.common.item.combat.ESItemTiers;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.data.gen.lang.AAEnglishLanguageProvider;
import team.leomc.assortedarmaments.data.gen.model.AAItemModelProvider;
import team.leomc.assortedarmaments.data.gen.tags.AAItemTagsProvider;
import team.leomc.assortedarmaments.integration.MaterialsComponent;
import team.leomc.assortedarmaments.item.ClaymoreItem;
import team.leomc.assortedarmaments.registry.AADataComponents;
import team.leomc.assortedarmaments.registry.AAMaterials;
import team.leomc.assortedarmaments.tags.AAItemTags;

import java.util.*;
import java.util.function.Supplier;

public class EternalStarlightHelper {
	public static final String ID = "eternal_starlight";
	public static final boolean IS_LOADED = ModList.get().isLoaded(ID);
	// tag -> [(id, ingredient),]
	private static final Map<TagKey<Item>, List<Tuple<ResourceLocation, String>>> tags = new HashMap<>();

	public static void registerClaymores(DeferredRegister.Items items) {
		register(AAItemTags.CLAYMORES, items, "swamp_silver_claymore", "c:ingots/swamp_silver", () -> new ClaymoreItem(ESItemTiers.SWAMP_SILVER, new Item.Properties().attributes(ClaymoreItem.createAttributes(ESItemTiers.SWAMP_SILVER, 5.5f, -3f, 0.5f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.LIGHT_DANCE, AAMaterials.BANE_UNDEAD, AAMaterials.POISON_IMMUNE))));
		register(AAItemTags.CLAYMORES, items, "thermal_springstone_claymore", "c:ingots/thermal_springstone", () -> new ClaymoreItem(ESItemTiers.THERMAL_SPRINGSTONE, new Item.Properties().attributes(ClaymoreItem.createAttributes(ESItemTiers.THERMAL_SPRINGSTONE, 5.5f, -3f, 0.5f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.HEAT, AAMaterials.FIRE_IMMUNE))));
		register(AAItemTags.CLAYMORES, items, "glacite_claymore", "c:gems/glacite", () -> new ClaymoreItem(ESItemTiers.GLACITE, new Item.Properties().attributes(ClaymoreItem.createAttributes(ESItemTiers.GLACITE, 5.5f, -3f, 0.5f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.CHILL, AAMaterials.COLD_IMMUNE))));
	}

	private static void register(TagKey<Item> tag, DeferredRegister.Items items, String name, String ingredient, Supplier<Item> supplier) {
		ResourceLocation id;
		if (IS_LOADED) {
			id = items.register(name, supplier).getId();
		} else {
			id = AssortedArmaments.id(name);
		}
		tags.computeIfAbsent(tag, t -> new LinkedList<>()).add(new Tuple<>(id, ingredient));
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
	}

	public static void addTranslations(LanguageProvider provider, boolean en) {
		if (en) {
			if (!IS_LOADED) {
				tags.values().stream().flatMap(Collection::stream).forEach(tuple -> provider.add(Util.makeDescriptionId("item", tuple.getA()), AAEnglishLanguageProvider.toTitleCase(tuple.getA().getPath())));
			}
		} else {
			provider.add("item.assorted_armaments.swamp_silver_claymore", "沼泽银大剑");
			provider.add("item.assorted_armaments.thermal_springstone_claymore", "热泉石大剑");
			provider.add("item.assorted_armaments.glacite_claymore", "永冻石大剑");
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
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerItemProperties() {
		tags.getOrDefault(AAItemTags.CLAYMORES, List.of()).forEach(tuple -> {
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
	}
}
