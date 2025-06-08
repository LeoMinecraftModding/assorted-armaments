package team.leomc.assortedarmaments;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import team.leomc.assortedarmaments.registry.*;

@Mod(AssortedArmaments.ID)
public class AssortedArmaments {
	public static final String ID = "assorted_armaments";

	public AssortedArmaments(IEventBus modBus, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, AACommonConfig.SPEC);
		AAItems.ITEMS.register(modBus);
		AACreativeModeTabs.TABS.register(modBus);
		AAEntityTypes.ENTITY_TYPES.register(modBus);
		AADataComponents.register(modBus);
		AADataAttachments.ATTACHMENT_TYPES.register(modBus);
	}

	public static ResourceLocation id(String string) {
		return ResourceLocation.fromNamespaceAndPath(ID, string);
	}

	public static String strId(String string) {
		return ID + ":" + string;
	}

	@OnlyIn(Dist.CLIENT)
	public static class ClientHelper {
		public static boolean isShiftKeyDown() {
			return Screen.hasShiftDown();
		}
	}
}
