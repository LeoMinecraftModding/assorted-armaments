package team.leomc.assortedarmaments.integration.twilightforest;

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
import net.minecraft.world.item.Rarity;
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
import team.leomc.assortedarmaments.tags.AAItemTags;
import twilightforest.util.TFToolMaterials;

import java.util.*;
import java.util.function.Supplier;

public class TwilightForestHelper {
	public static final String ID = "twilightforest";
	public static final boolean IS_LOADED = ModList.get().isLoaded(ID);
	private static final Map<TagKey<Item>, List<Tuple<ResourceLocation, String>>> tags = new HashMap<>();
	private static final List<DeferredItem<Item>> flailItems = new ArrayList<>();
	private static final List<DeferredItem<Item>> javelinItems = new ArrayList<>();

	public static void registerClaymores(DeferredRegister.Items items) {
		register(AAItemTags.CLAYMORES, items, "ironwood_claymore", "c:ingots/ironwood", () -> new ClaymoreItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(ClaymoreItem.createAttributes(TFToolMaterials.IRONWOOD, 5.5f, -3f))));
		register(AAItemTags.CLAYMORES, items, "steeleaf_claymore", "c:ingots/steeleaf", () -> new ClaymoreItem(TFToolMaterials.STEELEAF, new Item.Properties().attributes(ClaymoreItem.createAttributes(TFToolMaterials.STEELEAF, 6.0f, -3f))));
		register(AAItemTags.CLAYMORES, items, "knightmetal_claymore", "c:ingots/knightmetal", () -> new ClaymoreItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(ClaymoreItem.createAttributes(TFToolMaterials.KNIGHTMETAL, 6.0f, -3f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.KNIGHT))));
		register(AAItemTags.CLAYMORES, items, "fiery_claymore", "c:ingots/fiery", () -> new ClaymoreItem(TFToolMaterials.FIERY, new Item.Properties().attributes(ClaymoreItem.createAttributes(TFToolMaterials.FIERY, 6.0f, -3f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.IGNIT)).rarity(Rarity.UNCOMMON).fireResistant()));
	}

	public static void registerMace(DeferredRegister.Items items) {
		register(AAItemTags.MACES, items, "ironwood_mace", "c:ingots/ironwood", () -> new MaceItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(MaceItem.createAttributes(TFToolMaterials.IRONWOOD, 4.0f, -2.9f))));
		register(AAItemTags.MACES, items, "steeleaf_mace", "c:ingots/steeleaf", () -> new MaceItem(TFToolMaterials.STEELEAF, new Item.Properties().attributes(MaceItem.createAttributes(TFToolMaterials.STEELEAF, 4.5f, -2.9f))));
		register(AAItemTags.MACES, items, "knightmetal_mace", "c:ingots/knightmetal", () -> new MaceItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(MaceItem.createAttributes(TFToolMaterials.KNIGHTMETAL, 4.5f, -2.9f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.KNIGHT))));
		register(AAItemTags.MACES, items, "fiery_mace", "c:ingots/fiery", () -> new MaceItem(TFToolMaterials.FIERY, new Item.Properties().attributes(MaceItem.createAttributes(TFToolMaterials.FIERY, 4.5f, -2.9f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.IGNIT)).rarity(Rarity.UNCOMMON).fireResistant()));
	}

	public static void registerFlail(DeferredRegister.Items items) {
		register(AAItemTags.FLAILS, items, "ironwood_flail", "c:ingots/ironwood", () -> new FlailItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(FlailItem.createAttributes(TFToolMaterials.IRONWOOD, 4.5f, -3.2f))));
		register(AAItemTags.FLAILS, items, "steeleaf_flail", "c:ingots/steeleaf", () -> new FlailItem(TFToolMaterials.STEELEAF, new Item.Properties().attributes(FlailItem.createAttributes(TFToolMaterials.STEELEAF, 5.0f, -3.2f))));
		register(AAItemTags.FLAILS, items, "knightmetal_flail", "c:ingots/knightmetal", () -> new FlailItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(FlailItem.createAttributes(TFToolMaterials.KNIGHTMETAL, 5.0f, -3.2f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.KNIGHT))));
		register(AAItemTags.FLAILS, items, "fiery_flail", "c:ingots/fiery", () -> new FlailItem(TFToolMaterials.FIERY, new Item.Properties().attributes(FlailItem.createAttributes(TFToolMaterials.FIERY, 5.0f, -3.2f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.IGNIT)).rarity(Rarity.UNCOMMON).fireResistant()));
	}

	public static void registerJavelin(DeferredRegister.Items items) {
		register(AAItemTags.JAVELINS, items, "ironwood_javelin", "c:ingots/ironwood", () -> new JavelinItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(JavelinItem.createAttributes(TFToolMaterials.IRONWOOD, 1.5f, -2.0f))));
		register(AAItemTags.JAVELINS, items, "steeleaf_javelin", "c:ingots/steeleaf", () -> new JavelinItem(TFToolMaterials.STEELEAF, new Item.Properties().attributes(JavelinItem.createAttributes(TFToolMaterials.STEELEAF, 1.0f, -2.0f))));
		register(AAItemTags.JAVELINS, items, "knightmetal_javelin", "c:ingots/knightmetal", () -> new JavelinItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(JavelinItem.createAttributes(TFToolMaterials.KNIGHTMETAL, 1.0f, -2.0f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.KNIGHT))));
		register(AAItemTags.JAVELINS, items, "fiery_javelin", "c:ingots/fiery", () -> new JavelinItem(TFToolMaterials.FIERY, new Item.Properties().attributes(JavelinItem.createAttributes(TFToolMaterials.FIERY, 1.0f, -2.0f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.IGNIT)).rarity(Rarity.UNCOMMON).fireResistant()));
	}

	public static void registerPike(DeferredRegister.Items items) {
		register(AAItemTags.PIKES, items, "ironwood_pike", "c:ingots/ironwood", () -> new PikeItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(PikeItem.createAttributes(TFToolMaterials.IRONWOOD, 3.0f, -2.8f))));
		register(AAItemTags.PIKES, items, "steeleaf_pike", "c:ingots/steeleaf", () -> new PikeItem(TFToolMaterials.STEELEAF, new Item.Properties().attributes(PikeItem.createAttributes(TFToolMaterials.STEELEAF, 3.0f, -2.8f))));
		register(AAItemTags.PIKES, items, "knightmetal_pike", "c:ingots/knightmetal", () -> new PikeItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(PikeItem.createAttributes(TFToolMaterials.KNIGHTMETAL, 3.0f, -2.8f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.KNIGHT))));
		register(AAItemTags.PIKES, items, "fiery_pike", "c:ingots/fiery", () -> new PikeItem(TFToolMaterials.FIERY, new Item.Properties().attributes(PikeItem.createAttributes(TFToolMaterials.FIERY, 3.0f, -2.8f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.IGNIT)).rarity(Rarity.UNCOMMON).fireResistant()));
	}

	public static void registerRapier(DeferredRegister.Items items) {
		register(AAItemTags.RAPIERS, items, "ironwood_rapier", "c:ingots/ironwood", () -> new RapierItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(RapierItem.createAttributes(TFToolMaterials.IRONWOOD, 1.5f, -2.0f))));
		register(AAItemTags.RAPIERS, items, "steeleaf_rapier", "c:ingots/steeleaf", () -> new RapierItem(TFToolMaterials.STEELEAF, new Item.Properties().attributes(RapierItem.createAttributes(TFToolMaterials.STEELEAF, 1.0f, -2.0f))));
		register(AAItemTags.RAPIERS, items, "knightmetal_rapier", "c:ingots/knightmetal", () -> new RapierItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(RapierItem.createAttributes(TFToolMaterials.KNIGHTMETAL, 1.0f, -2.0f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.KNIGHT))));
		register(AAItemTags.RAPIERS, items, "fiery_rapier", "c:ingots/fiery", () -> new RapierItem(TFToolMaterials.FIERY, new Item.Properties().attributes(RapierItem.createAttributes(TFToolMaterials.FIERY, 1.0f, -2.0f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.IGNIT)).rarity(Rarity.UNCOMMON).fireResistant()));
	}

	public static void registerHalberd(DeferredRegister.Items items) {
		register(AAItemTags.HALBERDS, items, "ironwood_halberd", "c:ingots/ironwood", () -> new HalberdItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(HalberdItem.createAttributes(TFToolMaterials.IRONWOOD, 5.5f, -3.2f))));
		register(AAItemTags.HALBERDS, items, "steeleaf_halberd", "c:ingots/steeleaf", () -> new HalberdItem(TFToolMaterials.STEELEAF, new Item.Properties().attributes(HalberdItem.createAttributes(TFToolMaterials.STEELEAF, 6.5f, -3.2f))));
		register(AAItemTags.HALBERDS, items, "knightmetal_halberd", "c:ingots/knightmetal", () -> new HalberdItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(HalberdItem.createAttributes(TFToolMaterials.KNIGHTMETAL, 6.5f, -3.2f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.KNIGHT))));
		register(AAItemTags.HALBERDS, items, "fiery_halberd", "c:ingots/fiery", () -> new HalberdItem(TFToolMaterials.FIERY, new Item.Properties().attributes(HalberdItem.createAttributes(TFToolMaterials.FIERY, 7.0f, -3.2f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.IGNIT)).rarity(Rarity.UNCOMMON).fireResistant()));
	}

	public static void registerHeavyShield(DeferredRegister.Items items) {
		register(AAItemTags.HEAVY_SHIELDS, items, "ironwood_heavy_shield", "c:ingots/ironwood", () -> new HeavyShieldItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(HeavyShieldItem.createAttributes(TFToolMaterials.IRONWOOD, 2.0f, -2.8f))));
		register(AAItemTags.HEAVY_SHIELDS, items, "steeleaf_heavy_shield", "c:ingots/steeleaf", () -> new HeavyShieldItem(TFToolMaterials.STEELEAF, new Item.Properties().attributes(HeavyShieldItem.createAttributes(TFToolMaterials.STEELEAF, 1.0f, -2.8f))));
		register(AAItemTags.HEAVY_SHIELDS, items, "knightmetal_heavy_shield", "c:ingots/knightmetal", () -> new HeavyShieldItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(HeavyShieldItem.createAttributes(TFToolMaterials.KNIGHTMETAL, 1.0f, -2.8f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.KNIGHT))));
		register(AAItemTags.HEAVY_SHIELDS, items, "fiery_heavy_shield", "c:ingots/fiery", () -> new HeavyShieldItem(TFToolMaterials.FIERY, new Item.Properties().attributes(HeavyShieldItem.createAttributes(TFToolMaterials.FIERY, 1.0f, -2.8f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.IGNIT)).rarity(Rarity.UNCOMMON).fireResistant()));
	}

	public static void registerClaw(DeferredRegister.Items items) {
		register(AAItemTags.CLAW, items, "ironwood_claw", "c:ingots/ironwood", () -> new ClawItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(ClawItem.createAttributes(TFToolMaterials.IRONWOOD, 0.5f, -1.6f))));
		register(AAItemTags.CLAW, items, "steeleaf_claw", "c:ingots/steeleaf", () -> new ClawItem(TFToolMaterials.STEELEAF, new Item.Properties().attributes(ClawItem.createAttributes(TFToolMaterials.STEELEAF, 0.0f, -1.6f))));
		register(AAItemTags.CLAW, items, "knightmetal_claw", "c:ingots/knightmetal", () -> new ClawItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(ClawItem.createAttributes(TFToolMaterials.KNIGHTMETAL, 0.0f, -1.6f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.KNIGHT))));
		register(AAItemTags.CLAW, items, "fiery_claw", "c:ingots/fiery", () -> new ClawItem(TFToolMaterials.FIERY, new Item.Properties().attributes(ClawItem.createAttributes(TFToolMaterials.FIERY, 0.0f, -1.6f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.IGNIT)).rarity(Rarity.UNCOMMON).fireResistant()));
	}

	public static void registerJian(DeferredRegister.Items items) {
		register(AAItemTags.JIANS, items, "ironwood_jian", "c:ingots/ironwood", () -> new JianItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(JianItem.createAttributes(TFToolMaterials.IRONWOOD, 3.5f, -2.8f))));
		register(AAItemTags.JIANS, items, "steeleaf_jian", "c:ingots/steeleaf", () -> new JianItem(TFToolMaterials.STEELEAF, new Item.Properties().attributes(JianItem.createAttributes(TFToolMaterials.STEELEAF, 3.5f, -2.8f))));
		register(AAItemTags.JIANS, items, "knightmetal_jian", "c:ingots/knightmetal", () -> new JianItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(JianItem.createAttributes(TFToolMaterials.KNIGHTMETAL, 3.5f, -2.8f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.KNIGHT))));
		register(AAItemTags.JIANS, items, "fiery_jian", "c:ingots/fiery", () -> new JianItem(TFToolMaterials.FIERY, new Item.Properties().attributes(JianItem.createAttributes(TFToolMaterials.FIERY, 4.0f, -2.8f)).component(AADataComponents.MATERIAL, new MaterialsComponent(AAMaterials.IGNIT)).rarity(Rarity.UNCOMMON).fireResistant()));
	}

	public static Item[] getFlails() {
		return flailItems.stream().map(DeferredItem::get).toArray(Item[]::new);
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
		provider.tag(AAItemTags.KNIGHTMETAL_WEAPON)
			.addOptional(AssortedArmaments.id("knightmetal_rapier"))
			.addOptional(AssortedArmaments.id("knightmetal_pike"))
			.addOptional(AssortedArmaments.id("knightmetal_javelin"))
			.addOptional(AssortedArmaments.id("knightmetal_claw"));
		provider.tag(AAItemTags.KNIGHTMETAL_TOOL)
			.addOptional(AssortedArmaments.id("knightmetal_claymore"))
			.addOptional(AssortedArmaments.id("knightmetal_mace"))
			.addOptional(AssortedArmaments.id("knightmetal_flail"))
			.addOptional(AssortedArmaments.id("knightmetal_halberd"))
			.addOptional(AssortedArmaments.id("knightmetal_heavy_shield"))
			.addOptional(AssortedArmaments.id("knightmetal_jian"));
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
			provider.add("item.assorted_armaments.ironwood_claymore", "铁木大剑");
			provider.add("item.assorted_armaments.steeleaf_claymore", "钢叶大剑");
			provider.add("item.assorted_armaments.knightmetal_claymore", "骑士大剑");
			provider.add("item.assorted_armaments.fiery_claymore", "炽铁大剑");
			provider.add("item.assorted_armaments.ironwood_mace", "铁木钉头锤");
			provider.add("item.assorted_armaments.steeleaf_mace", "钢叶钉头锤");
			provider.add("item.assorted_armaments.knightmetal_mace", "骑士钉头锤");
			provider.add("item.assorted_armaments.fiery_mace", "炽铁钉头锤");
			provider.add("item.assorted_armaments.ironwood_flail", "铁木流星锤");
			provider.add("item.assorted_armaments.steeleaf_flail", "钢叶流星锤");
			provider.add("item.assorted_armaments.knightmetal_flail", "骑士流星锤");
			provider.add("item.assorted_armaments.fiery_flail", "炽铁流星锤");
			provider.add("item.assorted_armaments.ironwood_javelin", "铁木标枪");
			provider.add("item.assorted_armaments.steeleaf_javelin", "钢叶标枪");
			provider.add("item.assorted_armaments.knightmetal_javelin", "骑士标枪");
			provider.add("item.assorted_armaments.fiery_javelin", "炽铁标枪");
			provider.add("item.assorted_armaments.ironwood_pike", "铁木长枪");
			provider.add("item.assorted_armaments.steeleaf_pike", "钢叶长枪");
			provider.add("item.assorted_armaments.knightmetal_pike", "骑士长枪");
			provider.add("item.assorted_armaments.fiery_pike", "炽铁长枪");
			provider.add("item.assorted_armaments.ironwood_rapier", "铁木刺剑");
			provider.add("item.assorted_armaments.steeleaf_rapier", "钢叶刺剑");
			provider.add("item.assorted_armaments.knightmetal_rapier", "骑士刺剑");
			provider.add("item.assorted_armaments.fiery_rapier", "炽铁刺剑");
			provider.add("item.assorted_armaments.ironwood_halberd", "铁木长戟");
			provider.add("item.assorted_armaments.steeleaf_halberd", "钢叶长戟");
			provider.add("item.assorted_armaments.knightmetal_halberd", "骑士长戟");
			provider.add("item.assorted_armaments.fiery_halberd", "炽铁长戟");
			provider.add("item.assorted_armaments.ironwood_heavy_shield", "铁木重型战盾");
			provider.add("item.assorted_armaments.steeleaf_heavy_shield", "钢叶重型战盾");
			provider.add("item.assorted_armaments.knightmetal_heavy_shield", "骑士重型战盾");
			provider.add("item.assorted_armaments.fiery_heavy_shield", "炽铁重型战盾");
			provider.add("item.assorted_armaments.ironwood_claw", "铁木利爪");
			provider.add("item.assorted_armaments.steeleaf_claw", "钢叶利爪");
			provider.add("item.assorted_armaments.knightmetal_claw", "骑士利爪");
			provider.add("item.assorted_armaments.fiery_claw", "炽铁利爪");
			provider.add("item.assorted_armaments.ironwood_jian", "铁木锏");
			provider.add("item.assorted_armaments.steeleaf_jian", "钢叶锏");
			provider.add("item.assorted_armaments.knightmetal_jian", "骑士锏");
			provider.add("item.assorted_armaments.fiery_jian", "炽铁锏");
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
			String components = getEnchantComponents(tuple.getA().getPath());
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
				      "tag": "%s"
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
				    "id": "%s"%s
				  }
				}""".formatted(ID, getRodTag(tuple.getA().getPath()), tuple.getB(), tuple.getA(), components), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.MACES, List.of()).forEach(tuple -> {
			String components = getEnchantComponents(tuple.getA().getPath());
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
				      "tag": "%s"
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
				    "id": "%s"%s
				  }
				}""".formatted(ID, getRodTag(tuple.getA().getPath()), tuple.getB(), tuple.getA(), components), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.FLAILS, List.of()).forEach(tuple -> {
			String components = getEnchantComponents(tuple.getA().getPath());
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
				      "tag": "%s"
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
				    "id": "%s"%s
				  }
				}""".formatted(ID, getRodTag(tuple.getA().getPath()), tuple.getB(), tuple.getA(), components), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.JAVELINS, List.of()).forEach(tuple -> {
			String components = getEnchantComponents(tuple.getA().getPath());
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
				    "id": "%s"%s
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA(), components), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.PIKES, List.of()).forEach(tuple -> {
			String components = getEnchantComponents(tuple.getA().getPath());
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
				      "tag": "%s"
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
				    "id": "%s"%s
				  }
				}""".formatted(ID, getRodTag(tuple.getA().getPath()), tuple.getB(), tuple.getA(), components), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.RAPIERS, List.of()).forEach(tuple -> {
			String components = getEnchantComponents(tuple.getA().getPath());
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
				      "tag": "%s"
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
				    "id": "%s"%s
				  }
				}""".formatted(ID, getRodTag(tuple.getA().getPath()), tuple.getB(), tuple.getA(), components), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.HALBERDS, List.of()).forEach(tuple -> {
			String components = getEnchantComponents(tuple.getA().getPath());
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
				      "tag": "%s"
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
				    "id": "%s"%s
				  }
				}""".formatted(ID, getRodTag(tuple.getA().getPath()), tuple.getB(), tuple.getA(), components), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.HEAVY_SHIELDS, List.of()).forEach(tuple -> {
			String components = getEnchantComponents(tuple.getA().getPath());
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
				    "id": "%s"%s
				  }
				}""".formatted(ID, tuple.getB(), tuple.getA(), components), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.CLAW, List.of()).forEach(tuple -> {
			String components = getEnchantComponents(tuple.getA().getPath());
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
				      "tag": "%s"
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
				    "id": "%s"%s
				  }
				}""".formatted(ID, getRodTag(tuple.getA().getPath()), tuple.getB(), tuple.getA(), components), JsonObject.class));
		});
		tags.getOrDefault(AAItemTags.JIANS, List.of()).forEach(tuple -> {
			String components = getEnchantComponents(tuple.getA().getPath());
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
				      "tag": "%s"
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
				    "id": "%s"%s
				  }
				}""".formatted(ID, getRodTag(tuple.getA().getPath()), tuple.getB(), tuple.getA(), components), JsonObject.class));
		});
	}

	public static String getRodTag(String path) {
		if (path.contains("fiery")) {
			return "c:rods/blaze";
		}
		return "c:rods/wooden";
	}

	public static String getEnchantComponents(String path) {
		if (path.contains("ironwood")) {
			return ", \"components\": {\"minecraft:enchantments\": {\"minecraft:knockback\": 1}}";
		}
		if (path.contains("steeleaf")) {
			return ", \"components\": {\"minecraft:enchantments\": {\"minecraft:looting\": 2}}";
		}
		return "";
	}
}
