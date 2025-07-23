package team.leomc.assortedarmaments.entity;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.leomc.assortedarmaments.registry.AAEntityTypes;
import team.leomc.assortedarmaments.registry.AAItems;

import java.util.UUID;

public class ThrownJavelin extends AbstractArrow {
	private static final String TAG_HIT_TARGET = "hit_target";
	private static final String TAG_TARGET = "target";
	private static final String TAG_IN_TARGET = "in_target";
	private static final String TAG_FIXED_PITCH = "fixed_pitch";
	private static final String TAG_FIXED_YAW = "fixed_yaw";

	private boolean hitTarget;

	@Nullable
	private Entity target;
	@Nullable
	private UUID targetId;

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.targetId = target.getUUID();
		this.target = target;
	}

	private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(ThrownJavelin.class, EntityDataSerializers.ITEM_STACK);

	public void setItem(ItemStack stack) {
		this.getEntityData().set(ITEM_STACK, stack.copyWithCount(1));
	}

	public ItemStack getItem() {
		return this.getEntityData().get(ITEM_STACK);
	}

	protected static final EntityDataAccessor<Boolean> IN_TARGET = SynchedEntityData.defineId(ThrownJavelin.class, EntityDataSerializers.BOOLEAN);

	public boolean isInTarget() {
		return this.getEntityData().get(IN_TARGET);
	}

	public void setInTarget(boolean inTarget) {
		this.getEntityData().set(IN_TARGET, inTarget);
	}

	protected static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(ThrownJavelin.class, EntityDataSerializers.INT);

	public int getTargetId() {
		return this.getEntityData().get(TARGET_ID);
	}

	public void setTargetId(int targetId) {
		this.getEntityData().set(TARGET_ID, targetId);
	}

	protected static final EntityDataAccessor<Float> FIXED_PITCH = SynchedEntityData.defineId(ThrownJavelin.class, EntityDataSerializers.FLOAT);

	public float getFixedPitch() {
		return this.getEntityData().get(FIXED_PITCH);
	}

	public void setFixedPitch(float fixedPitch) {
		this.getEntityData().set(FIXED_PITCH, fixedPitch);
	}

	protected static final EntityDataAccessor<Float> FIXED_YAW = SynchedEntityData.defineId(ThrownJavelin.class, EntityDataSerializers.FLOAT);

	public float getFixedYaw() {
		return this.getEntityData().get(FIXED_YAW);
	}

	public void setFixedYaw(float fixedYaw) {
		this.getEntityData().set(FIXED_YAW, fixedYaw);
	}

	public ThrownJavelin(EntityType<? extends ThrownJavelin> entityType, Level level) {
		super(entityType, level);
	}

	public ThrownJavelin(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
		super(AAEntityTypes.JAVELIN.get(), shooter, level, pickupItemStack, pickupItemStack);
		setItem(pickupItemStack);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ITEM_STACK, new ItemStack(AAItems.WOODEN_JAVELIN.get()))
			.define(IN_TARGET, false)
			.define(TARGET_ID, -1)
			.define(FIXED_PITCH, 0f)
			.define(FIXED_YAW, 0f);
	}

	@Override
	public void push(Entity entity) {

	}

	@Override
	public void push(double x, double y, double z) {

	}

	@Override
	public void tick() {
		if (isInTarget()) {
			setDeltaMovement(Vec3.ZERO);
			super.baseTick();
		} else {
			super.tick();
		}
		if (!level().isClientSide) {
			if (tickCount % 5 == 0) {
				setItem(getItem());
			}
			if (target == null && targetId != null) {
				Entity entity = ((ServerLevel) this.level()).getEntity(targetId);
				if (entity != null) {
					target = entity;
				}
				if (target == null) {
					targetId = null;
				}
			}
			if (target != null) {
				setTargetId(target.getId());
				if (isInTarget()) {
					setPos(target.position().add(0, target.getBbHeight() / 2, 0));
				}
				if (target.isRemoved() || (target instanceof LivingEntity living && living.isDeadOrDying())) {
					targetId = null;
					target = null;
				}
			} else {
				setInTarget(false);
			}
			setNoPhysics(isInTarget());
			setNoGravity(isInTarget());
		} else {
			Entity attached = level().getEntity(getTargetId());
			if (isInTarget() && attached != null) {
				setPos(attached.position().add(0, attached.getBbHeight() / 2, 0));
				this.xOld = attached.xOld;
				this.yOld = attached.yOld + attached.getBbHeight() / 2;
				this.zOld = attached.zOld;
			}
		}
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean isAttackable() {
		return true;
	}

	public boolean removeFromTarget(Player player) {
		if (isInTarget() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem())) {
			if (target != null) {
				DamageSource source = this.damageSources().playerAttack(player);
				if (damageTarget(target, source, 2, false) && level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(ParticleTypes.CRIT, position().x(), position().y(), position().z(), 10, 0.1, 0.1, 0.1, 0.5);
				}
				discard();
				return true;
			}
		}
		return false;
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand) {
		if (!level().isClientSide && removeFromTarget(player)) {
			return InteractionResult.CONSUME;
		}
		return super.interact(player, hand);
	}

	@Override
	public void playerTouch(Player player) {
		if (!isInTarget() && hitTarget) {
			super.playerTouch(player);
		}
	}

	@Override
	protected boolean tryPickup(Player player) {
		return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return AAItems.WOODEN_JAVELIN.get().getDefaultInstance();
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		hitTarget = true;
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		Entity entity = result.getEntity();
		if (!hitTarget && entity != getOwner()) {
			DamageSource source = this.damageSources().thrown(this, getOwner());
			if (damageTarget(entity, source, 1, true)) {
				ItemStack origin = getPickupItemStackOrigin();
				if (origin.getDamageValue() >= origin.getMaxDamage() - 1) {
					discard();
				} else {
					origin.setDamageValue(origin.getDamageValue() + 1);
					if (!level().isClientSide) {
						setInTarget(true);
						setTarget(entity);
						setTargetId(entity.getId());
						setFixedPitch(getXRot());
						float yawOffset = entity.getYRot();
						if (entity instanceof LivingEntity living) {
							yawOffset = living.yBodyRot;
						}
						setFixedYaw(getYRot() + yawOffset);
					}
				}
			}
		}
	}

	private double getItemDamage(double baseValue) {
		double result = baseValue;
		ItemAttributeModifiers modifiers = getItem().getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
		for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
			if (entry.attribute() == Attributes.ATTACK_DAMAGE) {
				double amount = entry.modifier().amount();
				double addition;
				switch (entry.modifier().operation()) {
					case ADD_VALUE -> addition = amount;
					case ADD_MULTIPLIED_BASE -> addition = amount * baseValue;
					case ADD_MULTIPLIED_TOTAL -> addition = amount * result;
					default -> throw new MatchException(null, null);
				}
				result += addition;
			}
		}
		return result;
	}

	private boolean damageTarget(Entity entity, DamageSource source, float scale, boolean doKnockback) {
		// a tricky way to use melee weapon enchantments
		DamageSource directSource = getPlayerOwner() != null ? this.damageSources().playerAttack(this.getPlayerOwner()) : (getOwner() instanceof LivingEntity living ? damageSources().mobAttack(living) : damageSources().thrown(this, getOwner()));
		float damage = (float) getItemDamage(getOwner() instanceof LivingEntity living && living.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? (float) living.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) : 1);
		float knockback = getOwner() instanceof LivingEntity living && living.getAttributes().hasAttribute(Attributes.ATTACK_KNOCKBACK) ? calculateKnockback(living, entity, directSource) : 1;
		if (level() instanceof ServerLevel serverLevel && getWeaponItem() != null) {
			damage = EnchantmentHelper.modifyDamage(serverLevel, getWeaponItem(), entity, directSource, damage);
			knockback = EnchantmentHelper.modifyKnockback(serverLevel, getWeaponItem(), entity, source, knockback);
		}
		damage *= scale;
		boolean flag = entity.hurt(source, damage);
		if (flag && level() instanceof ServerLevel serverLevel) {
			EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, directSource, getWeaponItem());
		}
		if (doKnockback && knockback > 0.0F && entity instanceof LivingEntity living && getOwner() != null) {
			double x = getOwner().getX() - entity.getX();
			double z = getOwner().getZ() - entity.getZ();
			double d = x * x + z * z;
			if (d != 0) {
				living.knockback(knockback * 0.5, x / d, z / d);
			}
		}
		return flag;
	}


	public float calculateKnockback(LivingEntity owner, Entity target, DamageSource damageSource) {
		float knockback = (float) owner.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
		float result;
		if (level() instanceof ServerLevel serverlevel) {
			result = EnchantmentHelper.modifyKnockback(serverlevel, this.getWeaponItem(), target, damageSource, knockback);
		} else {
			result = knockback;
		}

		return result;
	}

	@Nullable
	public Player getPlayerOwner() {
		Entity entity = this.getOwner();
		return entity instanceof Player ? (Player) entity : null;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setItem(getPickupItemStackOrigin());
		this.hitTarget = compound.getBoolean(TAG_HIT_TARGET);
		if (compound.hasUUID(TAG_TARGET)) {
			targetId = compound.getUUID(TAG_TARGET);
		}
		setInTarget(compound.getBoolean(TAG_IN_TARGET));
		setFixedPitch(compound.getFloat(TAG_FIXED_PITCH));
		setFixedYaw(compound.getFloat(TAG_FIXED_YAW));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean(TAG_HIT_TARGET, this.hitTarget);
		if (target != null) {
			compound.putUUID(TAG_TARGET, target.getUUID());
		}
		compound.putBoolean(TAG_IN_TARGET, isInTarget());
		compound.putFloat(TAG_FIXED_PITCH, getFixedPitch());
		compound.putFloat(TAG_FIXED_YAW, getFixedYaw());
	}
}
