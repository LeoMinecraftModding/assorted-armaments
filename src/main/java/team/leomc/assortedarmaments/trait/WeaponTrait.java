package team.leomc.assortedarmaments.trait;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

import java.util.List;

public class WeaponTrait {

	public WeaponTrait() {
	}

	public ResourceLocation getId() {
		return AAWeaponTraits.WEAPON_TRAIT_REGISTRY.getKey(this);
	}

	public Component getName() {
		return Component.translatable("weapon_trait." + getId().getNamespace() + "." + getId().getPath()).withStyle(ChatFormatting.GREEN);
	}

	public Component getDescription() {
		return Component.translatable("weapon_trait." + getId().getNamespace() + "." + getId().getPath() + ".desc", descriptionArgs()).withStyle(ChatFormatting.GRAY);
	}

	public Object[] descriptionArgs() {
		return new Object[0];
	}

	public List<TraitAttribute> attributes() {
		return List.of();
	}

	public record TraitAttribute(Holder<Attribute> attribute, AttributeModifier.Operation operation, double amount) {
		public ResourceLocation makeId(ResourceLocation traitId) {
			ResourceLocation attrId = attribute.unwrapKey().orElseThrow().location();
			return ResourceLocation.fromNamespaceAndPath(traitId.getNamespace(), "weapon_trait/" + traitId.getPath() + "/" + attrId.getPath()
			);
		}

		public AttributeModifier createModifier(ResourceLocation traitId) {
			return new AttributeModifier(makeId(traitId), amount, operation);
		}
	}
}