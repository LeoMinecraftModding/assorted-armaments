package team.leomc.assortedarmaments.integration;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import team.leomc.assortedarmaments.data.gen.model.AAItemModelProvider;
import team.leomc.assortedarmaments.item.*;
import team.leomc.assortedarmaments.tags.AAItemTags;
import team.leomc.assortedarmaments.trait.WeaponTrait;

import java.util.List;

public enum AAWeaponType {
	CLAYMORE(AAItemTags.CLAYMORES, ClaymoreItem::new, ClaymoreItem::new, ClaymoreItem::createAttributes,
		List.of(new KeySpec('#', KeyType.ROD), new KeySpec('X', KeyType.INGOT)),
			" XX",
			"XXX",
			"#X "),
	MACE(AAItemTags.MACES, MaceItem::new, MaceItem::new, MaceItem::createAttributes,
		List.of(new KeySpec('#', KeyType.ROD), new KeySpec('X', KeyType.INGOT)),
			" XX",
			" XX",
			"#  "),
	FLAIL(AAItemTags.FLAILS, FlailItem::new, FlailItem::new, FlailItem::createAttributes,
		List.of(new KeySpec('#', KeyType.ROD), new KeySpec('X', KeyType.INGOT), new KeySpec('C', KeyType.CHAINS)),
			" CC",
			"XXC",
			"XX#"),
	JAVELIN(AAItemTags.JAVELINS, JavelinItem::new, JavelinItem::new, JavelinItem::createAttributes,
		List.of(new KeySpec('X', KeyType.INGOT)),
			"  X",
			" X ",
			"X  "),
	PIKE(AAItemTags.PIKES, PikeItem::new, PikeItem::new, PikeItem::createAttributes,
		List.of(new KeySpec('#', KeyType.ROD), new KeySpec('X', KeyType.INGOT)),
			"  X",
			" # ",
			"#  "),
	RAPIER(AAItemTags.RAPIERS, RapierItem::new, RapierItem::new, RapierItem::createAttributes,
		List.of(new KeySpec('#', KeyType.ROD), new KeySpec('X', KeyType.INGOT)),
			"  X",
			" X ",
			"## "),
	HALBERD(AAItemTags.HALBERDS, HalberdItem::new, HalberdItem::new, HalberdItem::createAttributes,
		List.of(new KeySpec('#', KeyType.ROD), new KeySpec('X', KeyType.INGOT)),
			"  X",
			" #X",
			"#  "),
	HEAVY_SHIELD(AAItemTags.HEAVY_SHIELDS, HeavyShieldItem::new, HeavyShieldItem::new, HeavyShieldItem::createAttributes,
		List.of(new KeySpec('#', KeyType.SHIELD), new KeySpec('X', KeyType.INGOT)),
			"XXX",
			"X#X",
			" X "),
	CLAW(AAItemTags.CLAW, ClawItem::new, ClawItem::new, ClawItem::createAttributes,
		List.of(new KeySpec('#', KeyType.ROD), new KeySpec('X', KeyType.INGOT)),
			"X X",
			"###",
			"   "),
	JIAN(AAItemTags.JIANS, JianItem::new, JianItem::new, JianItem::createAttributes,
		List.of(new KeySpec('#', KeyType.ROD), new KeySpec('X', KeyType.INGOT)),
			"  #",
			" X ",
			"X  ");

	private final TagKey<Item> tag;
	private final ItemFactory itemFactory;
	private final TraitedItemFactory traitedItemFactory;
	private final AttributesFactory attributesFactory;
	private final List<KeySpec> keys;
	private final String[] pattern;

	AAWeaponType(TagKey<Item> tag, ItemFactory itemFactory, TraitedItemFactory traitedItemFactory,
			   AttributesFactory attributesFactory, List<KeySpec> keys, String... pattern) {
		this.tag = tag;
		this.itemFactory = itemFactory;
		this.traitedItemFactory = traitedItemFactory;
		this.attributesFactory = attributesFactory;
		this.keys = keys;
		this.pattern = pattern;
	}

	public TagKey<Item> tag() {
		return tag;
	}

	public List<KeySpec> keys() {
		return keys;
	}

	public String[] pattern() {
		return pattern;
	}

	public Item create(Tier tier, Item.Properties properties, List<Holder<WeaponTrait>> traits) {
		return traits.isEmpty() ? itemFactory.create(tier, properties) : traitedItemFactory.create(tier, properties, traits);
	}

	public ItemAttributeModifiers attributes(Tier tier, float damage, float speed) {
		return attributesFactory.create(tier, damage, speed);
	}

	public void registerModels(AAItemModelProvider provider, ResourceLocation id, AACompatStyle style) {
		switch (this) {
			case CLAYMORE -> {
				provider.claymore(id);
				provider.inventoryHandheld(id);
			}
			case MACE -> {
				provider.mace(id, style);
				provider.inventoryHandheld(id);
			}
			case FLAIL -> {
				provider.flail(id, style);
				provider.spinningFlail(id, style);
				provider.thrownFlail(id, style);
			}
			case JAVELIN -> {
				provider.javelin(id);
				provider.thrownJavelin(id, style);
			}
			case PIKE, HALBERD -> {
				provider.pike(id);
				provider.inventoryHandheld(id);
			}
			case RAPIER -> provider.rapier(id);
			case HEAVY_SHIELD -> {
				provider.heavyShield(id, style);
				provider.inventoryHandheld(id);
			}
			case CLAW -> provider.claw(id);
			case JIAN -> {
				provider.jian(id, style);
				provider.inventoryHandheld(id);
			}
		}
	}

	public enum KeyType {
		INGOT,
		ROD,
		CHAINS,
		SHIELD
	}

	public record KeySpec(char symbol, KeyType type) {
	}

	@FunctionalInterface
	public interface ItemFactory {
		Item create(Tier tier, Item.Properties properties);
	}

	@FunctionalInterface
	public interface TraitedItemFactory {
		Item create(Tier tier, Item.Properties properties, List<Holder<WeaponTrait>> traits);
	}

	@FunctionalInterface
	public interface AttributesFactory {
		ItemAttributeModifiers create(Tier tier, float damage, float speed);
	}
}
