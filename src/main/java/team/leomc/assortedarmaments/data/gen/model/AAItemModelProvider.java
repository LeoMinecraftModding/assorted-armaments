package team.leomc.assortedarmaments.data.gen.model;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.integration.eternalstarlight.EternalStarlightHelper;
import team.leomc.assortedarmaments.registry.AAItems;

public class AAItemModelProvider extends ItemModelProvider {
	public AAItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, AssortedArmaments.ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		claymore(AAItems.WOODEN_CLAYMORE.get());
		inventoryHandheld(AAItems.WOODEN_CLAYMORE.get());
		claymore(AAItems.STONE_CLAYMORE.get());
		inventoryHandheld(AAItems.STONE_CLAYMORE.get());
		claymore(AAItems.IRON_CLAYMORE.get());
		inventoryHandheld(AAItems.IRON_CLAYMORE.get());
		claymore(AAItems.GOLDEN_CLAYMORE.get());
		inventoryHandheld(AAItems.GOLDEN_CLAYMORE.get());
		claymore(AAItems.DIAMOND_CLAYMORE.get());
		inventoryHandheld(AAItems.DIAMOND_CLAYMORE.get());
		claymore(AAItems.NETHERITE_CLAYMORE.get());
		inventoryHandheld(AAItems.NETHERITE_CLAYMORE.get());

		handheld(AAItems.WOODEN_MACE.get());
		handheld(AAItems.STONE_MACE.get());
		handheld(AAItems.IRON_MACE.get());
		handheld(AAItems.GOLDEN_MACE.get());
		handheld(AAItems.DIAMOND_MACE.get());
		handheld(AAItems.NETHERITE_MACE.get());

		flail(AAItems.WOODEN_FLAIL.get());
		spinningFlail(AAItems.WOODEN_FLAIL.get());
		thrownFlail(AAItems.WOODEN_FLAIL.get());
		flail(AAItems.STONE_FLAIL.get());
		spinningFlail(AAItems.STONE_FLAIL.get());
		thrownFlail(AAItems.STONE_FLAIL.get());
		flail(AAItems.IRON_FLAIL.get());
		spinningFlail(AAItems.IRON_FLAIL.get());
		thrownFlail(AAItems.IRON_FLAIL.get());
		flail(AAItems.GOLDEN_FLAIL.get());
		spinningFlail(AAItems.GOLDEN_FLAIL.get());
		thrownFlail(AAItems.GOLDEN_FLAIL.get());
		flail(AAItems.DIAMOND_FLAIL.get());
		spinningFlail(AAItems.DIAMOND_FLAIL.get());
		thrownFlail(AAItems.DIAMOND_FLAIL.get());
		flail(AAItems.NETHERITE_FLAIL.get());
		spinningFlail(AAItems.NETHERITE_FLAIL.get());
		thrownFlail(AAItems.NETHERITE_FLAIL.get());

		javelin(AAItems.WOODEN_JAVELIN.get());
		thrownJavelin(AAItems.WOODEN_JAVELIN.get());
		javelin(AAItems.STONE_JAVELIN.get());
		thrownJavelin(AAItems.STONE_JAVELIN.get());
		javelin(AAItems.IRON_JAVELIN.get());
		thrownJavelin(AAItems.IRON_JAVELIN.get());
		javelin(AAItems.GOLDEN_JAVELIN.get());
		thrownJavelin(AAItems.GOLDEN_JAVELIN.get());
		javelin(AAItems.DIAMOND_JAVELIN.get());
		thrownJavelin(AAItems.DIAMOND_JAVELIN.get());
		javelin(AAItems.NETHERITE_JAVELIN.get());
		thrownJavelin(AAItems.NETHERITE_JAVELIN.get());

		pike(AAItems.WOODEN_PIKE.get());
		inventoryHandheld(AAItems.WOODEN_PIKE.get());
		pike(AAItems.STONE_PIKE.get());
		inventoryHandheld(AAItems.STONE_PIKE.get());
		pike(AAItems.IRON_PIKE.get());
		inventoryHandheld(AAItems.IRON_PIKE.get());
		pike(AAItems.GOLDEN_PIKE.get());
		inventoryHandheld(AAItems.GOLDEN_PIKE.get());
		pike(AAItems.DIAMOND_PIKE.get());
		inventoryHandheld(AAItems.DIAMOND_PIKE.get());
		pike(AAItems.NETHERITE_PIKE.get());
		inventoryHandheld(AAItems.NETHERITE_PIKE.get());

		rapier(AAItems.WOODEN_RAPIER.get());
		rapier(AAItems.STONE_RAPIER.get());
		rapier(AAItems.IRON_RAPIER.get());
		rapier(AAItems.GOLDEN_RAPIER.get());
		rapier(AAItems.DIAMOND_RAPIER.get());
		rapier(AAItems.NETHERITE_RAPIER.get());

