package team.leomc.assortedarmaments.trait;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import team.leomc.assortedarmaments.network.UpdateBlockAbilityPayload;
import team.leomc.assortedarmaments.registry.AADataAttachments;

public class KnockWeaponTrait extends WeaponTrait {
	public static void onShieldBlock(LivingEntity blocker, ServerPlayer serverPlayer) {
		blocker.stopUsingItem();
		blocker.setData(AADataAttachments.BLOCKING_DISABLED_TIME, Math.max(blocker.getData(AADataAttachments.BLOCKING_DISABLED_TIME), 100));
		blocker.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 1));
		PacketDistributor.sendToPlayer(serverPlayer, new UpdateBlockAbilityPayload(true));
	}
}
