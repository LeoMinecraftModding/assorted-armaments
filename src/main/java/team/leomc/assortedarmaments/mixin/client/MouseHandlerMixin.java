package team.leomc.assortedarmaments.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.leomc.assortedarmaments.effect.SyncopeEffect;

@OnlyIn(Dist.CLIENT)
@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@Inject(method = "turnPlayer", at = @At("HEAD"), cancellable = true)
	private void lockTurnWhenSyncope(double movementTime, CallbackInfo ci) {
		if (aa$isSyncope()) {
			ci.cancel();
		}
	}

	@Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
	private void blockPressWhenSyncope(long windowPointer, int button, int action, int modifiers, CallbackInfo ci) {
		if (action != 0 && aa$isSyncope()) {
			ci.cancel();
		}
	}

	@Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
	private void blockScrollWhenSyncope(long windowPointer, double xOffset, double yOffset, CallbackInfo ci) {
		if (aa$isSyncope()) {
			ci.cancel();
		}
	}

	@Unique
	private boolean aa$isSyncope() {
		return minecraft.player != null && SyncopeEffect.isActive(minecraft.player);
	}
}
