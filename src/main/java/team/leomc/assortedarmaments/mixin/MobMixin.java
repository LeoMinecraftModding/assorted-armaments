package team.leomc.assortedarmaments.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.leomc.assortedarmaments.effect.SyncopeEffect;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
	protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "serverAiStep", at = @At("HEAD"), cancellable = true)
	private void serverAiStep(CallbackInfo ci) {
		if (SyncopeEffect.isActive(this)) {
			this.xxa = 0.0F;
			this.zza = 0.0F;
			this.jumping = false;
			ci.cancel();
		}
	}
}
