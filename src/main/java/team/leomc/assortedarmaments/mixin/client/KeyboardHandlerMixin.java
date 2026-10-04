package team.leomc.assortedarmaments.mixin.client;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.leomc.assortedarmaments.effect.SyncopeEffect;

@OnlyIn(Dist.CLIENT)
@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
	@Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
	private void blockKeyPressWhenSyncope(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
		if (action != 0 && aa$isSyncope()) {
			ci.cancel();
		}
	}

	@Inject(method = "charTyped", at = @At("HEAD"), cancellable = true)
	private void blockCharTypedWhenSyncope(long windowPointer, int codePoint, int modifiers, CallbackInfo ci) {
		if (aa$isSyncope()) {
			ci.cancel();
		}
	}

	@Unique
	private static boolean aa$isSyncope() {
		return Minecraft.getInstance().player != null && SyncopeEffect.isActive(Minecraft.getInstance().player);
	}
}
