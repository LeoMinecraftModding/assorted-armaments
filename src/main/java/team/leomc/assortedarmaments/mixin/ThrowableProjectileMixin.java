package team.leomc.assortedarmaments.mixin;

import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;

@Mixin(ThrowableProjectile.class)
public abstract class ThrowableProjectileMixin {
	@ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;scale(D)Lnet/minecraft/world/phys/Vec3;"), index = 0)
	private double tick(double drag) {
		if (((ThrowableProjectile) (Object) this) instanceof ThrowableItemProjectile projectile && projectile.isInWater() && WeaponTraitHelper.hasTrait(AAWeaponTraits.TORRENT, projectile.getItem())) {
			return 0.99;
		}
		return drag;
	}
}
