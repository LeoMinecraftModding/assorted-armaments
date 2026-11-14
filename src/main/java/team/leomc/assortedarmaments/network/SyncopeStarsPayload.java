package team.leomc.assortedarmaments.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.registry.AAParticleTypes;

public record SyncopeStarsPayload(int entityId, int ticks) implements CustomPacketPayload {
	public static final Type<SyncopeStarsPayload> TYPE = new Type<>(AssortedArmaments.id("syncope_stars"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SyncopeStarsPayload> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.VAR_INT,
		SyncopeStarsPayload::entityId,
		ByteBufCodecs.VAR_INT,
		SyncopeStarsPayload::ticks,
		SyncopeStarsPayload::new
	);

	public static void handle(SyncopeStarsPayload payload, IPayloadContext context) {
		context.enqueueWork(() -> {
			if (context.player().level().getEntity(payload.entityId()) instanceof LivingEntity entity) {
				entity.level().addParticle(AAParticleTypes.SYNCOPE_STAR.get(),
					entity.getX(), entity.getY() + entity.getBbHeight(), entity.getZ(),
					entity.getId(), payload.ticks(), 0.0D);
			}
		});
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
