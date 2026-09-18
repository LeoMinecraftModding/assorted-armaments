package team.leomc.assortedarmaments.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.List;

public class MaceItem extends net.minecraft.world.item.MaceItem {
	private final Tier tier;

	public MaceItem(Tier tier, Item.Properties properties) {
		super(properties.component(DataComponents.TOOL, createToolProperties(tier)).durability(tier.getUses()));
		this.tier = tier;
	}

	public static Tool createToolProperties(Tier tier) {
		return new Tool(List.of(Tool.Rule.deniesDrops(tier.getIncorrectBlocksForDrops()), Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, Math.max(tier.getSpeed() / 2f, 1.0f))), 1.0F, 1);
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}

	public Tier getTier() {
		return this.tier;
	}

	public int getEnchantmentValue() {
		return this.tier.getEnchantmentValue();
	}

	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return this.tier.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
		Entity var5 = damageSource.getDirectEntity();
		if (var5 instanceof LivingEntity livingentity) {
			if (!canSmashAttack(livingentity)) {
				return 0.0F;
			} else {
				if (livingentity.level() instanceof ServerLevel serverlevel) {
					return EnchantmentHelper.modifyFallBasedDamage(serverlevel, livingentity.getWeaponItem(), target, damageSource, 0.0F) * livingentity.fallDistance;
				} else {
					return 0.0F;
				}
			}
		}
		return damage;
	}
}
