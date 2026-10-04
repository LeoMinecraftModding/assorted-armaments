package team.leomc.assortedarmaments;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.leomc.assortedarmaments.data.AAEnchantments;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.tags.AAItemTags;

import java.util.List;

public class AAUtils {
	public static void addKnightMetalTooltip(ItemStack stack, List<Component>tooltips){
		if (stack.is(AAItemTags.KNIGHTMETAL_WEAPON)) {
			tooltips.add(Component.translatable("tooltip." + AssortedArmaments.ID + ".knightmetal_weapon").withStyle(ChatFormatting.GRAY));
		} else if (stack.is(AAItemTags.KNIGHTMETAL_TOOL)) {
			tooltips.add(Component.translatable("tooltip." + AssortedArmaments.ID + ".knightmetal_tool").withStyle(ChatFormatting.GRAY));
		}
	}

	public static boolean isKnightMetal(String path){
		return path.contains("knightmetal");
	}

	public static void addFieryTooltip(String path, List<Component>tooltips){
		if (isFiery(path)) {
			tooltips.add(Component.translatable("tooltip." + AssortedArmaments.ID + ".fiery_weapon").withStyle(ChatFormatting.GRAY));
		}
	}

	public static void addFierySmeltingTooltip(String path, List<Component>tooltips){
		if (isFiery(path)) {
			tooltips.add(Component.translatable("tooltip." + AssortedArmaments.ID + ".fiery_smelting").withStyle(ChatFormatting.GRAY));
		}
	}

	public static boolean isFiery(String path){
		return path.contains("fiery");
	}

	public static boolean isSteeleaf(String path){
		return path.contains("steeleaf");
	}

	public static ResourceLocation flailCompatParent(ResourceLocation item, String base) {
		String path = item.getPath();
		String suffix;
		if (isFiery(path)) {
			suffix = "_fiery";
		} else if (isSteeleaf(path)) {
			suffix = "_steeleaf";
		} else {
			suffix = "";
		}
		return AssortedArmaments.id("item/" + base + suffix);
	}

	public static ResourceLocation javelinCompatParent(ResourceLocation item, String base) {
		String path = item.getPath();
		String suffix;
		if (AAUtils.isFiery(path)) {
			suffix = "_fiery";
		} else if (AAUtils.isKnightMetal(path)) {
			suffix = "_knightmetal";
		} else {
			suffix = "";
		}
		return AssortedArmaments.id("item/" + base + suffix);
	}

	public static ResourceLocation heavyShieldCompatParent(ResourceLocation item, String base) {
		String path = item.getPath();
		String suffix;
		if (AAUtils.isFiery(path)) {
			suffix = "_fiery";
		} else if (path.contains("ironwood")) {
			suffix = "_ironwood";
		} else {
			suffix = "";
		}
		return AssortedArmaments.id("item/" + base + suffix);
	}

	public static boolean isFullAttack(LivingEntity attacker) {
		if (!(attacker instanceof Player player)) return true;
		float attackStrengthScale;
		if (attacker.getData(AADataAttachments.OFFHAND_ATTACK)) {
			attackStrengthScale = (attacker.getData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER) + 0.5F) / player.getCurrentItemAttackStrengthDelay();
		} else {
			attackStrengthScale = player.getAttackStrengthScale(0.5F);
		}
		return Mth.clamp(attackStrengthScale, 0.0F, 1.0F) > 0.9F;
	}

	public static boolean isFullAttack(LivingEntity attacker, InteractionHand hand) {
		if (!(attacker instanceof Player player)) return true;
		float attackStrengthScale;
		if (hand == InteractionHand.OFF_HAND) {
			attackStrengthScale = (attacker.getData(AADataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER) + 0.5F) / player.getCurrentItemAttackStrengthDelay();
		} else {
			attackStrengthScale = player.getAttackStrengthScale(0.5F);
		}
		return Mth.clamp(attackStrengthScale, 0.0F, 1.0F) > 0.9F;
	}

	public static boolean hasSplitAir(LivingEntity entity) {
		return !entity.getMainHandItem().isEmpty() && entity.getMainHandItem().getEnchantments().keySet().stream().anyMatch(holder -> holder.is(AAEnchantments.SPLIT_AIR)) ||
			   !entity.getOffhandItem().isEmpty() && entity.getOffhandItem().getEnchantments().keySet().stream().anyMatch(holder -> holder.is(AAEnchantments.SPLIT_AIR));
	}

	public static void grantParry(LivingEntity entity, ItemStack weapon, Entity contextEntity, DamageSource contextSource) {
		int parryDuration = AACommonConfig.parryDuration;
		if (entity.level() instanceof ServerLevel serverLevel) {
			parryDuration = Math.round(AAEnchantments.modifyParryDuration(serverLevel, weapon, contextEntity, contextSource, parryDuration));
		}
		entity.setData(AADataAttachments.PARRY_EXPIRE, entity.level().getGameTime() + parryDuration);
	}
}
