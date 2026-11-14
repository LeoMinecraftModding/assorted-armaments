package team.leomc.assortedarmaments.integration;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import team.leomc.assortedarmaments.trait.WeaponTrait;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public record AACompatMaterial(
	String prefix,
	Supplier<Tier> tier,
	String ingotTag,
	String rodTag,
	String components,
	UnaryOperator<Item.Properties> extra,
	AACompatStyle style,
	List<Holder<WeaponTrait>> traits,
	boolean craftable
) {
	public AACompatMaterial(String prefix, Supplier<Tier> tier, String ingotTag, String rodTag, String components, UnaryOperator<Item.Properties> extra, AACompatStyle style, List<Holder<WeaponTrait>> traits) {
		this(prefix, tier, ingotTag, rodTag, components, extra, style, traits, true);
	}

	public AACompatMaterial(String prefix, Supplier<Tier> tier, String ingotTag, String rodTag, String components, UnaryOperator<Item.Properties> extra, AACompatStyle style) {
		this(prefix, tier, ingotTag, rodTag, components, extra, style, List.of(), true);
	}
}
