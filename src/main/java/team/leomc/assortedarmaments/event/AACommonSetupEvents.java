package team.leomc.assortedarmaments.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

@EventBusSubscriber(modid = AssortedArmaments.ID)
public class AACommonSetupEvents {
	@SubscribeEvent
	public static void onNewRegistry(NewRegistryEvent event) {
		event.register(AAWeaponTraits.WEAPON_TRAIT_REGISTRY);
	}
}