		pike(AAItems.WOODEN_HALBERD.get());
		inventoryHandheld(AAItems.WOODEN_HALBERD.get());
		pike(AAItems.STONE_HALBERD.get());
		inventoryHandheld(AAItems.STONE_HALBERD.get());
		pike(AAItems.IRON_HALBERD.get());
		inventoryHandheld(AAItems.IRON_HALBERD.get());
		pike(AAItems.GOLDEN_HALBERD.get());
		inventoryHandheld(AAItems.GOLDEN_HALBERD.get());
		pike(AAItems.DIAMOND_HALBERD.get());
		inventoryHandheld(AAItems.DIAMOND_HALBERD.get());
		pike(AAItems.NETHERITE_HALBERD.get());
		inventoryHandheld(AAItems.NETHERITE_HALBERD.get());

		EternalStarlightHelper.registerModels(this);
	}

	public void claymore(ResourceLocation item) {
		ModelFile blocking = withExistingParent(item.getPath() + "_blocking", AssortedArmaments.id("item/large_handheld_blocking"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER));
		withExistingParent(item.getPath(), AssortedArmaments.id("item/large_handheld"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER))
			.override().predicate(AssortedArmaments.id("blocking"), 1).model(blocking).end();
	}

	private void claymore(Item item) {
		claymore(key(item));
	}

	private void flail(Item item) {
		ModelFile spinning = spinningFlail(item);
		withExistingParent(name(item), AssortedArmaments.id("item/flail"))
			.texture("layer0", itemTexture(item))
			.override().predicate(AssortedArmaments.id("spinning"), 1).model(spinning).end();
	}

	private void javelin(Item item) {
		withExistingParent(name(item), AssortedArmaments.id("item/javelin"))
			.texture("layer0", itemTexture(item));
	}

	private ItemModelBuilder handheld(Item item) {
		return handheld(item, itemTexture(item), false);
	}

	private ItemModelBuilder handheld(Item item, ResourceLocation texture, boolean large) {
		return getBuilder(item.toString())
			.parent(large ? new ModelFile.UncheckedModelFile(AssortedArmaments.id("item/large_handheld")) : new ModelFile.UncheckedModelFile("item/handheld"))
			.texture("layer0", texture);
	}

	public ResourceLocation itemTexture(Item item) {
		ResourceLocation name = key(item);
		return texture(name, ModelProvider.ITEM_FOLDER);
	}

	private ItemModelBuilder spinningFlail(Item item) {
		return getBuilder(item.toString() + "_spinning")
			.parent(new ModelFile.UncheckedModelFile(AssortedArmaments.id("item/spinning_flail")))
			.texture("flail", itemTexture(item) + "_spinning")
			.texture("particle", itemTexture(item));
	}

	private ItemModelBuilder thrownFlail(Item item) {
		return getBuilder(item.toString() + "_thrown")
			.parent(new ModelFile.UncheckedModelFile(AssortedArmaments.id("item/thrown_flail")))
			.texture("flail", itemTexture(item) + "_spinning")
			.texture("particle", itemTexture(item));
	}

	private ItemModelBuilder thrownJavelin(Item item) {
		return getBuilder(item.toString() + "_thrown")
			.parent(new ModelFile.UncheckedModelFile(AssortedArmaments.id("item/thrown_javelin")))
			.texture("javelin", itemTexture(item) + "_thrown")
			.texture("particle", itemTexture(item));
	}

	private ItemModelBuilder inventoryHandheld(Item item) {
		return inventoryHandheld(key(item));
	}

	public ItemModelBuilder inventoryHandheld(ResourceLocation item) {
		return getBuilder(item + "_inventory")
			.parent(new ModelFile.UncheckedModelFile("item/handheld"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER) + "_inventory");
	}

	private void pike(Item item) {
		ModelFile blocking = withExistingParent(name(item) + "_blocking", AssortedArmaments.id("item/pike_blocking"))
			.texture("layer0", itemTexture(item));
		ModelFile sprinting = withExistingParent(name(item) + "_sprinting", AssortedArmaments.id("item/pike_sprinting"))
			.texture("layer0", itemTexture(item));
		withExistingParent(name(item), AssortedArmaments.id("item/pike"))
			.texture("layer0", itemTexture(item))
			.override().predicate(AssortedArmaments.id("blocking"), 1).model(blocking).end()
			.override().predicate(AssortedArmaments.id("sprinting"), 1).predicate(AssortedArmaments.id("blocking"), 0).model(sprinting).end();
	}

	private void rapier(Item item) {
		ModelFile sprinting = withExistingParent(name(item) + "_sprinting", AssortedArmaments.id("item/handheld_sprinting"))
			.texture("layer0", itemTexture(item));
		withExistingParent(name(item), "item/handheld")
			.texture("layer0", itemTexture(item))
			.override().predicate(AssortedArmaments.id("sprinting"), 1).model(sprinting).end();
	}

	public ResourceLocation texture(ResourceLocation key, String prefix) {
		return ResourceLocation.fromNamespaceAndPath(key.getNamespace(), prefix + "/" + key.getPath());
	}

	private ResourceLocation key(Item item) {
		return BuiltInRegistries.ITEM.getKey(item);
	}

	private String name(Item item) {
		return key(item).getPath();
	}
}
