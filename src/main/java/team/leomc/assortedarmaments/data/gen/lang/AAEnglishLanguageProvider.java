package team.leomc.assortedarmaments.data.gen.lang;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.client.event.AAClientSetupEvents;
import team.leomc.assortedarmaments.integration.eternalstarlight.EternalStarlightHelper;
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
		add(AssortedArmaments.ID + ".configuration.speedBasedAttackDamagePercentage", "Speed Based Attack Damage Modifier");
		add(AssortedArmaments.ID + ".configuration.flailMaxUseDuration", "Maximum Flail Use Duration");
		add(AssortedArmaments.ID + ".configuration.flailThrowMinUseDuration", "Minimum Use Duration to Throw a Flail");
		add(AssortedArmaments.ID + ".configuration.flailSpinCooldown", "Flail Spin Cooldown");
		add(AssortedArmaments.ID + ".configuration.flailSpinDamageFactor", "Flail Spin Damage Factor");
		add(AssortedArmaments.ID + ".configuration.flailSpinKnockbackFactor", "Flail Spin Knockback Factor");
		add(AssortedArmaments.ID + ".configuration.zombieUseWeaponChance", "Chance of a Zombie Using a Modded Weapon");
		add(AssortedArmaments.ID + ".configuration.piglinUseWeaponChance", "Chance of a Piglin Using a Modded Weapon");

		add("desc." + AssortedArmaments.ID + ".shift", "Hold [SHIFT] for more information");
		add("desc." + AssortedArmaments.ID + ".can_block", "Can be used to block melee damage that is equal to half of the weapon's damage");
		add("desc." + AssortedArmaments.ID + ".strong_sweep", "All targets in range on a sweeping attack take the same damage as a direct melee attack");
		add("desc." + AssortedArmaments.ID + ".two_handed", "Disables offhand items when held in main hand");
		add("desc." + AssortedArmaments.ID + ".armor_based_damage", "Deals extra damage to enemies with armor");
		add("desc." + AssortedArmaments.ID + ".disables_blocking_on_attack", "Disables the target's ability to block");
		add("desc." + AssortedArmaments.ID + ".extra_knockback", "Deals extra knockback");
		add("desc." + AssortedArmaments.ID + ".speed_based_damage", "Increases damage based on speed when sprinting");

		add("desc." + AssortedArmaments.ID + ".flails", "Can be thrown");
		add("desc." + AssortedArmaments.ID + ".javelins", "Can be thrown and plunged into an enemy, will also deal damage when removed from the victim");
		add("desc." + AssortedArmaments.ID + ".rapiers", "Continuously attacking the same target gradually increases the damage");

		AAItems.ITEMS.getEntries().forEach(item -> add(item.get(), toTitleCase(item.getId().getPath())));
		AAEntityTypes.ENTITY_TYPES.getEntries().forEach(entityType -> add(entityType.get(), toTitleCase(entityType.getId().getPath())));
		EternalStarlightHelper.addTranslations(this, true);
	}

	public static String toTitleCase(String raw) {
		return Arrays.stream(raw.split("_"))
			.map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
			.collect(Collectors.joining(" "));
	}
}
