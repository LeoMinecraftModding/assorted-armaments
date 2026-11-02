package team.leomc.assortedarmaments.registry;

import com.google.common.base.Suppliers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum AATiers implements Tier {
	PUMPKIN(BlockTags.INCORRECT_FOR_GOLD_TOOL, 1024, 0.0F, -1.0F, 22, () -> Ingredient.of(Items.GOLD_INGOT)),
	MUSHROOM(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 0.0F, -1.0F, 15, () -> Ingredient.of(Items.MYCELIUM)),
	ENDERDRAGON(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 0.0F, -1.0F, 15, () -> Ingredient.of(Items.DRAGON_BREATH)),
	ENDERLANTERN(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 0.0F, -1.0F, 15, () -> Ingredient.of(Items.POPPED_CHORUS_FRUIT)),
	ELDER(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1800, 0.0F, -1.0F, 10, () -> Ingredient.of(Items.PRISMARINE));

	private final TagKey<Block> incorrectBlocksForDrops;
	private final int uses;
	private final float speed;
	private final float attackDamageBonus;
	private final int enchantmentValue;
	private final Supplier<Ingredient> repairIngredient;

	AATiers(TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
		this.incorrectBlocksForDrops = incorrectBlocksForDrops;
		this.uses = uses;
		this.speed = speed;
		this.attackDamageBonus = attackDamageBonus;
		this.enchantmentValue = enchantmentValue;
		this.repairIngredient = Suppliers.memoize(repairIngredient::get);
	}

	@Override
	public int getUses() {
		return this.uses;
	}

	@Override
	public float getSpeed() {
		return this.speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.attackDamageBonus;
	}

	@Override
	public TagKey<Block> getIncorrectBlocksForDrops() {
		return this.incorrectBlocksForDrops;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}
}
