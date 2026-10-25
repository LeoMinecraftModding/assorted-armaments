package team.leomc.assortedarmaments.data;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
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
import net.minecraft.world.item.enchantment.effects.ApplyMobEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.apache.commons.lang3.mutable.MutableFloat;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.registry.AAAttributes;
import team.leomc.assortedarmaments.registry.AAEffects;
import team.leomc.assortedarmaments.registry.AAEnchantmentEffectComponents;
import team.leomc.assortedarmaments.registry.AAEntityTypes;
import team.leomc.assortedarmaments.tags.AAEnchantmentTags;
import team.leomc.assortedarmaments.tags.AAItemTags;

public class AAEnchantments {
//	public static final ResourceKey<Enchantment> ARMOR_PENETRATION = create("armor_penetration");

	public static final ResourceKey<Enchantment> CRIT = create("crit");
	public static final ResourceKey<Enchantment> KINETIC_ENERGY = create("kinetic_energy");
	public static final ResourceKey<Enchantment> SUPER_THUMP = create("super_thump");
	public static final ResourceKey<Enchantment> INITIAL_VELOCITY = create("initial_velocity");
	public static final ResourceKey<Enchantment> BLAST = create("blast");
	public static final ResourceKey<Enchantment> DEEP_WOUND = create("deep_wound");
	public static final ResourceKey<Enchantment> PIN_UP = create("pin_up");
	public static final ResourceKey<Enchantment> WITHSTAND = create("withstand");
	public static final ResourceKey<Enchantment> SPLIT_AIR = create("split_air");

	public static void bootstrap(BootstrapContext<Enchantment> context) {
		HolderGetter<Item> items = context.lookup(Registries.ITEM);
		context.register(
			CRIT,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.JIAN_ENCHANTABLE),
						2,
						3,
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
						LevelBasedValue.perLevel(0.5F, 0.25F),
						AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
					)
				)
				.build(CRIT.location())
		);
//		context.register(
//			ARMOR_PENETRATION,
//			Enchantment.enchantment(
//					Enchantment.definition(
//						items.getOrThrow(AAItemTags.MACE_ENCHANTABLE),
//						2,
//						5,
//						Enchantment.dynamicCost(5, 9),
//						Enchantment.dynamicCost(25, 9),
//						4,
//						EquipmentSlotGroup.MAINHAND
//					)
//				)
//				.withEffect(EnchantmentEffectComponents.ARMOR_EFFECTIVENESS, new AddValue(LevelBasedValue.perLevel(-0.2F)))
//				.build(ARMOR_PENETRATION.location())
//		);
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
			SUPER_THUMP,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.FLAIL_ENCHANTABLE),
						2,
						3,
						Enchantment.constantCost(20),
						Enchantment.constantCost(50),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM,
					new ApplyMobEffect(
						HolderSet.direct(AAEffects.SYNCOPE),
						LevelBasedValue.perLevel(1.0F, 0.5F),
						LevelBasedValue.perLevel(1.0F, 0.5F),
						LevelBasedValue.constant(0.0F),
						LevelBasedValue.constant(0.0F)
					),
					LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(AAEntityTypes.FLAIL.get())))
