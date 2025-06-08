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
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.client.renderer.entity.ThrownFlailRenderer;
import team.leomc.assortedarmaments.client.renderer.entity.ThrownJavelinRenderer;
import team.leomc.assortedarmaments.integration.eternalstarlight.EternalStarlightHelper;
import team.leomc.assortedarmaments.registry.AAEntityTypes;
import team.leomc.assortedarmaments.registry.AAItems;
import team.leomc.assortedarmaments.tags.AAItemTags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = AssortedArmaments.ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class AAClientSetupEvents {
	public static final Map<ModelResourceLocation, Map<ItemDisplayContext, ModelResourceLocation>> ITEMS_WITH_SPECIAL_MODELS = Util.make(new HashMap<>(), map -> {
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("wooden_claymore")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_claymore_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_claymore_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_claymore_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_claymore_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("stone_claymore")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_claymore_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_claymore_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_claymore_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_claymore_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("iron_claymore")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_claymore_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_claymore_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_claymore_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_claymore_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("golden_claymore")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_claymore_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_claymore_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_claymore_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_claymore_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("diamond_claymore")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_claymore_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_claymore_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_claymore_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_claymore_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("netherite_claymore")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_claymore_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_claymore_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_claymore_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_claymore_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("wooden_flail")), Map.of(
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_flail"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("stone_flail")), Map.of(
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_flail"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("iron_flail")), Map.of(
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_flail"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("golden_flail")), Map.of(
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_flail"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("diamond_flail")), Map.of(
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_flail"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("netherite_flail")), Map.of(
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_flail"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("wooden_pike")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_pike_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_pike_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_pike_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_pike_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("stone_pike")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_pike_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_pike_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_pike_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_pike_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("iron_pike")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_pike_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_pike_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_pike_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_pike_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("golden_pike")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_pike_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_pike_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_pike_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_pike_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("diamond_pike")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_pike_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_pike_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_pike_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_pike_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("netherite_pike")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_pike_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_pike_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_pike_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_pike_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("wooden_halberd")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_halberd_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_halberd_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_halberd_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_halberd_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("stone_halberd")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_halberd_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_halberd_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_halberd_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_halberd_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("iron_halberd")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_halberd_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_halberd_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_halberd_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_halberd_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("golden_halberd")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_halberd_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_halberd_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_halberd_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_halberd_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("diamond_halberd")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_halberd_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_halberd_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_halberd_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_halberd_inventory"))
		));
		map.put(ModelResourceLocation.inventory(AssortedArmaments.id("netherite_halberd")), Map.of(
			ItemDisplayContext.HEAD, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_halberd_inventory")),
			ItemDisplayContext.GUI, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_halberd_inventory")),
			ItemDisplayContext.GROUND, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_halberd_inventory")),
			ItemDisplayContext.FIXED, ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_halberd_inventory"))
		));

		EternalStarlightHelper.registerAdditionalModel(map);
	});

	public static final List<ModelResourceLocation> ADDITIONAL_MODELS = Lists.newArrayList(
		ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_flail_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/wooden_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/stone_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/iron_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/golden_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/diamond_javelin_thrown")),
		ModelResourceLocation.standalone(AssortedArmaments.id("item/netherite_javelin_thrown"))
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

			EternalStarlightHelper.registerItemProperties();

			ItemProperties.register(AAItems.WOODEN_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.STONE_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.IRON_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.GOLDEN_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.DIAMOND_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);
			ItemProperties.register(AAItems.NETHERITE_FLAIL.get(), AssortedArmaments.id("spinning"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1 : 0);

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
		event.registerItem(new IClientItemExtensions() {
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
		}, AAItems.WOODEN_FLAIL.get(), AAItems.STONE_FLAIL.get(), AAItems.IRON_FLAIL.get(), AAItems.DIAMOND_FLAIL.get(), AAItems.GOLDEN_FLAIL.get(), AAItems.NETHERITE_FLAIL.get());
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
	private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(KEY_MAPPING_REMOVE_JAVELIN);
	}
}
