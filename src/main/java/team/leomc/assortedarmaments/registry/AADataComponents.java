package team.leomc.assortedarmaments.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.integration.MaterialsComponent;
import team.leomc.assortedarmaments.trait.WeaponTraitsComponent;

import java.util.function.Supplier;

public class AADataComponents {
	public static final DeferredRegister<DataComponentType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, AssortedArmaments.ID);

	public static final Supplier<DataComponentType<MaterialsComponent>> MATERIAL = TYPES.register("material",
		() -> DataComponentType.<MaterialsComponent>builder().persistent(MaterialsComponent.CODEC).networkSynchronized(MaterialsComponent.STREAM_CODEC).build()
	);

	public static final Supplier<DataComponentType<WeaponTraitsComponent>> WEAPON_TRAITS =
		TYPES.register("weapon_traits", () ->
			DataComponentType.<WeaponTraitsComponent>builder()
				.persistent(WeaponTraitsComponent.CODEC)
				.networkSynchronized(WeaponTraitsComponent.STREAM_CODEC)
				.build()
		);

	public static void register(IEventBus eventBus) {
		AAMaterials.init();
		TYPES.register(eventBus);
	}
}
