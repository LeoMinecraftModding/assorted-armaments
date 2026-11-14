package team.leomc.assortedarmaments.integration;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import team.leomc.assortedarmaments.trait.WeaponTrait;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public record AAWeaponDefinition(
	AAWeaponType type,
	AACompatMaterial material,
	float damage,
	float speed,
	String name,
	String zhName,
	List<Holder<WeaponTrait>> traits
) {
	public AAWeaponDefinition(AAWeaponType type, AACompatMaterial material, float damage, float speed, String name, String zhName) {
		this(type, material, damage, speed, name, zhName, List.of());
	}

	public Supplier<Item> factory() {
		return () -> {
			Tier tier = material.tier().get();
			Item.Properties properties = new Item.Properties().attributes(type.attributes(tier, damage, speed));
			properties = material.extra().apply(properties);
			List<Holder<WeaponTrait>> allTraits = Stream.concat(material.traits().stream(), traits.stream()).toList();
			return type.create(tier, properties, allTraits);
		};
	}
}
