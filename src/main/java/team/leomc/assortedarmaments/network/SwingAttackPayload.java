package team.leomc.assortedarmaments.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.trait.StrongSweepWeaponTrait;

public record SwingAttackPayload() implements CustomPacketPayload {
	public static final Type<SwingAttackPayload> TYPE = new Type<>(AssortedArmaments.id("swing_attack"));
	public static final StreamCodec<ByteBuf, SwingAttackPayload> STREAM_CODEC = StreamCodec.unit(new SwingAttackPayload());

	public static void handle(SwingAttackPayload payload, IPayloadContext context) {
		if (context.player() instanceof ServerPlayer player) {
			StrongSweepWeaponTrait.performSweepAttack(player);
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
