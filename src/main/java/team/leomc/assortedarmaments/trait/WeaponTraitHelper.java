package team.leomc.assortedarmaments.trait;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import team.leomc.assortedarmaments.integration.bettercombat.BetterCombatHelper;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AADataComponents;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

import java.util.function.Consumer;

public class WeaponTraitHelper {
	public static WeaponTraitsComponent getTraits(@Nullable ItemStack stack) {
		return stack == null || stack.isEmpty() ? WeaponTraitsComponent.EMPTY : stack.getOrDefault(AADataComponents.WEAPON_TRAITS.get(), WeaponTraitsComponent.EMPTY);
	}

	public static boolean hasTrait(Holder<WeaponTrait> trait, ItemStack stack) {
		if (BetterCombatHelper.IS_LOADED && trait.is(AAWeaponTraits.DUAL_WIELD)) {
			return false;
		}
		return getTraits(stack).hasTrait(trait);
	}

	public static boolean hasAnyTrait(ItemStack stack) {
		return !getTraits(stack).isEmpty();
	}

	public static void forEachTrait(@Nullable ItemStack stack, Consumer<WeaponTrait> consumer) {
		for (Holder<WeaponTrait> holder : getTraits(stack).traits()) {
			consumer.accept(holder.value());
		}
	}

	public static ItemStack getAttackingWeapon(LivingEntity entity) {
		return entity instanceof Player player && player.getData(AADataAttachments.OFFHAND_ATTACK) ? player.getOffhandItem() : entity.getMainHandItem();
	}

	public static void addTrait(ItemStack stack, Holder<WeaponTrait> trait) {
		stack.set(AADataComponents.WEAPON_TRAITS.get(), getTraits(stack).withTraitAdded(trait));
	}

	public static void removeTrait(ItemStack stack, Holder<WeaponTrait> trait) {
		stack.set(AADataComponents.WEAPON_TRAITS.get(), getTraits(stack).withTraitRemoved(trait));
	}
}
