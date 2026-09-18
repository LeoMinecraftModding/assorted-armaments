package team.leomc.assortedarmaments.trait;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import team.leomc.assortedarmaments.registry.AAWeaponTraits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public record WeaponTraitsComponent(List<Holder<WeaponTrait>> traits, boolean showInTooltip) implements TooltipProvider {
	public static final WeaponTraitsComponent EMPTY = new WeaponTraitsComponent(List.of(), true);

	public static final Codec<WeaponTraitsComponent> CODEC =
		RegistryFixedCodec.create(AAWeaponTraits.WEAPON_TRAIT_KEY)
			.listOf()
			.xmap(list -> new WeaponTraitsComponent(list, true), WeaponTraitsComponent::traits
			);

	public static final StreamCodec<RegistryFriendlyByteBuf, WeaponTraitsComponent> STREAM_CODEC =
		StreamCodec.composite(
			ByteBufCodecs.holderRegistry(AAWeaponTraits.WEAPON_TRAIT_KEY).apply(ByteBufCodecs.list()),
			WeaponTraitsComponent::traits,
			ByteBufCodecs.BOOL,
			WeaponTraitsComponent::showInTooltip,
			WeaponTraitsComponent::new);

	public WeaponTraitsComponent {
		traits = List.copyOf(traits);
	}

	public boolean hasTrait(Holder<WeaponTrait> trait) {
		return trait != null && traits.contains(trait);
	}

	public List<Holder<WeaponTrait>> keySet() {
		return Collections.unmodifiableList(traits);
	}

	public int size() {
		return traits.size();
	}

	public boolean isEmpty() {
		return traits.isEmpty();
	}

	public WeaponTraitsComponent withTraitAdded(Holder<WeaponTrait> trait) {
		if (trait == null || traits.contains(trait)) return this;
		List<Holder<WeaponTrait>> list = new ArrayList<>(traits);
		list.add(trait);
		return new WeaponTraitsComponent(list, showInTooltip);
	}

	public WeaponTraitsComponent withTraitRemoved(Holder<WeaponTrait> trait) {
		if (trait == null || !traits.contains(trait)) return this;
		List<Holder<WeaponTrait>> list = new ArrayList<>(traits);
		list.remove(trait);
		return new WeaponTraitsComponent(list, showInTooltip);
	}

	public WeaponTraitsComponent withTooltip(boolean show) {
		return new WeaponTraitsComponent(traits, show);
	}

	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag) {
		if (!showInTooltip) return;

		boolean shift = Screen.hasShiftDown();

		for (Holder<WeaponTrait> holder : traits) {
			WeaponTrait trait = holder.value();
			tooltipAdder.accept(trait.getName().copy().append(shift ? Component.literal(":") : CommonComponents.space().append(Component.translatable("desc.assorted_armaments.shift").withStyle(ChatFormatting.DARK_GRAY))));

			if (shift) {
				tooltipAdder.accept(CommonComponents.space().append(trait.getDescription()));
			}
		}
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		return other instanceof WeaponTraitsComponent(
			List<Holder<WeaponTrait>> list, boolean inTooltip)
			&& this.showInTooltip == inTooltip
			&& this.traits.equals(list);
	}

	@Override
	public int hashCode() {
		return 31 * traits.hashCode() + (showInTooltip ? 1 : 0);
	}

	@Override
	public String toString() {
		return "WeaponTraits{traits=" + traits + ", showInTooltip=" + showInTooltip + "}";
	}
}
