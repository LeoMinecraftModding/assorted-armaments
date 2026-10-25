package team.leomc.assortedarmaments.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import team.leomc.assortedarmaments.registry.AADataAttachments;
import team.leomc.assortedarmaments.registry.AASounds;

public class FlailSpinSoundInstance extends AbstractTickableSoundInstance {
	public static final float MAX_KINETIC_POWER = 5.0F;

	private static final float MIN_PITCH = 0.5F;
	private static final float MAX_PITCH = 1.4F;

	private final Player player;

	public FlailSpinSoundInstance(Player player) {
		super(AASounds.FLAIL_SPIN.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
		this.player = player;
		this.looping = true;
		this.delay = 0;
		this.volume = 1.0F;
		this.updatePosition();
		this.pitch = this.calculatePitch();
	}

	@Override
	public void tick() {
		if (this.player.isRemoved()) {
			this.stop();
			return;
		}
		this.updatePosition();
		this.pitch = this.calculatePitch();
	}

	private float calculatePitch() {
		float kineticPower = Mth.clamp(this.player.getData(AADataAttachments.FLAIL_KINETIC_POWER), 0.0F, MAX_KINETIC_POWER);
		float progress = kineticPower / MAX_KINETIC_POWER;
		float eased = progress * progress;
		return Mth.lerp(eased, MIN_PITCH, MAX_PITCH);
	}

	private void updatePosition() {
		this.x = (float) this.player.getX();
		this.y = (float) this.player.getY();
		this.z = (float) this.player.getZ();
	}
}
