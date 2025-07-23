package team.leomc.assortedarmaments.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.tags.AAItemTags;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	private void use(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
		if (player.getData(AADataAttachments.BLOCKING_DISABLED_TIME) > 0 && player.getItemInHand(usedHand).is(AAItemTags.DISABLED_WHEN_DISABLING_BLOCKING)) {
			cir.setReturnValue(InteractionResultHolder.pass(player.getItemInHand(usedHand)));
		}
		if (player.getMainHandItem().is(AAItemTags.TWO_HANDED) && usedHand == InteractionHand.OFF_HAND) {
			cir.setReturnValue(InteractionResultHolder.pass(player.getOffhandItem()));
		}
	}

	@Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
	private void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		if (context.getPlayer() != null && context.getPlayer().getData(AADataAttachments.BLOCKING_DISABLED_TIME) > 0 && context.getPlayer().getItemInHand(context.getHand()).is(AAItemTags.DISABLED_WHEN_DISABLING_BLOCKING)) {
			cir.setReturnValue(InteractionResult.PASS);
		}
		if (context.getPlayer() != null && context.getPlayer().getMainHandItem().is(AAItemTags.TWO_HANDED) && context.getHand() == InteractionHand.OFF_HAND) {
			cir.setReturnValue(InteractionResult.PASS);
		}
	}
}
