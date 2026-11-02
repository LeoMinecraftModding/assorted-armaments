package team.leomc.assortedarmaments.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.leomc.assortedarmaments.entity.ai.FlailSpinGoal;

@Mixin(Zombie.class)
public abstract class ZombieMixin {
	@Inject(method = "registerGoals", at = @At("TAIL"))
	private void aa$addFlailSpinGoal(CallbackInfo ci) {
		Mob mob = (Mob) (Object) this;
		mob.goalSelector.addGoal(1, new FlailSpinGoal(mob));
	}
}
