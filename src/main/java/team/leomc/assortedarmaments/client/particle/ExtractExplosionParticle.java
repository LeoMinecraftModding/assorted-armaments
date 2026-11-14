package team.leomc.assortedarmaments.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtractExplosionParticle extends TextureSheetParticle {
	private static final float BASE_QUAD_SIZE = 0.55F;

	protected ExtractExplosionParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
		super(level, x, y, z);
		this.hasPhysics = false;
		this.gravity = 0.0F;
		this.friction = 1.0F;
		this.lifetime = 10 + this.random.nextInt(6);
		this.quadSize = BASE_QUAD_SIZE * (0.7F + this.random.nextFloat() * 0.6F);
		this.xd = (this.random.nextDouble() - 0.5D) * 0.06D;
		this.yd = 0.02D + this.random.nextDouble() * 0.04D;
		this.zd = (this.random.nextDouble() - 0.5D) * 0.06D;
		this.roll = (float) (this.random.nextDouble() * Math.PI * 2.0D);
		this.oRoll = this.roll;
		this.setSpriteFromAge(sprites);
	}

	@Override
	public void tick() {
		super.tick();
		float progress = Mth.clamp((float) this.age / (float) this.lifetime, 0.0F, 1.0F);
		this.alpha = 1.0F - progress * progress;
		this.quadSize *= 1.03F;
	}

	@Override
	protected int getLightColor(float partialTick) {
		return LightTexture.FULL_BRIGHT;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Factory(SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new ExtractExplosionParticle(level, x, y, z, this.sprites);
		}
	}
}
