package team.leomc.assortedarmaments.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class SyncopeStarParticle extends TextureSheetParticle {
	private static final float RADIUS = 0.55F;
	private static final float HEIGHT_OFFSET = 0.3F;
	private static final float ANGULAR_SPEED = 0.3F;
	private static final float STAR_SIZE = 0.45F;

	private final int entityId;

	public SyncopeStarParticle(ClientLevel level, LivingEntity entity, int ticks, SpriteSet spriteSet) {
		super(level, entity.getX(), entity.getY() + entity.getBbHeight(), entity.getZ());
		this.entityId = entity.getId();
		this.lifetime = Math.max(ticks, 1);
		this.hasPhysics = false;
		this.quadSize = starSize(entity);
		this.setSpriteFromAge(spriteSet);
	}

	private static float ringRadius(LivingEntity entity) {
		return Mth.clamp(entity.getBbWidth() * 0.4F + 0.2F, 0.35F, 2.0F);
	}

	private static float starSize(LivingEntity entity) {
		return STAR_SIZE * Mth.clamp(1.0F + (entity.getBbWidth() - 0.6F) * 0.18F, 0.75F, 1.7F);
	}

	@Override
	public AABB getRenderBoundingBox(float partialTicks) {
		float radius = this.level.getEntity(this.entityId) instanceof LivingEntity living ? ringRadius(living) : RADIUS;
		return super.getRenderBoundingBox(partialTicks).inflate(radius);
	}

	@Override
	public void tick() {
		if (this.age++ >= this.lifetime) {
			this.remove();
			return;
		}
		if (!(this.level.getEntity(this.entityId) instanceof LivingEntity living) || living.isRemoved() || living.isDeadOrDying()) {
			this.remove();
			return;
		}

		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		double y = living.getY() + living.getBbHeight() + HEIGHT_OFFSET;
		this.setPos(living.getX(), y, living.getZ());
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
		Entity entity = this.level.getEntity(this.entityId);
		if (!(entity instanceof LivingEntity living)) return;

		Vec3 cam = camera.getPosition();
		double cx = Mth.lerp(partialTicks, living.xo, living.getX()) - cam.x;
		double cy = Mth.lerp(partialTicks, living.yo, living.getY()) + living.getBbHeight() + HEIGHT_OFFSET - cam.y;
		double cz = Mth.lerp(partialTicks, living.zo, living.getZ()) - cam.z;

		float time = (this.age + partialTicks) * ANGULAR_SPEED;
		float radius = ringRadius(living);
		float halfSize = starSize(living) * 0.5F;

		for (int i = 0; i < 3; i++) {
			float angle = time + (float)(Math.PI * 2.0 / 3.0 * i);
			float px = (float) (cx + Math.cos(angle) * radius);
			float py = (float) cy;
			float pz = (float) (cz + Math.sin(angle) * radius);

			Quaternionf rotation = new Quaternionf(camera.rotation());
			Vector3f[] corners = {
				new Vector3f(-1.0F, -1.0F, 0.0F),
				new Vector3f(1.0F, -1.0F, 0.0F),
				new Vector3f(1.0F, 1.0F, 0.0F),
				new Vector3f(-1.0F, 1.0F, 0.0F)
			};
			for (Vector3f corner : corners) {
				corner.rotate(rotation).mul(halfSize).add(px, py, pz);
			}

			float u0 = this.getU0(), u1 = this.getU1();
			float v0 = this.getV0(), v1 = this.getV1();

			buffer.addVertex(corners[0].x(), corners[0].y(), corners[0].z()).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setUv(u1, v1).setLight(240).setNormal(0, 0, 1);
			buffer.addVertex(corners[1].x(), corners[1].y(), corners[1].z()).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setUv(u0, v1).setLight(240).setNormal(0, 0, 1);
			buffer.addVertex(corners[2].x(), corners[2].y(), corners[2].z()).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setUv(u0, v0).setLight(240).setNormal(0, 0, 1);
			buffer.addVertex(corners[3].x(), corners[3].y(), corners[3].z()).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setUv(u1, v0).setLight(240).setNormal(0, 0, 1);
		}
	}

	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;
		public Factory(SpriteSet spriteSet) { this.spriteSet = spriteSet; }

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			Entity entity = level.getEntity((int) xSpeed);
			if (entity instanceof LivingEntity living) {
				return new SyncopeStarParticle(level, living, (int) ySpeed, this.spriteSet);
			}
			return null;
		}
	}
}