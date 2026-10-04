package team.leomc.assortedarmaments.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;

@Mixin(Slot.class)
public abstract class SlotMixin {
	@Final
	@Shadow
	public Container container;

	@Shadow
	public abstract int getContainerSlot();

	@Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
	private void onMayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (this.container instanceof Inventory && this.getContainerSlot() == 40) {
			if (WeaponTraitHelper.hasTrait(AAWeaponTraits.TWO_HANDED, stack)) {
				cir.setReturnValue(false);
			}
		}
	}
}
