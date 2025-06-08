package team.leomc.assortedarmaments.mixin.client;

import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.leomc.assortedarmaments.tags.AAItemTags;

@OnlyIn(Dist.CLIENT)
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
	@Shadow
	public Input input;

	@Shadow
	public abstract boolean isUsingItem();

	@Inject(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/LocalPlayer;sprintTriggerTime:I", ordinal = 3, shift = At.Shift.AFTER))
	private void modifyBlockingWalkSpeed(CallbackInfo ci) {
		Player player = ((Player) (Object) this);
		if (isUsingItem() && (player.getUseItem().is(AAItemTags.CAN_BLOCK) || player.getUseItem().is(AAItemTags.HEAVY_SHIELDS))) {
			input.forwardImpulse *= 5;
			input.leftImpulse *= 5;
		}
	}
}
