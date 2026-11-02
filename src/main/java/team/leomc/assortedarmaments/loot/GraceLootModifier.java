package team.leomc.assortedarmaments.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import team.leomc.assortedarmaments.AAUtils;
import team.leomc.assortedarmaments.entity.ThrownFlail;
import team.leomc.assortedarmaments.entity.ThrownJavelin;
import team.leomc.assortedarmaments.integration.aether.AetherHelper;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;
import team.leomc.assortedarmaments.trait.WeaponTraitHelper;

public class GraceLootModifier extends LootModifier {
	public static final MapCodec<GraceLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
		codecStart(instance).apply(instance, GraceLootModifier::new));

	public GraceLootModifier(LootItemCondition[] conditions) {
		super(conditions);
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
		Entity attacker = context.getParamOrNull(LootContextParams.DIRECT_ATTACKING_ENTITY);
		Entity target = context.getParamOrNull(LootContextParams.THIS_ENTITY);
		if (target == null) {
			return lootStacks;
		}
		ItemStack weapon;
		if (attacker instanceof LivingEntity living) {
			if (!AAUtils.isFullAttack(living)) {
				return lootStacks;
			}
			weapon = WeaponTraitHelper.getAttackingWeapon(living);
		} else if (attacker instanceof ThrownFlail flail) {
			weapon = flail.getItem();
		} else if (attacker instanceof ThrownJavelin javelin) {
			weapon = javelin.getWeaponItem();
		} else {
			return lootStacks;
		}
		if (!WeaponTraitHelper.hasTrait(AAWeaponTraits.GRACE, weapon)
			|| target.getType().is(AetherHelper.NO_SKYROOT_DOUBLE_DROPS_ENTITY)) {
			return lootStacks;
		}
		ObjectArrayList<ItemStack> doubled = new ObjectArrayList<>(lootStacks);
		for (ItemStack stack : lootStacks) {
			if (!stack.is(AetherHelper.NO_SKYROOT_DOUBLE_DROPS_ITEM)) {
				doubled.add(stack.copy());
			}
		}
		return doubled;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}
}
