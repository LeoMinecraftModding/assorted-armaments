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
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.integration.aquaculture.AquacultureHelper;
import team.leomc.assortedarmaments.integration.eternalstarlight.EternalStarlightHelper;
import team.leomc.assortedarmaments.integration.twilightforest.TwilightForestHelper;
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

		heavyShield(AAItems.WOODEN_HEAVY_SHIELD.get());
		inventoryHandheld(AAItems.WOODEN_HEAVY_SHIELD.get());
		heavyShield(AAItems.STONE_HEAVY_SHIELD.get());
		inventoryHandheld(AAItems.STONE_HEAVY_SHIELD.get());
		heavyShield(AAItems.IRON_HEAVY_SHIELD.get());
		inventoryHandheld(AAItems.IRON_HEAVY_SHIELD.get());
		heavyShield(AAItems.GOLDEN_HEAVY_SHIELD.get());
		inventoryHandheld(AAItems.GOLDEN_HEAVY_SHIELD.get());
		heavyShield(AAItems.DIAMOND_HEAVY_SHIELD.get());
		inventoryHandheld(AAItems.DIAMOND_HEAVY_SHIELD.get());
		heavyShield(AAItems.NETHERITE_HEAVY_SHIELD.get());
		inventoryHandheld(AAItems.NETHERITE_HEAVY_SHIELD.get());

		claw(AAItems.WOODEN_CLAW.get());
		claw(AAItems.STONE_CLAW.get());
		claw(AAItems.IRON_CLAW.get());
		claw(AAItems.GOLDEN_CLAW.get());
		claw(AAItems.DIAMOND_CLAW.get());
		claw(AAItems.NETHERITE_CLAW.get());

		handheld(AAItems.WOODEN_JIAN.get());
		handheld(AAItems.STONE_JIAN.get());
		handheld(AAItems.IRON_JIAN.get());
		handheld(AAItems.GOLDEN_JIAN.get());
		handheld(AAItems.DIAMOND_JIAN.get());
		handheld(AAItems.NETHERITE_JIAN.get());

		EternalStarlightHelper.registerModels(this);
		TwilightForestHelper.registerModels(this);
		AquacultureHelper.registerModels(this);
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

	public void flail(ResourceLocation item) {
		ModelFile spinning = spinningFlail(item);
		withExistingParent(item.getPath(), AssortedArmaments.id("item/flail"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER))
			.override().predicate(AssortedArmaments.id("spinning"), 1).model(spinning).end();
	}

	private void flail(Item item) {
		flail(key(item));
	}

	public void javelin(ResourceLocation item) {
		withExistingParent(item.getPath(), AssortedArmaments.id("item/javelin"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER));
	}

	private void javelin(Item item) {
		javelin(key(item));
	}

	public void claw(ResourceLocation item) {
		withExistingParent(item.getPath(), AssortedArmaments.id("item/claw"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER));
	}

	private void claw(Item item) {
		claw(key(item));
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

	public ItemModelBuilder spinningFlail(ResourceLocation item) {
		return getBuilder(item.getPath() + "_spinning")
			.parent(new ModelFile.UncheckedModelFile(AAUtils.flailCompatParent(item, "spinning_flail")))
			.texture("flail", texture(item, ModelProvider.ITEM_FOLDER) + "_spinning")
			.texture("particle", texture(item, ModelProvider.ITEM_FOLDER));
	}

	private ItemModelBuilder spinningFlail(Item item) {
		return spinningFlail(key(item));
	}

	public ItemModelBuilder thrownFlail(ResourceLocation item) {
		return getBuilder(item.getPath() + "_thrown")
			.parent(new ModelFile.UncheckedModelFile(AAUtils.flailCompatParent(item, "thrown_flail")))
			.texture("flail", texture(item, ModelProvider.ITEM_FOLDER) + "_spinning")
			.texture("particle", texture(item, ModelProvider.ITEM_FOLDER));
	}

	private ItemModelBuilder thrownFlail(Item item) {
		return thrownFlail(key(item));
	}

	public ItemModelBuilder thrownJavelin(ResourceLocation item) {
		return getBuilder(item.getPath() + "_thrown")
			.parent(new ModelFile.UncheckedModelFile(AAUtils.javelinCompatParent(item, "thrown_javelin")))
			.texture("javelin", texture(item, ModelProvider.ITEM_FOLDER) + "_thrown")
			.texture("particle", texture(item, ModelProvider.ITEM_FOLDER));
	}

	private ItemModelBuilder thrownJavelin(Item item) {
		return thrownJavelin(key(item));
	}

	private ItemModelBuilder inventoryHandheld(Item item) {
		return inventoryHandheld(key(item));
	}

	public ItemModelBuilder inventoryHandheld(ResourceLocation item) {
		return getBuilder(item + "_inventory")
			.parent(new ModelFile.UncheckedModelFile("item/handheld"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER) + "_inventory");
	}

	public void pike(ResourceLocation item) {
		ModelFile blocking = withExistingParent(item.getPath() + "_blocking", AssortedArmaments.id("item/pike_blocking"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER));
		ModelFile sprinting = withExistingParent(item.getPath() + "_sprinting", AssortedArmaments.id("item/pike_sprinting"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER));
		withExistingParent(item.getPath(), AssortedArmaments.id("item/pike"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER))
			.override().predicate(AssortedArmaments.id("blocking"), 1).model(blocking).end()
			.override().predicate(AssortedArmaments.id("sprinting"), 1).predicate(AssortedArmaments.id("blocking"), 0).model(sprinting).end();
	}

	private void pike(Item item) {
		pike(key(item));
	}

	public void rapier(ResourceLocation item) {
		ModelFile sprinting = withExistingParent(item.getPath() + "_sprinting", AssortedArmaments.id("item/rapier_handheld_sprinting"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER));
		withExistingParent(item.getPath(), AssortedArmaments.id("item/rapier_handheld"))
			.texture("layer0", texture(item, ModelProvider.ITEM_FOLDER))
			.override().predicate(AssortedArmaments.id("sprinting"), 1).model(sprinting).end();
	}

	private void rapier(Item item) {
		rapier(key(item));
	}

	public void heavyShield(ResourceLocation item) {
		boolean fiery = AAUtils.isFiery(item.getPath());
		ItemModelBuilder blocking = withExistingParent(item.getPath() + "_blocking", AAUtils.heavyShieldCompatParent(item, "heavy_shield_blocking"))
			.texture("shield", texture(item, ModelProvider.ITEM_FOLDER))
			.texture("particle", texture(item, ModelProvider.ITEM_FOLDER) + "_inventory");
		ItemModelBuilder builder = withExistingParent(item.getPath(), AAUtils.heavyShieldCompatParent(item, "heavy_shield"))
			.texture("shield", texture(item, ModelProvider.ITEM_FOLDER))
			.texture("particle", texture(item, ModelProvider.ITEM_FOLDER) + "_inventory");
		if (fiery) {
			ResourceLocation layer = AssortedArmaments.id("item/fiery_layer");
			blocking.texture("layer", layer);
			builder.texture("layer", layer);
		}
		builder.override().predicate(AssortedArmaments.id("blocking"), 1).model(blocking).end();
	}

	private void heavyShield(Item item) {
		heavyShield(key(item));
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
