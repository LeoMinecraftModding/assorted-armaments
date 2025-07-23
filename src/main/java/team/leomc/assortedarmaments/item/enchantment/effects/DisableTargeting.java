package team.leomc.assortedarmaments.item.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import team.leomc.assortedarmaments.registry.AADataAttachments;

public record DisableTargeting(LevelBasedValue duration) implements EnchantmentEntityEffect {
	public static final MapCodec<DisableTargeting> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(LevelBasedValue.CODEC.fieldOf("duration").forGetter(o -> o.duration)).apply(instance, DisableTargeting::new)
	);

	@Override
	public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
		entity.setData(AADataAttachments.NO_TARGET_TIME, (int) Math.max(entity.getData(AADataAttachments.NO_TARGET_TIME), this.duration.calculate(enchantmentLevel) * 20));
	}

	@Override
	public MapCodec<DisableTargeting> codec() {
		return CODEC;
	}
}
