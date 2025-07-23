package team.leomc.assortedarmaments;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = AssortedArmaments.ID)
public class AACommonConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	private static final ModConfigSpec.DoubleValue BLOCK_WALK_SPEED_MODIFIER = BUILDER
		.comment("Player's walk speed when blocking with a weapon = (1 - blockWalkSpeedModifier) * original speed")
		.defineInRange("blockWalkSpeedModifier", 0.25, 0, 1);

	private static final ModConfigSpec.DoubleValue ARMOR_BASED_ATTACK_DAMAGE_PERCENTAGE = BUILDER
		.comment("Damage caused when attacking with a weapon that deals damage according to the target's armor value = original damage + armorBasedAttackDamagePercentage * target's armor value")
		.defineInRange("armorBasedAttackDamagePercentage", 0.25, 0, Double.MAX_VALUE);

	private static final ModConfigSpec.DoubleValue SPRINT_EXTRA_ATTACK_DAMAGE_PERCENTAGE = BUILDER
		.comment("Damage caused when attacking with a weapon that deals extra damage when the attacker is sprinting = (1 + sprintExtraAttackDamagePercentage) * original damage")
		.defineInRange("sprintExtraAttackDamagePercentage", 0.15, 0, Double.MAX_VALUE);

	private static final ModConfigSpec.IntValue CLAYMORE_SWEEP_ATTACK_COOLDOWN = BUILDER
		.comment("Claymores' intentional sweep attack cooldown (in ticks, 20 ticks = 1 second)")
		.defineInRange("claymoreSweepAttackCooldown", 100, 0, Integer.MAX_VALUE);

	private static final ModConfigSpec.IntValue FLAIL_TIME_PER_POWER_LEVEL = BUILDER
		.comment("When using a flail, every flailTimePerPowerLevel ticks, the power level of the flail increases. This is also the minimum time a player needs to use a flail in order to throw it (in ticks, 20 ticks = 1 second)")
		.defineInRange("flailTimePerPowerLevel", 20, 0, Integer.MAX_VALUE);

	private static final ModConfigSpec.DoubleValue FLAIL_SPIN_DAMAGE_FACTOR = BUILDER
		.comment("Damage caused when attacking with a spinning flail = original damage * flailSpinDamageFactor")
		.defineInRange("flailSpinDamageFactor", 0.25, 0, 1);

	private static final ModConfigSpec.DoubleValue FLAIL_SPIN_KNOCKBACK_FACTOR = BUILDER
		.comment("Knockback caused when attacking with a spinning flail = original knockback * flailSpinKnockbackFactor")
		.defineInRange("flailSpinKnockbackFactor", 0.25, 0, 1);

	private static final ModConfigSpec.DoubleValue HEAVY_SHIELD_BLOCK_WALK_SPEED_MODIFIER = BUILDER
		.comment("Player's walk speed when blocking with a heavy shield = (1 - heavyShieldBlockWalkSpeedModifier) * original speed")
		.defineInRange("heavyShieldBlockWalkSpeedModifier", 0.5, 0, 1);

	private static final ModConfigSpec.DoubleValue HEAVY_SHIELD_BLOCK_ATTACK_DAMAGE_MODIFIER = BUILDER
		.comment("Player's attack damage when blocking with a heavy shield = (1 - heavyShieldBlockAttackDamageModifier) * original attack damage")
		.defineInRange("heavyShieldBlockAttackDamageModifier", 0.5, 0, 1);

	private static final ModConfigSpec.IntValue HEAVY_SHIELD_FAST_BLOCK_TIME = BUILDER
		.comment("Blocking a melee attack within the first heavyShieldFastBlockTime ticks of using a heavy shield will deal (heavyShieldFastBlockDamageReflectionPercentage * weapon attack damage) damage to the attacker (in ticks, 20 ticks = 1 second)")
		.defineInRange("heavyShieldFastBlockTime", 5, 0, Integer.MAX_VALUE);

	private static final ModConfigSpec.DoubleValue HEAVY_SHIELD_FAST_BLOCK_DAMAGE_REFLECTION_PERCENTAGE = BUILDER
		.comment("Blocking a melee attack within the first heavyShieldFastBlockTime ticks of using a heavy shield will deal (heavyShieldFastBlockDamageReflectionPercentage * weapon attack damage) damage to the attacker")
		.defineInRange("heavyShieldFastBlockDamageReflectionPercentage", 1.2, 0, Double.MAX_VALUE);

	private static final ModConfigSpec.IntValue HEAVY_SHIELD_FAST_COUNTERATTACK_TIME = BUILDER
		.comment("Using a heavy shield to block damage and then using it to damage the attacker heavyShieldFastCounterattackTime ticks later allows the blocked damage to be returned to the attacker (in ticks, 20 ticks = 1 second)")
		.defineInRange("heavyShieldFastCounterattackTime", 5, 0, Integer.MAX_VALUE);

	private static final ModConfigSpec.DoubleValue ZOMBIE_USE_WEAPON_CHANCE = BUILDER
		.comment("What is the probability that a zombie will use a weapon from Assorted Armaments?")
		.defineInRange("zombieUseWeaponChance", 0.1, 0, 1);

	private static final ModConfigSpec.DoubleValue PIGLIN_USE_WEAPON_CHANCE = BUILDER
		.comment("What is the probability that a piglin will use a weapon from Assorted Armaments?")
		.defineInRange("piglinUseWeaponChance", 0.1, 0, 1);

	public static final ModConfigSpec SPEC = BUILDER.build();

	public static double blockWalkSpeedModifier;
	public static double armorBasedAttackDamagePercentage;
	public static double sprintExtraAttackDamagePercentage;
	public static int claymoreSweepAttackCooldown;
	public static int flailTimePerPowerLevel;
	public static double flailSpinDamageFactor;
	public static double flailSpinKnockbackFactor;
	public static double heavyShieldBlockWalkSpeedModifier;
	public static double heavyShieldBlockAttackDamageModifier;
	public static int heavyShieldFastBlockTime;
	public static double heavyShieldFastBlockDamageReflectionPercentage;
	public static int heavyShieldFastCounterattackTime;
	public static double zombieUseWeaponChance;
	public static double piglinUseWeaponChance;

	@SubscribeEvent
	private static void onLoad(final ModConfigEvent event) {
		blockWalkSpeedModifier = BLOCK_WALK_SPEED_MODIFIER.get();
		armorBasedAttackDamagePercentage = ARMOR_BASED_ATTACK_DAMAGE_PERCENTAGE.get();
		sprintExtraAttackDamagePercentage = SPRINT_EXTRA_ATTACK_DAMAGE_PERCENTAGE.get();
		claymoreSweepAttackCooldown = CLAYMORE_SWEEP_ATTACK_COOLDOWN.get();
		flailTimePerPowerLevel = FLAIL_TIME_PER_POWER_LEVEL.get();
		flailSpinDamageFactor = FLAIL_SPIN_DAMAGE_FACTOR.get();
		flailSpinKnockbackFactor = FLAIL_SPIN_KNOCKBACK_FACTOR.get();
		heavyShieldBlockWalkSpeedModifier = HEAVY_SHIELD_BLOCK_WALK_SPEED_MODIFIER.get();
		heavyShieldBlockAttackDamageModifier = HEAVY_SHIELD_BLOCK_ATTACK_DAMAGE_MODIFIER.get();
		heavyShieldFastBlockTime = HEAVY_SHIELD_FAST_BLOCK_TIME.get();
		heavyShieldFastBlockDamageReflectionPercentage = HEAVY_SHIELD_FAST_BLOCK_DAMAGE_REFLECTION_PERCENTAGE.get();
		heavyShieldFastCounterattackTime = HEAVY_SHIELD_FAST_COUNTERATTACK_TIME.get();
		zombieUseWeaponChance = ZOMBIE_USE_WEAPON_CHANCE.get();
		piglinUseWeaponChance = PIGLIN_USE_WEAPON_CHANCE.get();
	}
}
