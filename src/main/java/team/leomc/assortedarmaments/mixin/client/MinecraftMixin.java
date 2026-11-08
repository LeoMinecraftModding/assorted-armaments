package team.leomc.assortedarmaments.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.leomc.assortedarmaments.network.SwingAttackPayload;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;

@OnlyIn(Dist.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	public LocalPlayer player;

	@Shadow
	public HitResult hitResult;

	@Inject(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;"))
	private void aa$sendSwingAttack(CallbackInfoReturnable<Boolean> cir) {
		if (player == null || hitResult == null || hitResult.getType() == HitResult.Type.ENTITY) {
			return;
		}
		if (WeaponTraitHelper.hasTrait(AAWeaponTraits.STRONG_SWEEP, player.getMainHandItem())) {
			PacketDistributor.sendToServer(new SwingAttackPayload());
		}
	}
}
