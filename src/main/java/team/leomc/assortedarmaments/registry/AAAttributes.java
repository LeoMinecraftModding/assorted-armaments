package team.leomc.assortedarmaments.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;

public class AAAttributes {
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, AssortedArmaments.ID);

	public static final DeferredHolder<Attribute, Attribute> CRITICAL_ATTACK_DAMAGE_MULTIPLIER = ATTRIBUTES.register("critical_attack_damage_multiplier", () -> new RangedAttribute("attribute.name." + AssortedArmaments.ID + ".player.critical_attack_damage_multiplier", 1, 0, 1024));
}
