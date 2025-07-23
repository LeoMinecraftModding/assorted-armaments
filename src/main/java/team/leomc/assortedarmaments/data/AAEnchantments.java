package team.leomc.assortedarmaments.data;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.item.enchantment.effects.MultiplyValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.apache.commons.lang3.mutable.MutableFloat;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.item.enchantment.effects.DisableTargeting;
import team.leomc.assortedarmaments.registry.AAAttributes;
import team.leomc.assortedarmaments.registry.AAEnchantmentEffectComponents;
import team.leomc.assortedarmaments.registry.AAEntityTypes;
import team.leomc.assortedarmaments.tags.AAItemTags;

public class AAEnchantments {
	public static final ResourceKey<Enchantment> CRIT = create("crit");
	public static final ResourceKey<Enchantment> ARMOR_PENETRATION = create("armor_penetration");
	public static final ResourceKey<Enchantment> SUPER_THUMP = create("super_thump");
	public static final ResourceKey<Enchantment> KINETIC_ENERGY = create("kinetic_energy");
	public static final ResourceKey<Enchantment> IMPACT = create("impact");

	public static void bootstrap(BootstrapContext<Enchantment> context) {
		HolderGetter<Item> items = context.lookup(Registries.ITEM);
		context.register(
			CRIT,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.MACE_ENCHANTABLE),
						2,
						5,
						Enchantment.dynamicCost(5, 9),
						Enchantment.dynamicCost(20, 9),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.withEffect(
					EnchantmentEffectComponents.ATTRIBUTES,
					new EnchantmentAttributeEffect(
						AssortedArmaments.id("enchantment.crit"),
						AAAttributes.CRITICAL_ATTACK_DAMAGE_MULTIPLIER,
						LevelBasedValue.perLevel(0.15F),
						AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
					)
				)
				.build(CRIT.location())
		);
		context.register(
			ARMOR_PENETRATION,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.MACE_ENCHANTABLE),
						2,
						5,
						Enchantment.dynamicCost(5, 9),
						Enchantment.dynamicCost(25, 9),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.withEffect(EnchantmentEffectComponents.ARMOR_EFFECTIVENESS, new AddValue(LevelBasedValue.perLevel(-0.2F)))
				.build(ARMOR_PENETRATION.location())
		);
		context.register(
			SUPER_THUMP,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.MACE_ENCHANTABLE),
						2,
						1,
						Enchantment.constantCost(20),
						Enchantment.constantCost(50),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.withEffect(AAEnchantmentEffectComponents.THUMP_EFFECTIVENESS.get(), new AddValue(LevelBasedValue.perLevel(1)))
				.build(SUPER_THUMP.location())
		);
		context.register(
			KINETIC_ENERGY,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.FLAIL_ENCHANTABLE),
						2,
						3,
						Enchantment.dynamicCost(5, 9),
						Enchantment.dynamicCost(20, 9),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.withEffect(AAEnchantmentEffectComponents.FLAIL_CRITICAL_ATTACK_CHANCE.get(), new AddValue(LevelBasedValue.perLevel(0.3F)))
				.build(KINETIC_ENERGY.location())
		);
		context.register(
			IMPACT,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.FLAIL_ENCHANTABLE),
						2,
						1,
						Enchantment.constantCost(20),
						Enchantment.constantCost(50),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new DisableTargeting(LevelBasedValue.perLevel(1.5F)), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(AAEntityTypes.FLAIL.get())))
				.withEffect(EnchantmentEffectComponents.KNOCKBACK, new MultiplyValue(LevelBasedValue.perLevel(2, 1)), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(AAEntityTypes.FLAIL.get())))
				.build(IMPACT.location())
		);
	}

	public static float modifyThumpEffectiveness(ServerLevel level, ItemStack tool, Entity entity, DamageSource damageSource, float damage) {
		MutableFloat mutablefloat = new MutableFloat(damage);
		EnchantmentHelper.runIterationOnItem(
			tool, (holder, enchantmentLevel) -> modifyThumpEffectiveness(holder.value(), level, enchantmentLevel, tool, entity, damageSource, mutablefloat)
		);
		return mutablefloat.floatValue();
	}

	public static void modifyThumpEffectiveness(Enchantment enchantment, ServerLevel level, int enchantmentLevel, ItemStack tool, Entity entity, DamageSource damageSource, MutableFloat thumpEffectiveness) {
		enchantment.modifyDamageFilteredValue(AAEnchantmentEffectComponents.THUMP_EFFECTIVENESS.get(), level, enchantmentLevel, tool, entity, damageSource, thumpEffectiveness);
	}

	public static float modifyFlailCriticalAttackChance(ServerLevel level, ItemStack tool, Entity entity, DamageSource damageSource, float damage) {
		MutableFloat mutablefloat = new MutableFloat(damage);
		EnchantmentHelper.runIterationOnItem(
			tool, (holder, enchantmentLevel) -> modifyFlailCriticalAttackChance(holder.value(), level, enchantmentLevel, tool, entity, damageSource, mutablefloat)
		);
		return mutablefloat.floatValue();
	}

	public static void modifyFlailCriticalAttackChance(Enchantment enchantment, ServerLevel level, int enchantmentLevel, ItemStack tool, Entity entity, DamageSource damageSource, MutableFloat flailCriticalAttackChance) {
		enchantment.modifyDamageFilteredValue(AAEnchantmentEffectComponents.FLAIL_CRITICAL_ATTACK_CHANCE.get(), level, enchantmentLevel, tool, entity, damageSource, flailCriticalAttackChance);
	}

	public static ResourceKey<Enchantment> create(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, AssortedArmaments.id(name));
	}
}
