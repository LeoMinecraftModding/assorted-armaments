package team.leomc.assortedarmaments.network;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.registry.AADataAttachments;

public record UpdateBlockAbilityPayload(boolean disabled) implements CustomPacketPayload {
	public static final Type<UpdateBlockAbilityPayload> TYPE = new Type<>(AssortedArmaments.id("update_block_ability"));
	public static final Codec<UpdateBlockAbilityPayload> CODEC = Codec.BOOL.xmap(UpdateBlockAbilityPayload::new, UpdateBlockAbilityPayload::disabled);
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateBlockAbilityPayload> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

	public static void handle(UpdateBlockAbilityPayload payload, IPayloadContext context) {
		context.player().setData(AADataAttachments.BLOCKING_DISABLED_TIME, payload.disabled() ? 10 : 0);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
