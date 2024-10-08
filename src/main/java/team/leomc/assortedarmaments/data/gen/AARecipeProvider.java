package team.leomc.assortedarmaments.data.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.registry.AAItems;

import java.util.concurrent.CompletableFuture;

public class AARecipeProvider extends RecipeProvider {
	public AARecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		claymore(recipeOutput, AAItems.WOODEN_CLAYMORE.get(), ItemTags.PLANKS);
		claymore(recipeOutput, AAItems.STONE_CLAYMORE.get(), ItemTags.STONE_TOOL_MATERIALS);
		claymore(recipeOutput, AAItems.IRON_CLAYMORE.get(), Items.IRON_INGOT);
		claymore(recipeOutput, AAItems.DIAMOND_CLAYMORE.get(), Items.DIAMOND);
		claymore(recipeOutput, AAItems.GOLDEN_CLAYMORE.get(), Items.GOLD_INGOT);
		netheriteSmithing(recipeOutput, AAItems.DIAMOND_CLAYMORE.get(), RecipeCategory.COMBAT, AAItems.NETHERITE_CLAYMORE.get());

		mace(recipeOutput, AAItems.WOODEN_MACE.get(), ItemTags.PLANKS);
		mace(recipeOutput, AAItems.STONE_MACE.get(), ItemTags.STONE_TOOL_MATERIALS);
		mace(recipeOutput, AAItems.IRON_MACE.get(), Items.IRON_INGOT);
		mace(recipeOutput, AAItems.DIAMOND_MACE.get(), Items.DIAMOND);
		mace(recipeOutput, AAItems.GOLDEN_MACE.get(), Items.GOLD_INGOT);
		netheriteSmithing(recipeOutput, AAItems.DIAMOND_MACE.get(), RecipeCategory.COMBAT, AAItems.NETHERITE_MACE.get());

		flail(recipeOutput, AAItems.WOODEN_FLAIL.get(), ItemTags.PLANKS);
		flail(recipeOutput, AAItems.STONE_FLAIL.get(), ItemTags.STONE_TOOL_MATERIALS);
		flail(recipeOutput, AAItems.IRON_FLAIL.get(), Items.IRON_INGOT);
		flail(recipeOutput, AAItems.DIAMOND_FLAIL.get(), Items.DIAMOND);
		flail(recipeOutput, AAItems.GOLDEN_FLAIL.get(), Items.GOLD_INGOT);
		netheriteSmithing(recipeOutput, AAItems.DIAMOND_FLAIL.get(), RecipeCategory.COMBAT, AAItems.NETHERITE_FLAIL.get());

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(
				AAItems.GOLDEN_CLAYMORE.get(),
				AAItems.GOLDEN_MACE.get(),
				AAItems.GOLDEN_FLAIL.get()
			), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.1F, 200)
			.unlockedBy("has_golden_claymore", has(AAItems.GOLDEN_CLAYMORE.get()))
			.unlockedBy("has_golden_mace", has(AAItems.GOLDEN_MACE.get()))
			.unlockedBy("has_golden_flail", has(AAItems.GOLDEN_FLAIL.get()))
			.save(recipeOutput, AssortedArmaments.id(getSmeltingRecipeName(Items.GOLD_NUGGET)));
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(
				AAItems.IRON_CLAYMORE.get(),
				AAItems.IRON_MACE.get(),
				AAItems.IRON_FLAIL.get()
			), RecipeCategory.MISC, Items.IRON_NUGGET, 0.1F, 200)
			.unlockedBy("has_iron_claymore", has(AAItems.IRON_CLAYMORE.get()))
			.unlockedBy("has_iron_mace", has(AAItems.IRON_MACE.get()))
			.unlockedBy("has_iron_flail", has(AAItems.IRON_FLAIL.get()))
			.save(recipeOutput, AssortedArmaments.id(getSmeltingRecipeName(Items.IRON_NUGGET)));
	}

	protected static void netheriteSmithing(RecipeOutput recipeOutput, Item ingredientItem, RecipeCategory category, Item resultItem) {
		SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ingredientItem), Ingredient.of(Items.NETHERITE_INGOT), category, resultItem).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(recipeOutput, AssortedArmaments.id(getItemName(resultItem) + "_smithing"));
	}

	protected void claymore(RecipeOutput recipeOutput, ItemLike output, TagKey<Item> input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
			.define('#', Tags.Items.RODS_WOODEN)
			.define('X', input)
			.pattern(" XX")
			.pattern("XXX")
			.pattern("#X ")
			.unlockedBy("has_item", has(input))
			.save(recipeOutput);
	}

	protected void claymore(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
			.define('#', Tags.Items.RODS_WOODEN)
			.define('X', input)
			.pattern(" XX")
			.pattern("XXX")
			.pattern("#X ")
			.unlockedBy(getHasName(input), has(input))
			.save(recipeOutput);
	}

	protected void mace(RecipeOutput recipeOutput, ItemLike output, TagKey<Item> input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
			.define('#', Tags.Items.RODS_WOODEN)
			.define('X', input)
			.pattern("X#X")
			.pattern("X#X")
			.pattern(" # ")
			.unlockedBy("has_item", has(input))
			.save(recipeOutput);
	}

	protected void mace(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
			.define('#', Tags.Items.RODS_WOODEN)
			.define('X', input)
			.pattern("X#X")
			.pattern("X#X")
			.pattern(" # ")
			.unlockedBy(getHasName(input), has(input))
			.save(recipeOutput);
	}

	protected void flail(RecipeOutput recipeOutput, ItemLike output, TagKey<Item> input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
			.define('$', Tags.Items.CHAINS)
			.define('#', Tags.Items.RODS_WOODEN)
			.define('X', input)
			.pattern("$XX")
			.pattern("$XX")
			.pattern("#  ")
			.unlockedBy("has_item", has(input))
			.save(recipeOutput);
	}

	protected void flail(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
			.define('$', Tags.Items.CHAINS)
			.define('#', Tags.Items.RODS_WOODEN)
			.define('X', input)
			.pattern("$XX")
			.pattern("$XX")
			.pattern("#  ")
			.unlockedBy(getHasName(input), has(input))
			.save(recipeOutput);
	}
}
