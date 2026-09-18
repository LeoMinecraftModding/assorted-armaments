package team.leomc.assortedarmaments.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.trait.*;

public class AAWeaponTraits {
	public static final ResourceKey<Registry<WeaponTrait>> WEAPON_TRAIT_KEY =
		ResourceKey.createRegistryKey(
			ResourceLocation.fromNamespaceAndPath(AssortedArmaments.ID, "weapon_trait"));

	public static final Registry<WeaponTrait> WEAPON_TRAIT_REGISTRY =
		new RegistryBuilder<>(WEAPON_TRAIT_KEY).sync(true).create();

	public static final DeferredRegister<WeaponTrait> WEAPON_TRAITS =
		DeferredRegister.create(WEAPON_TRAIT_REGISTRY, AssortedArmaments.ID);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> TWO_HANDED = WEAPON_TRAITS.register("two_handed", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> CAN_BLOCK = WEAPON_TRAITS.register("can_block", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> STRONG_SWEEP = WEAPON_TRAITS.register("strong_sweep", StrongSweepWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> LARGE_WEAPON = WEAPON_TRAITS.register("large_weapon", ()-> new ModifyInteractionRangeWeaponTrait(1f));

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SHOCK = WEAPON_TRAITS.register("shock", ShockWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> KNOCK = WEAPON_TRAITS.register("knock", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> THUMP = WEAPON_TRAITS.register("thump", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> FLAIL_SPIN = WEAPON_TRAITS.register("flail_spin", WeaponDescriptiveWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> CONCENTRATION = WEAPON_TRAITS.register("concentration", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> STAB = WEAPON_TRAITS.register("stab", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SEE_THROUGH = WEAPON_TRAITS.register("see_through", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> LONG_WEAPON = WEAPON_TRAITS.register("long_weapon", ()-> new ModifyInteractionRangeWeaponTrait(2f));

	public static final DeferredHolder<WeaponTrait, WeaponTrait> JAVELIN_THROW = WEAPON_TRAITS.register("javelin_throw", WeaponDescriptiveWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SHORT_WEAPON = WEAPON_TRAITS.register("short_weapon", ()-> new ModifyInteractionRangeWeaponTrait(-0.5f));

	public static final DeferredHolder<WeaponTrait, WeaponTrait> LIGHTWEIGHT = WEAPON_TRAITS.register("lightweight", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> DUAL_WIELD = WEAPON_TRAITS.register("dual_wield", DualWieldWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> QUICK_ATTACK = WEAPON_TRAITS.register("quick_attack", WeaponTrait::new);
}
