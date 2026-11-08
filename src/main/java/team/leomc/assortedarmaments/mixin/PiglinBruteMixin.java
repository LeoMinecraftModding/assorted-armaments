package team.leomc.assortedarmaments.mixin;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.leomc.assortedarmaments.AACommonConfig;
import team.leomc.assortedarmaments.event.AACommonEvents;
import team.leomc.assortedarmaments.tags.AAItemTags;

@Mixin(PiglinBrute.class)
public abstract class PiglinBruteMixin {
	@Inject(method = "finalizeSpawn", at = @At("RETURN"))
	private void aa$equipSpawnWeapon(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnData, CallbackInfoReturnable<SpawnGroupData> cir) {
		AACommonEvents.equipSpawnWeapon((Mob) (Object) this, level, AAItemTags.PIGLINS_CAN_USE, AACommonConfig.piglinUseWeaponChance, true);
	}
}
