package team.leomc.assortedarmaments.client.event;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.client.renderer.entity.ThrownFlailRenderer;
import team.leomc.assortedarmaments.client.renderer.entity.ThrownJavelinRenderer;
import team.leomc.assortedarmaments.integration.AACompatManager;
import team.leomc.assortedarmaments.registry.AAEntityTypes;
import team.leomc.assortedarmaments.registry.AAItems;
import team.leomc.assortedarmaments.tags.AAItemTags;
import team.leomc.assortedarmaments.trait.DualWieldWeaponTrait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = AssortedArmaments.ID, value = Dist.CLIENT)
public class AAClientSetupEvents {
	public static final Map<ModelResourceLocation, Map<ItemDisplayContext, ModelResourceLocation>> ITEMS_WITH_SPECIAL_MODELS = Util.make(new HashMap<>(), map -> {
		putInventoryModels(map, "wooden_claymore", "stone_claymore", "iron_claymore", "golden_claymore", "diamond_claymore", "netherite_claymore", "ender_dragon_claymore");
		putGuiModels(map, "wooden_flail", "stone_flail", "iron_flail", "golden_flail", "diamond_flail", "netherite_flail", "elder_roll_flail");
		putInventoryModels(map, "wooden_pike", "stone_pike", "iron_pike", "golden_pike", "diamond_pike", "netherite_pike");
		putInventoryModels(map, "wooden_halberd", "stone_halberd", "iron_halberd", "golden_halberd", "diamond_halberd", "netherite_halberd");
		putInventoryModels(map, "wooden_heavy_shield", "stone_heavy_shield", "iron_heavy_shield", "golden_heavy_shield", "diamond_heavy_shield", "netherite_heavy_shield");
		putInventoryModels(map, "wooden_mace", "stone_mace", "iron_mace", "golden_mace", "diamond_mace", "netherite_mace", "golden_pumpkin_mace");
		putInventoryModels(map, "wooden_jian", "stone_jian", "iron_jian", "golden_jian", "diamond_jian", "netherite_jian", "mushroom_skewers_jian");

		AACompatManager.registerAdditionalModel(map);
	});

	private static void putInventoryModels(Map<ModelResourceLocation, Map<ItemDisplayContext, ModelResourceLocation>> map, String... itemPaths) {
		for (String itemPath : itemPaths) {
			ModelResourceLocation inventory = ModelResourceLocation.standalone(AssortedArmaments.id("item/" + itemPath + "_inventory"));
			map.put(ModelResourceLocation.inventory(AssortedArmaments.id(itemPath)), Map.of(
				ItemDisplayContext.HEAD, inventory,
				ItemDisplayContext.GUI, inventory,
				ItemDisplayContext.GROUND, inventory,
				ItemDisplayContext.FIXED, inventory
			));
		}
	}

	private static void putGuiModels(Map<ModelResourceLocation, Map<ItemDisplayContext, ModelResourceLocation>> map, String... itemPaths) {
		for (String itemPath : itemPaths) {
			map.put(ModelResourceLocation.inventory(AssortedArmaments.id(itemPath)), Map.of(
				ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/" + itemPath))
			));
		}
	}

	public static final List<ModelResourceLocation> ADDITIONAL_MODELS = Lists.newArrayList(
		ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/elder_roll_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/ender_lantern_javelin_thrown"))
	);

	public static final String KEY_CATEGORY_ASSORTED_ARMAMENTS = "key.categories.assorted_armaments";

	public static final KeyMapping KEY_MAPPING_REMOVE_JAVELIN = new KeyMapping(Util.makeDescriptionId("key", AssortedArmaments.id("remove_javelin")), InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_ASSORTED_ARMAMENTS);

	@SubscribeEvent
	private static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ItemProperties.register(AAItems.WOODEN_CLAYMORE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.STONE_CLAYMORE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.IRON_CLAYMORE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.GOLDEN_CLAYMORE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.DIAMOND_CLAYMORE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.NETHERITE_CLAYMORE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);

			AACompatManager.registerItemProperties();

			ItemProperties.register(AAItems.WOODEN_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.STONE_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.IRON_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.GOLDEN_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.DIAMOND_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.NETHERITE_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.ELDER_ROLL_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);

			ItemProperties.register(AAItems.WOODEN_PIKE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.STONE_PIKE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.IRON_PIKE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.GOLDEN_PIKE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.DIAMOND_PIKE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.NETHERITE_PIKE.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);

			ItemProperties.register(AAItems.WOODEN_PIKE.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.STONE_PIKE.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.IRON_PIKE.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.GOLDEN_PIKE.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.DIAMOND_PIKE.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.NETHERITE_PIKE.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);

			ItemProperties.register(AAItems.WOODEN_RAPIER.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.STONE_RAPIER.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.IRON_RAPIER.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.GOLDEN_RAPIER.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.DIAMOND_RAPIER.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.NETHERITE_RAPIER.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);

			ItemProperties.register(AAItems.WOODEN_HALBERD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.STONE_HALBERD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.IRON_HALBERD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.GOLDEN_HALBERD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.DIAMOND_HALBERD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.NETHERITE_HALBERD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);

