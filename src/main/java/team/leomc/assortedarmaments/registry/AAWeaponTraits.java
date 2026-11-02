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

	public static final DeferredHolder<WeaponTrait, WeaponTrait> TWO_HANDED = WEAPON_TRAITS.register("two_handed", TwoHandedWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> CAN_BLOCK = WEAPON_TRAITS.register("can_block", CanBlockWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> STRONG_SWEEP = WEAPON_TRAITS.register("strong_sweep", StrongSweepWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> LARGE_WEAPON = WEAPON_TRAITS.register("large_weapon", ()-> new ModifyInteractionRangeWeaponTrait(1f, 1f));

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SHOCK = WEAPON_TRAITS.register("shock", ShockWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> KNOCK = WEAPON_TRAITS.register("knock", KnockWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> THUMP = WEAPON_TRAITS.register("thump", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> FLAIL_SPIN = WEAPON_TRAITS.register("flail_spin", WeaponDescriptiveWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> TORRENT = WEAPON_TRAITS.register("torrent", TorrentWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> CONCENTRATION = WEAPON_TRAITS.register("concentration", ConcentrationWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> STAB = WEAPON_TRAITS.register("stab", StabWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SEE_THROUGH = WEAPON_TRAITS.register("see_through", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> LONG_WEAPON = WEAPON_TRAITS.register("long_weapon", ()-> new ModifyInteractionRangeWeaponTrait(2f, 2f));

	public static final DeferredHolder<WeaponTrait, WeaponTrait> JAVELIN_THROW = WEAPON_TRAITS.register("javelin_throw", WeaponDescriptiveWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SHORT_WEAPON = WEAPON_TRAITS.register("short_weapon", ()-> new ModifyInteractionRangeWeaponTrait(0f, -0.5f));

	public static final DeferredHolder<WeaponTrait, WeaponTrait> LIGHTWEIGHT = WEAPON_TRAITS.register("lightweight", LightweightWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> DUAL_WIELD = WEAPON_TRAITS.register("dual_wield", DualWieldWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> QUICK_ATTACK = WEAPON_TRAITS.register("quick_attack", QuickAttackWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> AXE = WEAPON_TRAITS.register("axe", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> HEAVY_BLOCKING = WEAPON_TRAITS.register("heavy_blocking", HeavyBlockingWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SHIELD_PARRY = WEAPON_TRAITS.register("shield_parry", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> IRONCLAD = WEAPON_TRAITS.register("ironclad", IroncladWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> PARRY = WEAPON_TRAITS.register("parry", ParryWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> GLITTERING = WEAPON_TRAITS.register("glittering", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SUPER_HEAVY = WEAPON_TRAITS.register("super_heavy", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> MUSHROOM_GIFT = WEAPON_TRAITS.register("mushroom_gift", MushroomGiftWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> DRAGON_FIRE = WEAPON_TRAITS.register("dragon_fire", DragonFireWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> GLOW = WEAPON_TRAITS.register("glow", GlowWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SHINE = WEAPON_TRAITS.register("shine", ShineWeaponTrait::new);

	//Compat Trail
	public static final DeferredHolder<WeaponTrait, WeaponTrait> IGNIT = WEAPON_TRAITS.register("ignit", IgnitWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> KNIGHT = WEAPON_TRAITS.register("knight", KnightWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> NEPTUNES_MIGHT = WEAPON_TRAITS.register("neptunes_might", NeptunesMightWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> GRACE = WEAPON_TRAITS.register("grace", WeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> CRYSTALLIZE = WEAPON_TRAITS.register("crystallize", CrystallizeWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SHARPEN = WEAPON_TRAITS.register("sharpen", SharpenWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> SHIFT_GRAVITY = WEAPON_TRAITS.register("shift_gravity", ShiftGravityWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> LIGHT_DANCE = WEAPON_TRAITS.register("light_dance", LightDanceWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> BANE_UNDEAD = WEAPON_TRAITS.register("bane_undead", BaneUndeadWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> POISON_IMMUNE = WEAPON_TRAITS.register("poison_immune", PoisonImmuneWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> HEAT = WEAPON_TRAITS.register("heat", HeatWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> FIRE_IMMUNE = WEAPON_TRAITS.register("fire_immune", FireImmuneWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> CHILL = WEAPON_TRAITS.register("chill", ChillWeaponTrait::new);

	public static final DeferredHolder<WeaponTrait, WeaponTrait> COLD_IMMUNE = WEAPON_TRAITS.register("cold_immune", ColdImmuneWeaponTrait::new);
}