//				.withEffect(AAEnchantmentEffectComponents.THUMP_EFFECTIVENESS.get(), new AddValue(LevelBasedValue.perLevel(1)))
				.build(SUPER_THUMP.location())
		);
		context.register(
			INITIAL_VELOCITY,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.FLAIL_ENCHANTABLE),
						2,
						3,
						Enchantment.constantCost(20),
						Enchantment.constantCost(50),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.build(INITIAL_VELOCITY.location())
		);
		context.register(
			BLAST,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.FLAIL_ENCHANTABLE),
						2,
						2,
						Enchantment.constantCost(20),
						Enchantment.constantCost(50),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.withEffect(AAEnchantmentEffectComponents.FLAIL_BLAST_DAMAGE.get(), new AddValue(LevelBasedValue.perLevel(0.25F)))
				.build(BLAST.location())
		);
		context.register(
			DEEP_WOUND,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.JAVELIN_ENCHANTABLE),
						2,
						1,
						Enchantment.constantCost(20),
						Enchantment.constantCost(50),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.exclusiveWith(context.lookup(Registries.ENCHANTMENT).getOrThrow(AAEnchantmentTags.EXCLUSIVE_SET_DEEP_WOUND))
				.withEffect(AAEnchantmentEffectComponents.DEEP_WOUND_DAMAGE.get(), new AddValue(LevelBasedValue.perLevel(2F, 0F)))
				.build(DEEP_WOUND.location())
		);
		context.register(
			PIN_UP,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.JAVELIN_ENCHANTABLE),
						2,
						3,
						Enchantment.dynamicCost(5, 9),
						Enchantment.dynamicCost(20, 9),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.exclusiveWith(context.lookup(Registries.ENCHANTMENT).getOrThrow(AAEnchantmentTags.EXCLUSIVE_SET_DEEP_WOUND))
				.withEffect(EnchantmentEffectComponents.KNOCKBACK, new AddValue(LevelBasedValue.perLevel(1.0F)), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(AAEntityTypes.JAVELIN.get())))
				.build(PIN_UP.location())
		);
		context.register(
			WITHSTAND,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.JIAN_ENCHANTABLE),
						2,
						3,
						Enchantment.dynamicCost(5, 9),
						Enchantment.dynamicCost(20, 9),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.withEffect(AAEnchantmentEffectComponents.PARRY_DURATION.get(), new AddValue(LevelBasedValue.perLevel(1.0F)))
				.build(WITHSTAND.location())
		);
		context.register(
			SPLIT_AIR,
			Enchantment.enchantment(
					Enchantment.definition(
						items.getOrThrow(AAItemTags.JIAN_ENCHANTABLE),
						2,
						1,
						Enchantment.constantCost(20),
						Enchantment.constantCost(50),
						4,
						EquipmentSlotGroup.MAINHAND
					)
				)
				.build(SPLIT_AIR.location())
		);
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

	public static float modifyFlailBlastDamage(ServerLevel level, ItemStack tool, Entity entity, DamageSource damageSource, float damage) {
		MutableFloat mutablefloat = new MutableFloat(damage);
		EnchantmentHelper.runIterationOnItem(
			tool, (holder, enchantmentLevel) -> modifyFlailBlastDamage(holder.value(), level, enchantmentLevel, tool, entity, damageSource, mutablefloat)
		);
		return mutablefloat.floatValue();
	}

	public static void modifyFlailBlastDamage(Enchantment enchantment, ServerLevel level, int enchantmentLevel, ItemStack tool, Entity entity, DamageSource damageSource, MutableFloat flailBlastDamage) {
		enchantment.modifyDamageFilteredValue(AAEnchantmentEffectComponents.FLAIL_BLAST_DAMAGE.get(), level, enchantmentLevel, tool, entity, damageSource, flailBlastDamage);
	}

	public static float modifyParryDuration(ServerLevel level, ItemStack tool, Entity entity, DamageSource damageSource, float duration) {
		MutableFloat mutablefloat = new MutableFloat(duration);
		EnchantmentHelper.runIterationOnItem(
			tool, (holder, enchantmentLevel) -> modifyParryDuration(holder.value(), level, enchantmentLevel, tool, entity, damageSource, mutablefloat)
		);
		return mutablefloat.floatValue();
	}

	public static void modifyParryDuration(Enchantment enchantment, ServerLevel level, int enchantmentLevel, ItemStack tool, Entity entity, DamageSource damageSource, MutableFloat parryDuration) {
		enchantment.modifyDamageFilteredValue(AAEnchantmentEffectComponents.PARRY_DURATION.get(), level, enchantmentLevel, tool, entity, damageSource, parryDuration);
	}

	public static float modifyDeepWoundDamage(ServerLevel level, ItemStack tool, Entity entity, DamageSource damageSource, float damage) {
		MutableFloat mutablefloat = new MutableFloat(damage);
		EnchantmentHelper.runIterationOnItem(
			tool, (holder, enchantmentLevel) -> modifyDeepWoundDamage(holder.value(), level, enchantmentLevel, tool, entity, damageSource, mutablefloat)
		);
		return mutablefloat.floatValue();
	}

	public static void modifyDeepWoundDamage(Enchantment enchantment, ServerLevel level, int enchantmentLevel, ItemStack tool, Entity entity, DamageSource damageSource, MutableFloat deepWoundDamage) {
		enchantment.modifyDamageFilteredValue(AAEnchantmentEffectComponents.DEEP_WOUND_DAMAGE.get(), level, enchantmentLevel, tool, entity, damageSource, deepWoundDamage);
	}

	public static ResourceKey<Enchantment> create(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, AssortedArmaments.id(name));
	}
}