			ItemProperties.register(AAItems.WOODEN_HALBERD.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.STONE_HALBERD.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.IRON_HALBERD.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.GOLDEN_HALBERD.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.DIAMOND_HALBERD.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);
			ItemProperties.register(AAItems.NETHERITE_HALBERD.get(), AssortedArmaments.id("sprinting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.isSprinting() ? 1 : 0);

			ItemProperties.register(AAItems.WOODEN_HEAVY_SHIELD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.STONE_HEAVY_SHIELD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.IRON_HEAVY_SHIELD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.GOLDEN_HEAVY_SHIELD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.DIAMOND_HEAVY_SHIELD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.NETHERITE_HEAVY_SHIELD.get(), AssortedArmaments.id("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
		});
	}

	@SubscribeEvent
	private static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(AAEntityTypes.FLAIL.get(), ThrownFlailRenderer::new);
		event.registerEntityRenderer(AAEntityTypes.JAVELIN.get(), ThrownJavelinRenderer::new);
	}

	public static final EnumProxy<HumanoidModel.ArmPose> ASSORTED_ARMAMENTS_FLAIL_SPIN_POSE = new EnumProxy<>(
		HumanoidModel.ArmPose.class, false, (IArmPoseTransformer) (model, entity, arm) -> {
		if (arm == HumanoidArm.RIGHT) {
			model.rightArm.xRot = -180 * Mth.DEG_TO_RAD;
			model.rightArm.yRot = 0;
			model.rightArm.zRot = 0;
		} else {
			model.leftArm.xRot = -180 * Mth.DEG_TO_RAD;
			model.leftArm.yRot = 0;
			model.leftArm.zRot = 0;
		}
	});

	@SubscribeEvent
	private static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
		IClientItemExtensions flailExtensions = new IClientItemExtensions() {
			@Nullable
			@Override
			public HumanoidModel.ArmPose getArmPose(LivingEntity living, InteractionHand hand, ItemStack itemStack) {
				if (living.isUsingItem() && living.getUseItem().is(AAItemTags.FLAILS)) {
					return ASSORTED_ARMAMENTS_FLAIL_SPIN_POSE.getValue();
				}
				return null;
			}

			@Override
			public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
				if (player.isUsingItem() && player.getUseItem().is(AAItemTags.FLAILS)) {
					poseStack.translate(0, 0.7f, 0.1f);
					return true;
				}
				return false;
			}
		};

		List<Item> flails = new ArrayList<>(List.of(
			AAItems.WOODEN_FLAIL.get(),
			AAItems.STONE_FLAIL.get(),
			AAItems.IRON_FLAIL.get(),
			AAItems.GOLDEN_FLAIL.get(),
			AAItems.DIAMOND_FLAIL.get(),
			AAItems.NETHERITE_FLAIL.get(),
			AAItems.ELDER_ROLL_FLAIL.get()
		));

		flails.addAll(List.of(AACompatManager.getFlails()));

		event.registerItem(flailExtensions, flails.toArray(Item[]::new));
	}

	@SubscribeEvent
	private static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event) {
		for (Map.Entry<ModelResourceLocation, Map<ItemDisplayContext, ModelResourceLocation>> entry : ITEMS_WITH_SPECIAL_MODELS.entrySet()) {
			for (Map.Entry<ItemDisplayContext, ModelResourceLocation> entry1 : entry.getValue().entrySet()) {
				event.register(entry1.getValue());
			}
		}
		for (ModelResourceLocation location : ADDITIONAL_MODELS) {
			event.register(location);
		}
		for (ModelResourceLocation location : AACompatManager.getThrownModels()) {
			event.register(location);
		}
	}

	@SubscribeEvent
	private static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
		for (ModelResourceLocation location : event.getModels().keySet()) {
			if (ITEMS_WITH_SPECIAL_MODELS.containsKey(location)) {
				BakedModel model = event.getModels().get(location);
				List<ItemOverrides.BakedOverride> overrides = new ArrayList<>();
				for (ItemOverrides.BakedOverride bakedOverride : model.getOverrides().getOverrides()) {
					if (bakedOverride.model != null) {
						overrides.add(new ItemOverrides.BakedOverride(bakedOverride.matchers, new BakedModelWrapper<>(bakedOverride.model) {
							@Override
							public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
								if (ITEMS_WITH_SPECIAL_MODELS.get(location).containsKey(cameraTransformType)) {
									return event.getModels().get(ITEMS_WITH_SPECIAL_MODELS.get(location).get(cameraTransformType)).applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
								}
								return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
							}
						}));
					}
				}
				event.getModels().put(location, new BakedModelWrapper<>(model) {
					@Override
					public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
						if (ITEMS_WITH_SPECIAL_MODELS.get(location).containsKey(cameraTransformType)) {
							return event.getModels().get(ITEMS_WITH_SPECIAL_MODELS.get(location).get(cameraTransformType)).applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
						}
						return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
					}

					@Override
					public ItemOverrides getOverrides() {
						ItemOverrides itemOverrides = super.getOverrides();
						itemOverrides.overrides = overrides.toArray(new ItemOverrides.BakedOverride[itemOverrides.overrides.length]);
						return itemOverrides;
					}
				});
			}
		}
	}

	@SubscribeEvent
	private static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, AssortedArmaments.id("offhand_attack_indicator"), (graphics, partialTicks) -> DualWieldWeaponTrait.renderOffhandAttackIndicator(graphics));
	}

	@SubscribeEvent
	private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(KEY_MAPPING_REMOVE_JAVELIN);
	}
}
