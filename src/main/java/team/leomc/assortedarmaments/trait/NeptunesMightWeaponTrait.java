package team.leomc.assortedarmaments.trait;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class NeptunesMightWeaponTrait extends WeaponTrait {
	public static void onIncomingDamage(LivingIncomingDamageEvent event) {
		Entity source = event.getContainer().getSource().getEntity();
		if (source instanceof LivingEntity living && living.isEyeInFluid(FluidTags.WATER)) {
			DamageContainer damage = event.getContainer();
			damage.setNewDamage(damage.getOriginalDamage() * 1.5F);
		}
	}
}
