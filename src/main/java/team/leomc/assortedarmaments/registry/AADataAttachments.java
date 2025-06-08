package team.leomc.assortedarmaments.registry;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.entity.ThrownFlail;

public class AADataAttachments {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, AssortedArmaments.ID);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<ThrownFlail>> FLAIL = ATTACHMENT_TYPES.register("flail", () -> AttachmentType.builder(() -> (ThrownFlail) null).build());
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<LivingEntity>> CONCENTRATED_TARGET = ATTACHMENT_TYPES.register("concentrated_target", () -> AttachmentType.builder(() -> (LivingEntity) null).build());
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<ItemStack>> CONCENTRATED_WEAPON = ATTACHMENT_TYPES.register("concentrated_weapon", () -> AttachmentType.builder(() -> (ItemStack) null).build());
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> LAST_CONCENTRATED_ATTACK_TIME = ATTACHMENT_TYPES.register("last_concentrated_attack_time", () -> AttachmentType.builder(() -> Integer.MIN_VALUE).build());
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> CONCENTRATION_LEVEL = ATTACHMENT_TYPES.register("concentration_level", () -> AttachmentType.builder(() -> 0).build());
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> LAST_HEAVY_SHIELD_BLOCKED_DAMAGE = ATTACHMENT_TYPES.register("last_heavy_shield_blocked_damage", () -> AttachmentType.builder(() -> 0f).build());
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> LAST_HEAVY_SHIELD_BLOCKED_DAMAGE_TIME = ATTACHMENT_TYPES.register("last_heavy_shield_blocked_damage_time", () -> AttachmentType.builder(() -> Integer.MIN_VALUE).build());
}
