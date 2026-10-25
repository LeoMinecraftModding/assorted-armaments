package team.leomc.assortedarmaments.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.integration.aquaculture.AquacultureHelper;
import team.leomc.assortedarmaments.integration.eternalstarlight.EternalStarlightHelper;
import team.leomc.assortedarmaments.integration.twilightforest.TwilightForestHelper;
import team.leomc.assortedarmaments.item.*;

public class AAItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AssortedArmaments.ID);

	// claymores
	public static final DeferredItem<ClaymoreItem> WOODEN_CLAYMORE = ITEMS.register("wooden_claymore", () -> new ClaymoreItem(Tiers.WOOD, new Item.Properties().attributes(ClaymoreItem.createAttributes(Tiers.WOOD, 4.5f, -3f))));
	public static final DeferredItem<ClaymoreItem> STONE_CLAYMORE = ITEMS.register("stone_claymore", () -> new ClaymoreItem(Tiers.STONE, new Item.Properties().attributes(ClaymoreItem.createAttributes(Tiers.STONE, 5f, -3f))));
	public static final DeferredItem<ClaymoreItem> IRON_CLAYMORE = ITEMS.register("iron_claymore", () -> new ClaymoreItem(Tiers.IRON, new Item.Properties().attributes(ClaymoreItem.createAttributes(Tiers.IRON, 5.5f, -3f))));
	public static final DeferredItem<ClaymoreItem> GOLDEN_CLAYMORE = ITEMS.register("golden_claymore", () -> new ClaymoreItem(Tiers.GOLD, new Item.Properties().attributes(ClaymoreItem.createAttributes(Tiers.GOLD, 4.5f, -3f))));
	public static final DeferredItem<ClaymoreItem> DIAMOND_CLAYMORE = ITEMS.register("diamond_claymore", () -> new ClaymoreItem(Tiers.DIAMOND, new Item.Properties().attributes(ClaymoreItem.createAttributes(Tiers.DIAMOND, 6f, -3f))));
	public static final DeferredItem<ClaymoreItem> NETHERITE_CLAYMORE = ITEMS.register("netherite_claymore", () -> new ClaymoreItem(Tiers.NETHERITE, new Item.Properties().attributes(ClaymoreItem.createAttributes(Tiers.NETHERITE, 6f, -3f)).fireResistant()));

	static {
		EternalStarlightHelper.registerClaymores(ITEMS);
		TwilightForestHelper.registerClaymores(ITEMS);
		AquacultureHelper.registerClaymores(ITEMS);
	}

	// maces
	public static final DeferredItem<MaceItem> WOODEN_MACE = ITEMS.register("wooden_mace", () -> new MaceItem(Tiers.WOOD, new Item.Properties().attributes(MaceItem.createAttributes(Tiers.WOOD, 4f, -2.9f))));
	public static final DeferredItem<MaceItem> STONE_MACE = ITEMS.register("stone_mace", () -> new MaceItem(Tiers.STONE, new Item.Properties().attributes(MaceItem.createAttributes(Tiers.STONE, 4f, -2.9f))));
	public static final DeferredItem<MaceItem> IRON_MACE = ITEMS.register("iron_mace", () -> new MaceItem(Tiers.IRON, new Item.Properties().attributes(MaceItem.createAttributes(Tiers.IRON, 4f, -2.9f))));
	public static final DeferredItem<MaceItem> GOLDEN_MACE = ITEMS.register("golden_mace", () -> new MaceItem(Tiers.GOLD, new Item.Properties().attributes(MaceItem.createAttributes(Tiers.GOLD, 4f, -2.9f))));
	public static final DeferredItem<MaceItem> DIAMOND_MACE = ITEMS.register("diamond_mace", () -> new MaceItem(Tiers.DIAMOND, new Item.Properties().attributes(MaceItem.createAttributes(Tiers.DIAMOND, 4.5f, -2.9f))));
	public static final DeferredItem<MaceItem> NETHERITE_MACE = ITEMS.register("netherite_mace", () -> new MaceItem(Tiers.NETHERITE, new Item.Properties().attributes(MaceItem.createAttributes(Tiers.NETHERITE, 4.5f, -2.9f)).fireResistant()));

	static {
//		EternalStarlightHelper.registerMace(ITEMS);
		TwilightForestHelper.registerMace(ITEMS);
		AquacultureHelper.registerMace(ITEMS);
	}

	// flails
	public static final DeferredItem<FlailItem> WOODEN_FLAIL = ITEMS.register("wooden_flail", () -> new FlailItem(Tiers.WOOD, new Item.Properties().attributes(FlailItem.createAttributes(Tiers.WOOD, 4.5f, -3.2f))));
	public static final DeferredItem<FlailItem> STONE_FLAIL = ITEMS.register("stone_flail", () -> new FlailItem(Tiers.STONE, new Item.Properties().attributes(FlailItem.createAttributes(Tiers.STONE, 4.5f, -3.2f))));
	public static final DeferredItem<FlailItem> IRON_FLAIL = ITEMS.register("iron_flail", () -> new FlailItem(Tiers.IRON, new Item.Properties().attributes(FlailItem.createAttributes(Tiers.IRON, 4.5f, -3.2f))));
	public static final DeferredItem<FlailItem> GOLDEN_FLAIL = ITEMS.register("golden_flail", () -> new FlailItem(Tiers.GOLD, new Item.Properties().attributes(FlailItem.createAttributes(Tiers.GOLD, 4.5f, -3.2f))));
	public static final DeferredItem<FlailItem> DIAMOND_FLAIL = ITEMS.register("diamond_flail", () -> new FlailItem(Tiers.DIAMOND, new Item.Properties().attributes(FlailItem.createAttributes(Tiers.DIAMOND, 5f, -3.2f))));
	public static final DeferredItem<FlailItem> NETHERITE_FLAIL = ITEMS.register("netherite_flail", () -> new FlailItem(Tiers.NETHERITE, new Item.Properties().attributes(FlailItem.createAttributes(Tiers.NETHERITE, 5f, -3.2f)).fireResistant()));

	static {
//		EternalStarlightHelper.registerFlail(ITEMS);
		TwilightForestHelper.registerFlail(ITEMS);
		AquacultureHelper.registerFlail(ITEMS);
	}

	// javelins
	public static final DeferredItem<JavelinItem> WOODEN_JAVELIN = ITEMS.register("wooden_javelin", () -> new JavelinItem(Tiers.WOOD, new Item.Properties().attributes(JavelinItem.createAttributes(Tiers.WOOD, 2f, -2f))));
	public static final DeferredItem<JavelinItem> STONE_JAVELIN = ITEMS.register("stone_javelin", () -> new JavelinItem(Tiers.STONE, new Item.Properties().attributes(JavelinItem.createAttributes(Tiers.STONE, 2f, -2f))));
	public static final DeferredItem<JavelinItem> IRON_JAVELIN = ITEMS.register("iron_javelin", () -> new JavelinItem(Tiers.IRON, new Item.Properties().attributes(JavelinItem.createAttributes(Tiers.IRON, 1.5f, -2f))));
	public static final DeferredItem<JavelinItem> GOLDEN_JAVELIN = ITEMS.register("golden_javelin", () -> new JavelinItem(Tiers.GOLD, new Item.Properties().attributes(JavelinItem.createAttributes(Tiers.GOLD, 2f, -2f))));
	public static final DeferredItem<JavelinItem> DIAMOND_JAVELIN = ITEMS.register("diamond_javelin", () -> new JavelinItem(Tiers.DIAMOND, new Item.Properties().attributes(JavelinItem.createAttributes(Tiers.DIAMOND, 1f, -2f))));
	public static final DeferredItem<JavelinItem> NETHERITE_JAVELIN = ITEMS.register("netherite_javelin", () -> new JavelinItem(Tiers.NETHERITE, new Item.Properties().attributes(JavelinItem.createAttributes(Tiers.NETHERITE, 1f, -2f)).fireResistant()));

	static {
//		EternalStarlightHelper.registerJavelin(ITEMS);
		TwilightForestHelper.registerJavelin(ITEMS);
		AquacultureHelper.registerJavelin(ITEMS);
	}

	// pikes
	public static final DeferredItem<PikeItem> WOODEN_PIKE = ITEMS.register("wooden_pike", () -> new PikeItem(Tiers.WOOD, new Item.Properties().attributes(PikeItem.createAttributes(Tiers.WOOD, 3f, -2.8f))));
	public static final DeferredItem<PikeItem> STONE_PIKE = ITEMS.register("stone_pike", () -> new PikeItem(Tiers.STONE, new Item.Properties().attributes(PikeItem.createAttributes(Tiers.STONE, 3f, -2.8f))));
	public static final DeferredItem<PikeItem> IRON_PIKE = ITEMS.register("iron_pike", () -> new PikeItem(Tiers.IRON, new Item.Properties().attributes(PikeItem.createAttributes(Tiers.IRON, 3f, -2.8f))));
	public static final DeferredItem<PikeItem> GOLDEN_PIKE = ITEMS.register("golden_pike", () -> new PikeItem(Tiers.GOLD, new Item.Properties().attributes(PikeItem.createAttributes(Tiers.GOLD, 3f, -2.8f))));
	public static final DeferredItem<PikeItem> DIAMOND_PIKE = ITEMS.register("diamond_pike", () -> new PikeItem(Tiers.DIAMOND, new Item.Properties().attributes(PikeItem.createAttributes(Tiers.DIAMOND, 3f, -2.8f))));
	public static final DeferredItem<PikeItem> NETHERITE_PIKE = ITEMS.register("netherite_pike", () -> new PikeItem(Tiers.NETHERITE, new Item.Properties().attributes(PikeItem.createAttributes(Tiers.NETHERITE, 3f, -2.8f)).fireResistant()));

	static {
//		EternalStarlightHelper.registerPike(ITEMS);
		TwilightForestHelper.registerPike(ITEMS);
		AquacultureHelper.registerPike(ITEMS);
	}

	// rapiers
	public static final DeferredItem<RapierItem> WOODEN_RAPIER = ITEMS.register("wooden_rapier", () -> new RapierItem(Tiers.WOOD, new Item.Properties().attributes(RapierItem.createAttributes(Tiers.WOOD, 2f, -2f))));
	public static final DeferredItem<RapierItem> STONE_RAPIER = ITEMS.register("stone_rapier", () -> new RapierItem(Tiers.STONE, new Item.Properties().attributes(RapierItem.createAttributes(Tiers.STONE, 2f, -2f))));
	public static final DeferredItem<RapierItem> IRON_RAPIER = ITEMS.register("iron_rapier", () -> new RapierItem(Tiers.IRON, new Item.Properties().attributes(RapierItem.createAttributes(Tiers.IRON, 1.5f, -2f))));
	public static final DeferredItem<RapierItem> GOLDEN_RAPIER = ITEMS.register("golden_rapier", () -> new RapierItem(Tiers.GOLD, new Item.Properties().attributes(RapierItem.createAttributes(Tiers.GOLD, 2f, -2f))));
	public static final DeferredItem<RapierItem> DIAMOND_RAPIER = ITEMS.register("diamond_rapier", () -> new RapierItem(Tiers.DIAMOND, new Item.Properties().attributes(RapierItem.createAttributes(Tiers.DIAMOND, 1f, -2f))));
	public static final DeferredItem<RapierItem> NETHERITE_RAPIER = ITEMS.register("netherite_rapier", () -> new RapierItem(Tiers.NETHERITE, new Item.Properties().attributes(RapierItem.createAttributes(Tiers.NETHERITE, 1f, -2f)).fireResistant()));

	static {
//		EternalStarlightHelper.registerRapier(ITEMS);
		TwilightForestHelper.registerRapier(ITEMS);
		AquacultureHelper.registerRapier(ITEMS);
	}

	// halberds
	public static final DeferredItem<HalberdItem> WOODEN_HALBERD = ITEMS.register("wooden_halberd", () -> new HalberdItem(Tiers.WOOD, new Item.Properties().attributes(HalberdItem.createAttributes(Tiers.WOOD, 5f, -3.2f))));
	public static final DeferredItem<HalberdItem> STONE_HALBERD = ITEMS.register("stone_halberd", () -> new HalberdItem(Tiers.STONE, new Item.Properties().attributes(HalberdItem.createAttributes(Tiers.STONE, 5.5f, -3.2f))));
	public static final DeferredItem<HalberdItem> IRON_HALBERD = ITEMS.register("iron_halberd", () -> new HalberdItem(Tiers.IRON, new Item.Properties().attributes(HalberdItem.createAttributes(Tiers.IRON, 6f, -3.2f))));
	public static final DeferredItem<HalberdItem> GOLDEN_HALBERD = ITEMS.register("golden_halberd", () -> new HalberdItem(Tiers.GOLD, new Item.Properties().attributes(HalberdItem.createAttributes(Tiers.GOLD, 5f, -3.2f))));
	public static final DeferredItem<HalberdItem> DIAMOND_HALBERD = ITEMS.register("diamond_halberd", () -> new HalberdItem(Tiers.DIAMOND, new Item.Properties().attributes(HalberdItem.createAttributes(Tiers.DIAMOND, 6.5f, -3.2f))));
	public static final DeferredItem<HalberdItem> NETHERITE_HALBERD = ITEMS.register("netherite_halberd", () -> new HalberdItem(Tiers.NETHERITE, new Item.Properties().attributes(HalberdItem.createAttributes(Tiers.NETHERITE, 7f, -3.2f)).fireResistant()));

	static {
//		EternalStarlightHelper.registerHalberd(ITEMS);
		TwilightForestHelper.registerHalberd(ITEMS);
		AquacultureHelper.registerHalberd(ITEMS);
	}

	// heavy shields
	public static final DeferredItem<HeavyShieldItem> WOODEN_HEAVY_SHIELD = ITEMS.register("wooden_heavy_shield", () -> new HeavyShieldItem(Tiers.WOOD, new Item.Properties().attributes(HeavyShieldItem.createAttributes(Tiers.WOOD, 2f, -2.8f))));
	public static final DeferredItem<HeavyShieldItem> STONE_HEAVY_SHIELD = ITEMS.register("stone_heavy_shield", () -> new HeavyShieldItem(Tiers.STONE, new Item.Properties().attributes(HeavyShieldItem.createAttributes(Tiers.STONE, 1.5f, -2.8f))));
	public static final DeferredItem<HeavyShieldItem> IRON_HEAVY_SHIELD = ITEMS.register("iron_heavy_shield", () -> new HeavyShieldItem(Tiers.IRON, new Item.Properties().attributes(HeavyShieldItem.createAttributes(Tiers.IRON, 1.5f, -2.8f))));
	public static final DeferredItem<HeavyShieldItem> GOLDEN_HEAVY_SHIELD = ITEMS.register("golden_heavy_shield", () -> new HeavyShieldItem(Tiers.GOLD, new Item.Properties().attributes(HeavyShieldItem.createAttributes(Tiers.GOLD, 2f, -2.8f))));
	public static final DeferredItem<HeavyShieldItem> DIAMOND_HEAVY_SHIELD = ITEMS.register("diamond_heavy_shield", () -> new HeavyShieldItem(Tiers.DIAMOND, new Item.Properties().attributes(HeavyShieldItem.createAttributes(Tiers.DIAMOND, 1f, -2.8f))));
	public static final DeferredItem<HeavyShieldItem> NETHERITE_HEAVY_SHIELD = ITEMS.register("netherite_heavy_shield", () -> new HeavyShieldItem(Tiers.NETHERITE, new Item.Properties().attributes(HeavyShieldItem.createAttributes(Tiers.NETHERITE, 1f, -2.8f)).fireResistant()));

	static {
//		EternalStarlightHelper.registerHeavyShield(ITEMS);
		TwilightForestHelper.registerHeavyShield(ITEMS);
		AquacultureHelper.registerHeavyShield(ITEMS);
	}

	// claws
	public static final DeferredItem<ClawItem> WOODEN_CLAW = ITEMS.register("wooden_claw", () -> new ClawItem(Tiers.WOOD, new Item.Properties().attributes(ClawItem.createAttributes(Tiers.WOOD, 1.5f, -1.6f))));
	public static final DeferredItem<ClawItem> STONE_CLAW = ITEMS.register("stone_claw", () -> new ClawItem(Tiers.STONE, new Item.Properties().attributes(ClawItem.createAttributes(Tiers.STONE, 1f, -1.6f))));
	public static final DeferredItem<ClawItem> IRON_CLAW = ITEMS.register("iron_claw", () -> new ClawItem(Tiers.IRON, new Item.Properties().attributes(ClawItem.createAttributes(Tiers.IRON, 0.5f, -1.6f))));
	public static final DeferredItem<ClawItem> GOLDEN_CLAW = ITEMS.register("golden_claw", () -> new ClawItem(Tiers.GOLD, new Item.Properties().attributes(ClawItem.createAttributes(Tiers.GOLD, 1.5f, -1.6f))));
	public static final DeferredItem<ClawItem> DIAMOND_CLAW = ITEMS.register("diamond_claw", () -> new ClawItem(Tiers.DIAMOND, new Item.Properties().attributes(ClawItem.createAttributes(Tiers.DIAMOND, 0.0f, -1.6f))));
	public static final DeferredItem<ClawItem> NETHERITE_CLAW = ITEMS.register("netherite_claw", () -> new ClawItem(Tiers.NETHERITE, new Item.Properties().attributes(ClawItem.createAttributes(Tiers.NETHERITE, 0.0f, -1.6f)).fireResistant()));

	static {
//		EternalStarlightHelper.registerClaw(ITEMS);
		TwilightForestHelper.registerClaw(ITEMS);
		AquacultureHelper.registerClaw(ITEMS);
	}

	// jian
	public static final DeferredItem<JianItem> WOODEN_JIAN = ITEMS.register("wooden_jian", () -> new JianItem(Tiers.WOOD, new Item.Properties().attributes(JianItem.createAttributes(Tiers.WOOD, 3.5f, -2.8f))));
	public static final DeferredItem<JianItem> STONE_JIAN = ITEMS.register("stone_jian", () -> new JianItem(Tiers.STONE, new Item.Properties().attributes(JianItem.createAttributes(Tiers.STONE, 3.5f, -2.8f))));
	public static final DeferredItem<JianItem> IRON_JIAN = ITEMS.register("iron_jian", () -> new JianItem(Tiers.IRON, new Item.Properties().attributes(JianItem.createAttributes(Tiers.IRON, 3.5f, -2.8f))));
	public static final DeferredItem<JianItem> GOLDEN_JIAN = ITEMS.register("golden_jian", () -> new JianItem(Tiers.GOLD, new Item.Properties().attributes(JianItem.createAttributes(Tiers.GOLD, 3.5f, -2.8f))));
	public static final DeferredItem<JianItem> DIAMOND_JIAN = ITEMS.register("diamond_jian", () -> new JianItem(Tiers.DIAMOND, new Item.Properties().attributes(JianItem.createAttributes(Tiers.DIAMOND, 3.5f, -2.8f))));
	public static final DeferredItem<JianItem> NETHERITE_JIAN = ITEMS.register("netherite_jian", () -> new JianItem(Tiers.NETHERITE, new Item.Properties().attributes(JianItem.createAttributes(Tiers.NETHERITE, 4.0f, -2.8f)).fireResistant()));

	static {
//		EternalStarlightHelper.registerJian(ITEMS);
		TwilightForestHelper.registerJian(ITEMS);
		AquacultureHelper.registerJian(ITEMS);
	}
}
