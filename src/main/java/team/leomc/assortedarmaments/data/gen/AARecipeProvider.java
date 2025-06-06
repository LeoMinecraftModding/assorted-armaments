package team.leomc.assortedarmaments.data.gen;

import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.integration.eternalstarlight.EternalStarlightHelper;
import team.leomc.assortedarmaments.registry.AAItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AARecipeProvider extends RecipeProvider {
	private final Map<ResourceLocation, JsonObject> jsons = new HashMap<>();

	public AARecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		claymore(recipeOutput, AAItems.WOODEN_CLAYMORE.get(), ItemTags.PLANKS);
		claymore(recipeOutput, AAItems.STONE_CLAYMORE.get(), ItemTags.STONE_TOOL_MATERIALS);
		claymore(recipeOutput, AAItems.IRON_CLAYMORE.get(), Tags.Items.INGOTS_IRON);
		claymore(recipeOutput, AAItems.GOLDEN_CLAYMORE.get(), Tags.Items.INGOTS_GOLD);
		claymore(recipeOutput, AAItems.DIAMOND_CLAYMORE.get(), Tags.Items.GEMS_DIAMOND);
		netheriteSmithing(recipeOutput, AAItems.DIAMOND_CLAYMORE.get(), RecipeCategory.COMBAT, AAItems.NETHERITE_CLAYMORE.get());

		mace(recipeOutput, AAItems.WOODEN_MACE.get(), ItemTags.PLANKS);
		mace(recipeOutput, AAItems.STONE_MACE.get(), ItemTags.STONE_TOOL_MATERIALS);
		mace(recipeOutput, AAItems.IRON_MACE.get(), Tags.Items.INGOTS_IRON);
		mace(recipeOutput, AAItems.GOLDEN_MACE.get(), Tags.Items.INGOTS_GOLD);
		mace(recipeOutput, AAItems.DIAMOND_MACE.get(), Tags.Items.GEMS_DIAMOND);
		netheriteSmithing(recipeOutput, AAItems.DIAMOND_MACE.get(), RecipeCategory.COMBAT, AAItems.NETHERITE_MACE.get());

		flail(recipeOutput, AAItems.WOODEN_FLAIL.get(), ItemTags.PLANKS);
		flail(recipeOutput, AAItems.STONE_FLAIL.get(), ItemTags.STONE_TOOL_MATERIALS);
		flail(recipeOutput, AAItems.IRON_FLAIL.get(), Tags.Items.INGOTS_IRON);
		flail(recipeOutput, AAItems.GOLDEN_FLAIL.get(), Tags.Items.INGOTS_GOLD);
		flail(recipeOutput, AAItems.DIAMOND_FLAIL.get(), Tags.Items.GEMS_DIAMOND);
		netheriteSmithing(recipeOutput, AAItems.DIAMOND_FLAIL.get(), RecipeCategory.COMBAT, AAItems.NETHERITE_FLAIL.get());

		javelin(recipeOutput, AAItems.WOODEN_JAVELIN.get(), ItemTags.PLANKS);
		javelin(recipeOutput, AAItems.STONE_JAVELIN.get(), ItemTags.STONE_TOOL_MATERIALS);
		javelin(recipeOutput, AAItems.IRON_JAVELIN.get(), Tags.Items.INGOTS_IRON);
		javelin(recipeOutput, AAItems.GOLDEN_JAVELIN.get(), Tags.Items.INGOTS_GOLD);
		javelin(recipeOutput, AAItems.DIAMOND_JAVELIN.get(), Tags.Items.GEMS_DIAMOND);
		netheriteSmithing(recipeOutput, AAItems.DIAMOND_JAVELIN.get(), RecipeCategory.COMBAT, AAItems.NETHERITE_JAVELIN.get());

		pike(recipeOutput, AAItems.WOODEN_PIKE.get(), ItemTags.PLANKS);
		pike(recipeOutput, AAItems.STONE_PIKE.get(), ItemTags.STONE_TOOL_MATERIALS);
		pike(recipeOutput, AAItems.IRON_PIKE.get(), Tags.Items.INGOTS_IRON);
		pike(recipeOutput, AAItems.GOLDEN_PIKE.get(), Tags.Items.INGOTS_GOLD);
		pike(recipeOutput, AAItems.DIAMOND_PIKE.get(), Tags.Items.GEMS_DIAMOND);
		netheriteSmithing(recipeOutput, AAItems.DIAMOND_PIKE.get(), RecipeCategory.COMBAT, AAItems.NETHERITE_PIKE.get());

		rapier(recipeOutput, AAItems.WOODEN_RAPIER.get(), ItemTags.PLANKS);
		rapier(recipeOutput, AAItems.STONE_RAPIER.get(), ItemTags.STONE_TOOL_MATERIALS);
		rapier(recipeOutput, AAItems.IRON_RAPIER.get(), Tags.Items.INGOTS_IRON);
		rapier(recipeOutput, AAItems.GOLDEN_RAPIER.get(), Tags.Items.INGOTS_GOLD);
		rapier(recipeOutput, AAItems.DIAMOND_RAPIER.get(), Tags.Items.GEMS_DIAMOND);
		netheriteSmithing(recipeOutput, AAItems.DIAMOND_RAPIER.get(), RecipeCategory.COMBAT, AAItems.NETHERITE_RAPIER.get());

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(
				AAItems.GOLDEN_CLAYMORE.get(),
				AAItems.GOLDEN_MACE.get(),
				AAItems.GOLDEN_FLAIL.get(),
				AAItems.GOLDEN_JAVELIN.get(),
				AAItems.GOLDEN_PIKE.get(),
				AAItems.GOLDEN_RAPIER.get()
			), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.1F, 200)
			.unlockedBy("has_golden_claymore", has(AAItems.GOLDEN_CLAYMORE.get()))
			.unlockedBy("has_golden_mace", has(AAItems.GOLDEN_MACE.get()))
			.unlockedBy("has_golden_flail", has(AAItems.GOLDEN_FLAIL.get()))
			.unlockedBy("has_golden_javelin", has(AAItems.GOLDEN_JAVELIN.get()))
			.unlockedBy("has_golden_pike", has(AAItems.GOLDEN_PIKE.get()))
			.unlockedBy("has_golden_rapier", has(AAItems.GOLDEN_RAPIER.get()))
			.save(recipeOutput, AssortedArmaments.id(getSmeltingRecipeName(Items.GOLD_NUGGET)));
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(
				AAItems.IRON_CLAYMORE.get(),
				AAItems.IRON_MACE.get(),
				AAItems.IRON_FLAIL.get(),
				AAItems.IRON_JAVELIN.get(),
				AAItems.IRON_PIKE.get(),
				AAItems.IRON_RAPIER.get()
			), RecipeCategory.MISC, Items.IRON_NUGGET, 0.1F, 200)
			.unlockedBy("has_iron_claymore", has(AAItems.IRON_CLAYMORE.get()))
			.unlockedBy("has_iron_mace", has(AAItems.IRON_MACE.get()))
			.unlockedBy("has_iron_flail", has(AAItems.IRON_FLAIL.get()))
			.unlockedBy("has_iron_javelin", has(AAItems.IRON_JAVELIN.get()))
			.unlockedBy("has_iron_pike", has(AAItems.IRON_PIKE.get()))
			.unlockedBy("has_iron_rapier", has(AAItems.IRON_RAPIER.get()))
			.save(recipeOutput, AssortedArmaments.id(getSmeltingRecipeName(Items.IRON_NUGGET)));

		EternalStarlightHelper.buildRecipes(jsons);
	}

	protected static void netheriteSmithing(RecipeOutput recipeOutput, Item ingredientItem, RecipeCategory category, Item resultItem) {
		SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ingredientItem), Ingredient.of(Tags.Items.INGOTS_NETHERITE), category, resultItem).unlocks("has_netherite_ingot", has(Tags.Items.INGOTS_NETHERITE)).save(recipeOutput, AssortedArmaments.id(getItemName(resultItem) + "_smithing"));
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

	protected void javelin(RecipeOutput recipeOutput, ItemLike output, TagKey<Item> input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
			.define('X', input)
			.pattern("  X")
			.pattern(" X ")
			.pattern("X  ")
			.unlockedBy("has_item", has(input))
			.save(recipeOutput);
	}

	protected void pike(RecipeOutput recipeOutput, ItemLike output, TagKey<Item> input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
			.define('X', input)
			.define('#', Tags.Items.RODS_WOODEN)
			.pattern("  X")
			.pattern(" # ")
			.pattern("#  ")
			.unlockedBy("has_item", has(input))
			.save(recipeOutput);
	}

	protected void rapier(RecipeOutput recipeOutput, ItemLike output, TagKey<Item> input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
			.define('X', input)
			.define('#', Tags.Items.RODS_WOODEN)
			.pattern("  X")
			.pattern(" X ")
			.pattern("## ")
			.unlockedBy("has_item", has(input))
			.save(recipeOutput);
	}

	@Override
	protected CompletableFuture<?> run(CachedOutput output, HolderLookup.Provider registries) {
		List<CompletableFuture<?>> futures = new ArrayList<>();
		futures.add(super.run(output, registries));
		jsons.forEach((id, obj) -> futures.add(DataProvider.saveStable(output, obj, recipePathProvider.json(id))));
		return CompletableFuture.allOf(futures.toArray(CompletableFuture<?>[]::new));
	}
}
