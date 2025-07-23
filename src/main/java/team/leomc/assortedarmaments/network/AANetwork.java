package team.leomc.assortedarmaments.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import team.leomc.assortedarmaments.AssortedArmaments;

@EventBusSubscriber(modid = AssortedArmaments.ID)
public class AANetwork {
	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1");
		registrar.playToClient(
			UpdateBlockAbilityPayload.TYPE,
			UpdateBlockAbilityPayload.STREAM_CODEC,
			new DirectionalPayloadHandler<>(
				UpdateBlockAbilityPayload::handle,
				UpdateBlockAbilityPayload::handle
			)
		);
		registrar.commonToServer(
			RemoveJavelinPayload.TYPE,
			RemoveJavelinPayload.STREAM_CODEC,
			new DirectionalPayloadHandler<>(
				RemoveJavelinPayload::handle,
				RemoveJavelinPayload::handle
			)
		);
	}
}
