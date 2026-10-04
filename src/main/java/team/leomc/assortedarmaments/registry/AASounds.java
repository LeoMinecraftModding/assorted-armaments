package team.leomc.assortedarmaments.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.leomc.assortedarmaments.AssortedArmaments;

public class AASounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, AssortedArmaments.ID);

	public static final DeferredHolder<SoundEvent, SoundEvent> FLAIL_FULLY_CHARGE = SOUND_EVENTS.register("misc.fully_charge.flail", () -> SoundEvent.createVariableRangeEvent(AssortedArmaments.id("misc.fully_charge.flail")));
}
