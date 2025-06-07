package team.leomc.assortedarmaments;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = AssortedArmaments.ID, bus = EventBusSubscriber.Bus.MOD)
public class AACommonConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	private static final ModConfigSpec.DoubleValue BLOCK_WALK_SPEED_MODIFIER = BUILDER
		.comment("Player's walk speed when blocking with a weapon = (1 - blockWalkSpeedModifier) * original speed")
		.defineInRange("blockWalkSpeedModifier", 0.25, 0, 1);

	private static final ModConfigSpec.DoubleValue ARMOR_BASED_ATTACK_DAMAGE_PERCENTAGE = BUILDER
		.comment("Damage caused when attacking with a weapon that deals damage according to the target's armor value = original damage + armorBasedAttackDamagePercentage * target's armor value")
		.defineInRange("armorBasedAttackDamagePercentage", 0.25, 0, Double.MAX_VALUE);

	private static final ModConfigSpec.DoubleValue SPEED_BASED_ATTACK_DAMAGE_MODIFIER = BUILDER
		.comment("Damage caused when attacking with a weapon that deals damage according to the attacker's movement speed = original damage + speedBasedAttackDamageModifier * attacker's movement speed")
		.defineInRange("speedBasedAttackDamageModifier", 10, 0, Double.MAX_VALUE);

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

	private static final ModConfigSpec.DoubleValue ZOMBIE_USE_WEAPON_CHANCE = BUILDER
		.comment("What is the probability that a zombie will use a weapon from Assorted Armaments?")
		.defineInRange("zombieUseWeaponChance", 0.1, 0, 1);

	private static final ModConfigSpec.DoubleValue PIGLIN_USE_WEAPON_CHANCE = BUILDER
		.comment("What is the probability that a piglin will use a weapon from Assorted Armaments?")
		.defineInRange("piglinUseWeaponChance", 0.1, 0, 1);

	public static final ModConfigSpec SPEC = BUILDER.build();

	public static double blockWalkSpeedModifier;
	public static double armorBasedAttackDamagePercentage;
	public static double speedBasedAttackDamageModifier;
	public static int claymoreSweepAttackCooldown;
	public static int flailTimePerPowerLevel;
	public static double flailSpinDamageFactor;
	public static double flailSpinKnockbackFactor;
	public static double zombieUseWeaponChance;
	public static double piglinUseWeaponChance;

	@SubscribeEvent
	private static void onLoad(final ModConfigEvent event) {
		blockWalkSpeedModifier = BLOCK_WALK_SPEED_MODIFIER.get();
		armorBasedAttackDamagePercentage = ARMOR_BASED_ATTACK_DAMAGE_PERCENTAGE.get();
		speedBasedAttackDamageModifier = SPEED_BASED_ATTACK_DAMAGE_MODIFIER.get();
		claymoreSweepAttackCooldown = CLAYMORE_SWEEP_ATTACK_COOLDOWN.get();
		flailTimePerPowerLevel = FLAIL_TIME_PER_POWER_LEVEL.get();
		flailSpinDamageFactor = FLAIL_SPIN_DAMAGE_FACTOR.get();
		flailSpinKnockbackFactor = FLAIL_SPIN_KNOCKBACK_FACTOR.get();
		zombieUseWeaponChance = ZOMBIE_USE_WEAPON_CHANCE.get();
		piglinUseWeaponChance = PIGLIN_USE_WEAPON_CHANCE.get();
	}
}
