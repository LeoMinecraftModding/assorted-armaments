package team.leomc.assortedarmaments.trait;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import team.leomc.assortedarmaments.registry.AADataComponents;

public class WeaponTraitHelper {
	public static WeaponTraitsComponent getTraits(@Nullable ItemStack stack) {
		return stack == null || stack.isEmpty() ? WeaponTraitsComponent.EMPTY : stack.getOrDefault(AADataComponents.WEAPON_TRAITS.get(), WeaponTraitsComponent.EMPTY);
	}

	public static boolean hasTrait(Holder<WeaponTrait> trait, ItemStack stack) {
		return getTraits(stack).hasTrait(trait);
	}

	public static boolean hasAnyTrait(ItemStack stack) {
		return !getTraits(stack).isEmpty();
	}

	public static void addTrait(ItemStack stack, Holder<WeaponTrait> trait) {
		stack.set(AADataComponents.WEAPON_TRAITS.get(), getTraits(stack).withTraitAdded(trait));
	}

	public static void removeTrait(ItemStack stack, Holder<WeaponTrait> trait) {
		stack.set(AADataComponents.WEAPON_TRAITS.get(), getTraits(stack).withTraitRemoved(trait));
	}
}
