package team.leomc.assortedarmaments.data.gen.lang;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.client.event.AAClientSetupEvents;
import team.leomc.assortedarmaments.data.AAEnchantments;
import team.leomc.assortedarmaments.integration.eternalstarlight.EternalStarlightHelper;
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
		add(AssortedArmaments.ID + ".configuration.armorBasedAttackDamagePercentage", "Armor Based Attack Damage Percentage");
		add(AssortedArmaments.ID + ".configuration.sprintExtraAttackDamagePercentage", "Sprint Extra Attack Damage Percentage");
		add(AssortedArmaments.ID + ".configuration.claymoreSweepAttackCooldown", "Claymore Sweep Attack Cooldown");
		add(AssortedArmaments.ID + ".configuration.flailTimePerPowerLevel", "Time Required for each Power Increase of a Flail");
		add(AssortedArmaments.ID + ".configuration.flailSpinDamageFactor", "Flail Spin Damage Factor");
		add(AssortedArmaments.ID + ".configuration.flailSpinKnockbackFactor", "Flail Spin Knockback Factor");
		add(AssortedArmaments.ID + ".configuration.heavyShieldBlockWalkSpeedModifier", "Heavy Shield Block Walk Speed Modifier");
		add(AssortedArmaments.ID + ".configuration.heavyShieldBlockAttackDamageModifier", "Heavy Shield Block Attack Damage Modifier");
		add(AssortedArmaments.ID + ".configuration.heavyShieldFastBlockTime", "Heavy Shield Fast Block Time");
		add(AssortedArmaments.ID + ".configuration.heavyShieldFastBlockDamageReflectionPercentage", "Heavy Shield Fast Block Damage Reflection Percentage");
		add(AssortedArmaments.ID + ".configuration.heavyShieldFastCounterattackTime", "Heavy Shield Fast Counterattack Time");
		add(AssortedArmaments.ID + ".configuration.zombieUseWeaponChance", "Chance of a Zombie Using a Modded Weapon");
		add(AssortedArmaments.ID + ".configuration.piglinUseWeaponChance", "Chance of a Piglin Using a Modded Weapon");

		add("desc." + AssortedArmaments.ID + ".shift", "[SHIFT]");

		add("weapon_trait." + AssortedArmaments.ID + ".can_block", "Block");
		add("weapon_trait." + AssortedArmaments.ID + ".can_block.desc", "Can be used to block melee damage that is equal to 200% of the weapon's damage");
		add("weapon_trait." + AssortedArmaments.ID + ".strong_sweep", "Sweep");
		add("weapon_trait." + AssortedArmaments.ID + ".strong_sweep.desc", "All targets in range on a sweeping attack take the same damage as a direct melee attack");
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
		add("weapon_trait." + AssortedArmaments.ID + ".thump.desc", "Smash attack temporarily stuns the target");
		add("weapon_trait." + AssortedArmaments.ID + ".extra_knockback.desc", "Deals extra knockback");
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

		add("weapon_trait." + AssortedArmaments.ID + ".flail_spin", "Spin");
		add("weapon_trait." + AssortedArmaments.ID + ".flail_spin.desc", "Can be swung by using for at least 2 seconds to deal damage and knockback to nearby targets, and be thrown when released");
		add("weapon_trait." + AssortedArmaments.ID + ".javelin_throw", "Throw");
		add("weapon_trait." + AssortedArmaments.ID + ".javelin_throw.desc", "Can be thrown to deal 100% damage and be plunged into an enemy, will also deal 200% damage when removed from the victim");

		add("weapon_trait.modify_interaction_range.desc", "Increases entity and block interaction range by %s");

		add("weapon_trait." + AssortedArmaments.ID + ".heavy_shields.desc", "Can still attack while blocking; Blocking a melee attack within a short time of starting to use a heavy shield will deal some damage back to the attacker; Using it to block damage and then using it to damage the attacker a short time later allows the blocked damage to be returned to the attacker");

		AAItems.ITEMS.getEntries().forEach(item -> add(item.get(), toTitleCase(item.getId().getPath())));
		AAEntityTypes.ENTITY_TYPES.getEntries().forEach(entityType -> add(entityType.get(), toTitleCase(entityType.getId().getPath())));

		add(Util.makeDescriptionId("enchantment", AAEnchantments.CRIT.location()), "Crit");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.CRIT.location()) + ".desc", "Increase the damage of your critical attacks");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.ARMOR_PENETRATION.location()), "Armor Penetration");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.ARMOR_PENETRATION.location()) + ".desc", "Reduces the target's armor effectiveness");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.SUPER_THUMP.location()), "Super Thump");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.SUPER_THUMP.location()) + ".desc", "Disables the target's ability to block and inflicts slowness effect on the target after a critical attack");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.KINETIC_ENERGY.location()), "Kinetic Energy");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.KINETIC_ENERGY.location()) + ".desc", "Allows thrown flails to deal critical hits");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.IMPACT.location()), "Impact");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.IMPACT.location()) + ".desc", "Increases knockback of thrown flails and stuns targets on hit");

		add(AAAttributes.CRITICAL_ATTACK_DAMAGE_MULTIPLIER.get().getDescriptionId(), "Critical Attack Damage Multiplier");

		EternalStarlightHelper.addTranslations(this, true);
	}

	public static String toTitleCase(String raw) {
		return Arrays.stream(raw.split("_"))
			.map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
			.collect(Collectors.joining(" "));
	}
}
