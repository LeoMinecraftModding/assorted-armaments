package team.leomc.assortedarmaments.data.gen.lang;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.client.event.AAClientSetupEvents;
import team.leomc.assortedarmaments.data.AAEnchantments;
import team.leomc.assortedarmaments.integration.AACompatManager;
import team.leomc.assortedarmaments.registry.AAAttributes;
import team.leomc.assortedarmaments.registry.AAEntityTypes;
import team.leomc.assortedarmaments.registry.AAItems;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AAEnglishLanguageProvider extends LanguageProvider {
	public AAEnglishLanguageProvider(PackOutput output) {
		super(output, AssortedArmaments.ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("name." + AssortedArmaments.ID, "Assorted Armaments");
		add("fml.menu.mods.info.description." + AssortedArmaments.ID, "A Minecraft mod that adds more types of weapons");

		add(AAClientSetupEvents.KEY_CATEGORY_ASSORTED_ARMAMENTS, "Assorted Armaments");
		add(Util.makeDescriptionId("key", AssortedArmaments.id("remove_javelin")), "Remove Javelin");

		add(AssortedArmaments.ID + ".configuration.blockWalkSpeedModifier", "Block Walk Speed Modifier");
		add(AssortedArmaments.ID + ".configuration.canBlockDamagePercentage", "Can Block Weapon Damage Percentage");
		add(AssortedArmaments.ID + ".configuration.armorBasedAttackDamagePercentage", "Armor Based Attack Damage Percentage");
		add(AssortedArmaments.ID + ".configuration.sprintExtraAttackDamagePercentage", "Sprint Extra Attack Damage Percentage");
		add(AssortedArmaments.ID + ".configuration.claymoreSweepAttackCooldown", "Claymore Sweep Attack Cooldown");
		add(AssortedArmaments.ID + ".configuration.flailTimePerPowerLevel", "Time Required for each Power Increase of a Flail");
		add(AssortedArmaments.ID + ".configuration.flailSpinDamageFactor", "Flail Spin Damage Factor");
		add(AssortedArmaments.ID + ".configuration.flailSpinKnockbackFactor", "Flail Spin Knockback Factor");
		add(AssortedArmaments.ID + ".configuration.glowStuckDamageBonus", "Glow Stuck Damage Bonus");
		add(AssortedArmaments.ID + ".configuration.heavyShieldBlockWalkSpeedModifier", "Heavy Shield Block Walk Speed Modifier");
		add(AssortedArmaments.ID + ".configuration.heavyShieldBlockAttackDamageModifier", "Heavy Shield Block Attack Damage Modifier");
		add(AssortedArmaments.ID + ".configuration.heavyShieldFastBlockTime", "Heavy Shield Fast Block Time");
		add(AssortedArmaments.ID + ".configuration.heavyShieldFastBlockDamageReflectionPercentage", "Heavy Shield Fast Block Damage Reflection Percentage");
		add(AssortedArmaments.ID + ".configuration.heavyShieldFastCounterattackTime", "Heavy Shield Fast Counterattack Time");
		add(AssortedArmaments.ID + ".configuration.ironcladArmorValue", "Ironclad Armor Value");
		add(AssortedArmaments.ID + ".configuration.parryDuration", "Parry Duration");
		add(AssortedArmaments.ID + ".configuration.parryMeleeDamageReduction", "Parry Melee Damage Reduction");
		add(AssortedArmaments.ID + ".configuration.zombieUseWeaponChance", "Chance of a Zombie Using a Modded Weapon");
		add(AssortedArmaments.ID + ".configuration.piglinUseWeaponChance", "Chance of a Piglin Using a Modded Weapon");
		add(AssortedArmaments.ID + ".configuration.pinUpSyncopeDuration", "Pin Up Syncope Duration");

		add("desc." + AssortedArmaments.ID + ".shift", "[SHIFT]");

		add("weapon_trait." + AssortedArmaments.ID + ".can_block", "Block");
		add("weapon_trait." + AssortedArmaments.ID + ".can_block.desc", "Can be used to block melee damage that is equal to %s%% of the weapon's damage");
		add("weapon_trait." + AssortedArmaments.ID + ".strong_sweep", "Sweep");
		add("weapon_trait." + AssortedArmaments.ID + ".strong_sweep.desc", "All targets in range on a sweeping attack take 75% melee attack");
		add("weapon_trait." + AssortedArmaments.ID + ".two_handed", "Two-Handed");
		add("weapon_trait." + AssortedArmaments.ID + ".two_handed.desc", "Disables offhand items when held in main hand");
		add("weapon_trait." + AssortedArmaments.ID + ".large_weapon", "Large Weapon");
		add("weapon_trait." + AssortedArmaments.ID + ".long_weapon", "Long Weapon");
		add("weapon_trait." + AssortedArmaments.ID + ".short_weapon", "Short Weapon");
		add("weapon_trait." + AssortedArmaments.ID + ".shock", "Shock");
		add("weapon_trait." + AssortedArmaments.ID + ".shock.desc", "Deals extra damage equal to %s%% of the target's armor to enemies with armor");
		add("weapon_trait." + AssortedArmaments.ID + ".knock", "Knock");
		add("weapon_trait." + AssortedArmaments.ID + ".knock.desc", "Disables the target's ability to block");
		add("weapon_trait." + AssortedArmaments.ID + ".thump", "Thump");
		add("weapon_trait." + AssortedArmaments.ID + ".thump.desc", "Smash attack temporarily stuns the target but grants no base damage bonus");
		add("weapon_trait." + AssortedArmaments.ID + ".stab", "Stab");
		add("weapon_trait." + AssortedArmaments.ID + ".stab.desc", "Increases damage when sprinting");
		add("weapon_trait." + AssortedArmaments.ID + ".see_through", "See Through");
		add("weapon_trait." + AssortedArmaments.ID + ".see_through.desc", "Bypasses invincibility time");
		add("weapon_trait." + AssortedArmaments.ID + ".concentration", "Concentration");
		add("weapon_trait." + AssortedArmaments.ID + ".concentration.desc", "Continuously attacking the same target gradually increases attack damage and speed");
		add("weapon_trait." + AssortedArmaments.ID + ".lightweight", "Lightweight");
		add("weapon_trait." + AssortedArmaments.ID + ".lightweight.desc", "Increases movement speed when sprinting");
		add("weapon_trait." + AssortedArmaments.ID + ".dual_wield", "Dual Wield");
		add("weapon_trait." + AssortedArmaments.ID + ".dual_wield.desc", "Can be dual wielded with separated attack cooldowns");
		add("weapon_trait." + AssortedArmaments.ID + ".quick_attack", "Quick Attack");
		add("weapon_trait." + AssortedArmaments.ID + ".quick_attack.desc", "Applies only half of invincibility time");
		add("weapon_trait." + AssortedArmaments.ID + ".axe", "Axe");
		add("weapon_trait." + AssortedArmaments.ID + ".axe.desc", "Functions as an axe and breaks wood-based blocks faster");
		add("weapon_trait." + AssortedArmaments.ID + ".heavy_blocking", "Heavy Blocking");
		add("weapon_trait." + AssortedArmaments.ID + ".heavy_blocking.desc", "Can be used to block like a shield, but cannot be disabled by axes or the Warden's attack");
		add("weapon_trait." + AssortedArmaments.ID + ".shield_parry", "Shield Parry");
		add("weapon_trait." + AssortedArmaments.ID + ".shield_parry.desc", "Blocking a melee attack within a short time of starting to use a heavy shield will deal some damage back to the attacker; Using it to block damage and then using it to damage the attacker a short time later allows the blocked damage to be returned to the attacker");
		add("weapon_trait." + AssortedArmaments.ID + ".ironclad", "Ironclad");
		add("weapon_trait." + AssortedArmaments.ID + ".ironclad.desc", "Increases armor by %s and armor toughness equal to 100%% of the weapon's damage while held");
		add("weapon_trait." + AssortedArmaments.ID + ".parry", "Parry");
		add("weapon_trait." + AssortedArmaments.ID + ".parry.desc", "Attacking at full cooldown grants a brief parry that prevents damage knockback and reduces melee damage taken by %s%%");

		add("weapon_trait." + AssortedArmaments.ID + ".flail_spin", "Spin");
		add("weapon_trait." + AssortedArmaments.ID + ".flail_spin.desc", "Can be swung by using for at least 2 seconds to deal damage and knockback to nearby targets, and be thrown when released");
		add("weapon_trait." + AssortedArmaments.ID + ".torrent", "Torrent");
		add("weapon_trait." + AssortedArmaments.ID + ".torrent.desc", "Spinning the flail while in water or rain does not slow your movement and throws travel farther, and thrown flails are unaffected by fluid drag in water");
		add("weapon_trait." + AssortedArmaments.ID + ".javelin_throw", "Throw");
		add("weapon_trait." + AssortedArmaments.ID + ".javelin_throw.desc", "Can be thrown to deal 100% damage and be plunged into an enemy, will also deal 200% damage when removed from the victim");

		add("weapon_trait." + AssortedArmaments.ID + ".glittering", "Glittering");
		add("weapon_trait." + AssortedArmaments.ID + ".glittering.desc", "Piglins remain neutral towards you while held");
		add("weapon_trait." + AssortedArmaments.ID + ".glow", "Glow");
		add("weapon_trait." + AssortedArmaments.ID + ".glow.desc", "Targets hit glow for 3 seconds, and a javelin stuck in a target keeps it glowing and increases the damage it takes by %s%%");
		add("weapon_trait." + AssortedArmaments.ID + ".super_heavy", "Super Heavy");
		add("weapon_trait." + AssortedArmaments.ID + ".super_heavy.desc", "Attacking while falling after jumping from flat ground triggers a smash attack with a base damage bonus and inflicts a longer stun");
		add("weapon_trait." + AssortedArmaments.ID + ".mushroom_gift", "Mushroom Gift");
		add("weapon_trait." + AssortedArmaments.ID + ".mushroom_gift.desc", "Fully charged attacks randomly trigger one of: Poison II for 6s, restore 1 hunger and saturation, set the target on fire for 12s, or a 1.5-block spore explosion dealing 50% weapon damage");
		add("weapon_trait." + AssortedArmaments.ID + ".dragon_fire", "Dragon Fire");
		add("weapon_trait." + AssortedArmaments.ID + ".dragon_fire.desc", "Attacks deal Dragon's Breath damage to enemies");

		add("weapon_trait." + AssortedArmaments.ID + ".neptunes_might", "Neptune's Grace");
		add("weapon_trait." + AssortedArmaments.ID + ".neptunes_might.desc", "Increases damage against enemies underwater");

		add("weapon_trait." + AssortedArmaments.ID + ".grace", "Grace");
		add("weapon_trait." + AssortedArmaments.ID + ".grace.desc", "Doubles mob drops when killing with a full-strength attack or a thrown weapon");

		add("weapon_trait." + AssortedArmaments.ID + ".crystallize", "Crystallize");
		add("weapon_trait." + AssortedArmaments.ID + ".crystallize.desc", "Has a 1/25 chance to drop an Ambrosium Shard on a full-strength hit");

		add("weapon_trait." + AssortedArmaments.ID + ".sharpen", "Sharpen");
		add("weapon_trait." + AssortedArmaments.ID + ".sharpen.desc", "Increases attack damage as durability decreases, up to +150%");

		add("weapon_trait." + AssortedArmaments.ID + ".shift_gravity", "Shift Gravity");
		add("weapon_trait." + AssortedArmaments.ID + ".shift_gravity.desc", "Full-strength hits launch the target into the air");
		add("weapon_trait." + AssortedArmaments.ID + ".shine", "Shine");
		add("weapon_trait." + AssortedArmaments.ID + ".shine.desc", "A javelin stuck in a target explodes when the target dies, passing this effect on");

		add("weapon_trait." + AssortedArmaments.ID + ".light_dance", "Light Dance");
		add("weapon_trait." + AssortedArmaments.ID + ".light_dance.desc", "Increases attack speed by 10%");
		add("weapon_trait." + AssortedArmaments.ID + ".bane_undead", "Bane of Undead");
		add("weapon_trait." + AssortedArmaments.ID + ".bane_undead.desc", "Deals 25% more damage to undead");
		add("weapon_trait." + AssortedArmaments.ID + ".poison_immune", "Poison Immunity");
		add("weapon_trait." + AssortedArmaments.ID + ".poison_immune.desc", "Immune to poison");
		add("weapon_trait." + AssortedArmaments.ID + ".heat", "Heat");
		add("weapon_trait." + AssortedArmaments.ID + ".heat.desc", "Ignites the target for 5 seconds on hit");
		add("weapon_trait." + AssortedArmaments.ID + ".fire_immune", "Fire Immunity");
		add("weapon_trait." + AssortedArmaments.ID + ".fire_immune.desc", "Immune to fire damage");
		add("weapon_trait." + AssortedArmaments.ID + ".chill", "Chill");
		add("weapon_trait." + AssortedArmaments.ID + ".chill.desc", "Slows the target on hit, stacking up; higher stacks also apply Mining Fatigue");
		add("weapon_trait." + AssortedArmaments.ID + ".cold_immune", "Cold Immunity");
		add("weapon_trait." + AssortedArmaments.ID + ".cold_immune.desc", "Immune to Slowness and Mining Fatigue");

		add("weapon_trait.modify_interaction_range.desc", "Increases attack range by %s and block interaction range by %s");

		AAItems.ITEMS.getEntries().forEach(item -> add(item.get(), toTitleCase(item.getId().getPath())));
		AAEntityTypes.ENTITY_TYPES.getEntries().forEach(entityType -> add(entityType.get(), toTitleCase(entityType.getId().getPath())));

		add(Util.makeDescriptionId("enchantment", AAEnchantments.KINETIC_ENERGY.location()), "Kinetic Energy");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.KINETIC_ENERGY.location()) + ".desc", "Allows thrown flails to deal critical hits");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.SUPER_THUMP.location()), "Super Thump");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.SUPER_THUMP.location()) + ".desc", "Thrown flail hits stun the target for (1 + level) * 0.5 seconds");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.INITIAL_VELOCITY.location()), "Initial Velocity");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.INITIAL_VELOCITY.location()) + ".desc", "Increases the initial momentum gained when starting the flail spin per level");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.BLAST.location()), "Blast");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.BLAST.location()) + ".desc", "Thrown flails detonate on impact, damaging all enemies within a 1.5 block radius for 25% of the flail's damage per level");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.DEEP_WOUND.location()), "Deep Wound");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.DEEP_WOUND.location()) + ".desc", "Reduces javelin throw damage by 100% and deal 400% damage when removed from the victim");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.PIN_UP.location()), "Pin Up");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.PIN_UP.location()) + ".desc", "Javelin throws knock targets back, stunning them if the knockback slams them into a block");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.CRIT.location()), "Bash");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.CRIT.location()) + ".desc", "Increases critical damage by (1 + level) * 25%.");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.WITHSTAND.location()), "Withstand");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.WITHSTAND.location()) + ".desc", "Increases parry duration by 0.05 seconds per level");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.SPLIT_AIR.location()), "Split Air");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.SPLIT_AIR.location()) + ".desc", "Deflects projectiles when parrying");

		add(AAAttributes.CRITICAL_ATTACK_DAMAGE_MULTIPLIER.get().getDescriptionId(), "Critical Attack Damage Multiplier");

		add("effect." + AssortedArmaments.ID + ".syncope", "Syncope");
		add("effect." + AssortedArmaments.ID + ".syncope.description", "Completely immobilizes the target");
		add("effect." + AssortedArmaments.ID + ".perforation", "Perforation");
		add("effect." + AssortedArmaments.ID + ".perforation.description", "The target takes 1 additional armor-piercing damage whenever it takes damage");
		add("effect." + AssortedArmaments.ID + ".extract", "Extract");
		add("effect." + AssortedArmaments.ID + ".extract.description", "On death, applies this effect to all hostile mobs within a 5 block radius for 10 seconds and deals damage equal to 20% of the dead entity's maximum health");

		add("tooltip." + AssortedArmaments.ID + ".knightmetal_weapon", "Extra damage to armored targets");
		add("tooltip." + AssortedArmaments.ID + ".knightmetal_tool", "Extra damage to unarmored targets");
		add("tooltip." + AssortedArmaments.ID + ".fiery_weapon", "Burns targets");
		add("tooltip." + AssortedArmaments.ID + ".fiery_smelting", "Auto-smelting");

		add("subtitles." + AssortedArmaments.ID + ".flail.fully_charge", "Flail Fully Charged");
		add("subtitles." + AssortedArmaments.ID + ".flail.spin", "Flail Spin");
		add("subtitles." + AssortedArmaments.ID + ".flail.thrown", "Flail Thrown");
		add("subtitles." + AssortedArmaments.ID + ".javelin.pull_out", "Javelin Pull Out");

		AACompatManager.addTranslations(this, true);
	}

	public static String toTitleCase(String raw) {
		return Arrays.stream(raw.split("_"))
			.map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
			.collect(Collectors.joining(" "));
	}
}
