package team.leomc.assortedarmaments.data.gen.lang;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.client.event.AAClientSetupEvents;
import team.leomc.assortedarmaments.data.AAEnchantments;
import team.leomc.assortedarmaments.integration.aquaculture.AquacultureHelper;
import team.leomc.assortedarmaments.integration.eternalstarlight.EternalStarlightHelper;
import team.leomc.assortedarmaments.integration.twilightforest.TwilightForestHelper;
import team.leomc.assortedarmaments.registry.AAAttributes;
import team.leomc.assortedarmaments.registry.AAEntityTypes;
import team.leomc.assortedarmaments.registry.AAItems;

public class AAChineseLanguageProvider extends LanguageProvider {
	public AAChineseLanguageProvider(PackOutput output) {
		super(output, AssortedArmaments.ID, "zh_cn");
	}

	@Override
	protected void addTranslations() {
		add("name." + AssortedArmaments.ID, "百般武艺");
		add("fml.menu.mods.info.description." + AssortedArmaments.ID, "一个添加更多类型武器的模组");

		add(AAClientSetupEvents.KEY_CATEGORY_ASSORTED_ARMAMENTS, "百般武艺");
		add(Util.makeDescriptionId("key", AssortedArmaments.id("remove_javelin")), "拔出标枪");

		add(AssortedArmaments.ID + ".configuration.blockWalkSpeedModifier", "格挡行走速度因数");
		add(AssortedArmaments.ID + ".configuration.armorBasedAttackDamagePercentage", "基于盔甲的伤害百分比");
		add(AssortedArmaments.ID + ".configuration.sprintExtraAttackDamagePercentage", "疾跑额外伤害百分比");
		add(AssortedArmaments.ID + ".configuration.claymoreSweepAttackCooldown", "大剑横扫攻击冷却");
		add(AssortedArmaments.ID + ".configuration.flailTimePerPowerLevel", "流星锤强度级数增加时间");
		add(AssortedArmaments.ID + ".configuration.flailSpinDamageFactor", "流星锤旋转伤害因数");
		add(AssortedArmaments.ID + ".configuration.flailSpinKnockbackFactor", "流星锤旋转击退因数");
		add(AssortedArmaments.ID + ".configuration.heavyShieldBlockWalkSpeedModifier", "重型战盾格挡行走速度因数");
		add(AssortedArmaments.ID + ".configuration.heavyShieldBlockAttackDamageModifier", "重型战盾格挡攻击伤害因数");
		add(AssortedArmaments.ID + ".configuration.heavyShieldFastBlockTime", "重型战盾快速格挡时间");
		add(AssortedArmaments.ID + ".configuration.heavyShieldFastBlockDamageReflectionPercentage", "重型战盾快速格挡伤害反弹百分比");
		add(AssortedArmaments.ID + ".configuration.heavyShieldFastCounterattackTime", "重型战盾快速反击时间");
		add(AssortedArmaments.ID + ".configuration.ironcladArmorValue", "铁壁护甲值");
		add(AssortedArmaments.ID + ".configuration.parryDuration", "招架持续时间");
		add(AssortedArmaments.ID + ".configuration.parryMeleeDamageReduction", "招架近战伤害减免");
		add(AssortedArmaments.ID + ".configuration.zombieUseWeaponChance", "僵尸使用模组武器几率");
		add(AssortedArmaments.ID + ".configuration.piglinUseWeaponChance", "猪灵使用模组武器几率");
		add(AssortedArmaments.ID + ".configuration.pinUpSyncopeDuration", "钉刺附魔晕厥持续时间");

		add("desc." + AssortedArmaments.ID + ".shift", "按[SHIFT]查看更多信息");

		add("weapon_trait." + AssortedArmaments.ID + ".can_block", "格挡");
		add("weapon_trait." + AssortedArmaments.ID + ".can_block.desc", "可以格挡武器伤害一半大小的近战伤害");
		add("weapon_trait." + AssortedArmaments.ID + ".strong_sweep", "横扫");
		add("weapon_trait." + AssortedArmaments.ID + ".strong_sweep.desc", "横扫攻击时范围内所有目标受到75%的伤害");
		add("weapon_trait." + AssortedArmaments.ID + ".two_handed", "双手武器");
		add("weapon_trait." + AssortedArmaments.ID + ".two_handed.desc", "拿在主手时禁用副手物品");
		add("weapon_trait." + AssortedArmaments.ID + ".large_weapon", "大型武器");
		add("weapon_trait." + AssortedArmaments.ID + ".long_weapon", "长柄武器");
		add("weapon_trait." + AssortedArmaments.ID + ".short_weapon", "短柄武器");
		add("weapon_trait." + AssortedArmaments.ID + ".shock", "钝击");
		add("weapon_trait." + AssortedArmaments.ID + ".shock.desc", "对有护甲的敌人造成基于目标护甲的 %s%% 额外伤害");
		add("weapon_trait." + AssortedArmaments.ID + ".knock", "强打");
		add("weapon_trait." + AssortedArmaments.ID + ".knock.desc", "攻击被格挡时使目标失去格挡能力");
		add("weapon_trait." + AssortedArmaments.ID + ".thump", "重击");
		add("weapon_trait." + AssortedArmaments.ID + ".thump.desc", "下落攻击会暂时击晕目标");
		add("weapon_trait." + AssortedArmaments.ID + ".stab", "刺击");
		add("weapon_trait." + AssortedArmaments.ID + ".stab.desc", "疾跑时增加伤害");
		add("weapon_trait." + AssortedArmaments.ID + ".see_through", "看破");
		add("weapon_trait." + AssortedArmaments.ID + ".see_through.desc", "无视目标无敌时间");
		add("weapon_trait." + AssortedArmaments.ID + ".concentration", "专注");
		add("weapon_trait." + AssortedArmaments.ID + ".concentration.desc", "持续攻击同一目标会逐渐提升攻击伤害与攻击速度");
		add("weapon_trait." + AssortedArmaments.ID + ".lightweight", "轻巧");
		add("weapon_trait." + AssortedArmaments.ID + ".lightweight.desc", "疾跑时提高移动速度");
		add("weapon_trait." + AssortedArmaments.ID + ".dual_wield", "双持");
		add("weapon_trait." + AssortedArmaments.ID + ".dual_wield.desc", "可双持且两把武器的攻击冷却独立计算");
		add("weapon_trait." + AssortedArmaments.ID + ".quick_attack", "快攻");
		add("weapon_trait." + AssortedArmaments.ID + ".quick_attack.desc", "攻击造成目标无敌帧减半");
		add("weapon_trait." + AssortedArmaments.ID + ".axe", "斧刃");
		add("weapon_trait." + AssortedArmaments.ID + ".axe.desc", "视作一把斧头且破坏木制品效率更高");
		add("weapon_trait." + AssortedArmaments.ID + ".heavy_blocking", "重防");
		add("weapon_trait." + AssortedArmaments.ID + ".heavy_blocking.desc", "右键可像盾牌一样格挡，但不会被斧或监守者的攻击禁用");
		add("weapon_trait." + AssortedArmaments.ID + ".shield_parry", "盾反");
		add("weapon_trait." + AssortedArmaments.ID + ".shield_parry.desc", "在格挡时仍可以攻击；开始举盾的短时间内格挡近战攻击会对攻击者造成伤害；使用它格挡伤害并在短时间内使用它伤害攻击者可以将挡下的伤害反还给攻击者");
		add("weapon_trait." + AssortedArmaments.ID + ".ironclad", "铁壁");
		add("weapon_trait." + AssortedArmaments.ID + ".ironclad.desc", "手持时增加 %s 护甲值和等同于武器伤害100%%的盔甲韧性");
		add("weapon_trait." + AssortedArmaments.ID + ".parry", "招架");
		add("weapon_trait." + AssortedArmaments.ID + ".parry.desc", "满冷却攻击后获得短暂招架，期间免疫伤害击退且受到的近战伤害减少 %s%%");

		add("weapon_trait." + AssortedArmaments.ID + ".flail_spin", "旋锤");
		add("weapon_trait." + AssortedArmaments.ID + ".flail_spin.desc", "长按挥动流星锤2秒后，对附近目标造成伤害和击退，松开时抛出流星锤");
		add("weapon_trait." + AssortedArmaments.ID + ".javelin_throw", "投掷");
		add("weapon_trait." + AssortedArmaments.ID + ".javelin_throw.desc", "可投掷并插入目标体内，从目标身上拔出时会造成200%伤害");

		add("weapon_trait." + AssortedArmaments.ID + ".neptunes_might", "海王之力");
		add("weapon_trait." + AssortedArmaments.ID + ".neptunes_might.desc", "增加在水下对敌人造成的伤害");

		add("weapon_trait.modify_interaction_range.desc", "增加 %s 攻击距离和 %s 方块交互距离");

		add(AAItems.WOODEN_CLAYMORE.get(), "木大剑");
		add(AAItems.STONE_CLAYMORE.get(), "石大剑");
		add(AAItems.IRON_CLAYMORE.get(), "铁大剑");
		add(AAItems.GOLDEN_CLAYMORE.get(), "金大剑");
		add(AAItems.DIAMOND_CLAYMORE.get(), "钻石大剑");
		add(AAItems.NETHERITE_CLAYMORE.get(), "下界合金大剑");

		add(AAItems.WOODEN_MACE.get(), "木钉头锤");
		add(AAItems.STONE_MACE.get(), "石钉头锤");
		add(AAItems.IRON_MACE.get(), "铁钉头锤");
		add(AAItems.GOLDEN_MACE.get(), "金钉头锤");
		add(AAItems.DIAMOND_MACE.get(), "钻石钉头锤");
		add(AAItems.NETHERITE_MACE.get(), "下界合金钉头锤");

		add(AAItems.WOODEN_FLAIL.get(), "木流星锤");
		add(AAItems.STONE_FLAIL.get(), "石流星锤");
		add(AAItems.IRON_FLAIL.get(), "铁流星锤");
		add(AAItems.GOLDEN_FLAIL.get(), "金流星锤");
		add(AAItems.DIAMOND_FLAIL.get(), "钻石流星锤");
		add(AAItems.NETHERITE_FLAIL.get(), "下界合金流星锤");
		add(AAEntityTypes.FLAIL.get(), "流星锤");

		add(AAItems.WOODEN_JAVELIN.get(), "木标枪");
		add(AAItems.STONE_JAVELIN.get(), "石标枪");
		add(AAItems.IRON_JAVELIN.get(), "铁标枪");
		add(AAItems.GOLDEN_JAVELIN.get(), "金标枪");
		add(AAItems.DIAMOND_JAVELIN.get(), "钻石标枪");
		add(AAItems.NETHERITE_JAVELIN.get(), "下界合金标枪");
		add(AAEntityTypes.JAVELIN.get(), "标枪");

		add(AAItems.WOODEN_PIKE.get(), "木长枪");
		add(AAItems.STONE_PIKE.get(), "石长枪");
		add(AAItems.IRON_PIKE.get(), "铁长枪");
		add(AAItems.GOLDEN_PIKE.get(), "金长枪");
		add(AAItems.DIAMOND_PIKE.get(), "钻石长枪");
		add(AAItems.NETHERITE_PIKE.get(), "下界合金长枪");

		add(AAItems.WOODEN_RAPIER.get(), "木刺剑");
		add(AAItems.STONE_RAPIER.get(), "石刺剑");
		add(AAItems.IRON_RAPIER.get(), "铁刺剑");
		add(AAItems.GOLDEN_RAPIER.get(), "金刺剑");
		add(AAItems.DIAMOND_RAPIER.get(), "钻石刺剑");
		add(AAItems.NETHERITE_RAPIER.get(), "下界合金刺剑");

		add(AAItems.WOODEN_HALBERD.get(), "木长戟");
		add(AAItems.STONE_HALBERD.get(), "石长戟");
		add(AAItems.IRON_HALBERD.get(), "铁长戟");
		add(AAItems.GOLDEN_HALBERD.get(), "金长戟");
		add(AAItems.DIAMOND_HALBERD.get(), "钻石长戟");
		add(AAItems.NETHERITE_HALBERD.get(), "下界合金长戟");

		add(AAItems.WOODEN_HEAVY_SHIELD.get(), "木重型战盾");
		add(AAItems.STONE_HEAVY_SHIELD.get(), "石重型战盾");
		add(AAItems.IRON_HEAVY_SHIELD.get(), "铁重型战盾");
		add(AAItems.GOLDEN_HEAVY_SHIELD.get(), "金重型战盾");
		add(AAItems.DIAMOND_HEAVY_SHIELD.get(), "钻石重型战盾");
		add(AAItems.NETHERITE_HEAVY_SHIELD.get(), "下界合金重型战盾");

		add(AAItems.WOODEN_CLAW.get(), "木利爪");
		add(AAItems.STONE_CLAW.get(), "石利爪");
		add(AAItems.IRON_CLAW.get(), "铁利爪");
		add(AAItems.GOLDEN_CLAW.get(), "金利爪");
		add(AAItems.DIAMOND_CLAW.get(), "钻石利爪");
		add(AAItems.NETHERITE_CLAW.get(), "下界合金利爪");

		add(AAItems.WOODEN_JIAN.get(), "木锏");
		add(AAItems.STONE_JIAN.get(), "石锏");
		add(AAItems.IRON_JIAN.get(), "铁锏");
		add(AAItems.GOLDEN_JIAN.get(), "金锏");
		add(AAItems.DIAMOND_JIAN.get(), "钻石锏");
		add(AAItems.NETHERITE_JIAN.get(), "下界合金锏");

		add(Util.makeDescriptionId("enchantment", AAEnchantments.KINETIC_ENERGY.location()), "动能");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.KINETIC_ENERGY.location()) + ".desc", "使扔出的流星锤可以造成暴击");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.SUPER_THUMP.location()), "超重击");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.SUPER_THUMP.location()) + ".desc", "投掷的流星锤命中时使目标眩晕(1+附魔等级)×0.5秒");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.INITIAL_VELOCITY.location()), "初速度");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.INITIAL_VELOCITY.location()) + ".desc", "每级增加开始旋锤时获得的初始动能");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.BLAST.location()), "爆裂");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.BLAST.location()) + ".desc", "投出的流星锤命中敌人或方块时，对周围半径1.5格内的敌人造成(25%×等级)的飞锤伤害");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.DEEP_WOUND.location()), "深度创伤");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.DEEP_WOUND.location()) + ".desc", "减少标枪100%投掷伤害，造成400%拔出伤害");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.PIN_UP.location()), "钉刺");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.PIN_UP.location()) + ".desc", "标枪投掷命中时造成击退，若目标因此被击退至墙面则使其晕厥");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.CRIT.location()), "猛击");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.CRIT.location()) + ".desc", "增加(1+附魔等级)*25%暴击伤害");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.WITHSTAND.location()), "抵挡");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.WITHSTAND.location()) + ".desc", "每级增加0.05秒招架时间");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.SPLIT_AIR.location()), "破空");
		add(Util.makeDescriptionId("enchantment", AAEnchantments.SPLIT_AIR.location()) + ".desc", "招架期间弹开前方飞来的弹射物");

		add(AAAttributes.CRITICAL_ATTACK_DAMAGE_MULTIPLIER.get().getDescriptionId(), "暴击伤害倍率");

		add("effect." + AssortedArmaments.ID + ".syncope", "晕厥");
		add("effect." + AssortedArmaments.ID + ".syncope.description", "令目标完全无法动弹");
		add("effect." + AssortedArmaments.ID + ".perforation", "穿孔");
		add("effect." + AssortedArmaments.ID + ".perforation.description", "每次受到伤害时额外受到1点无视护甲伤害");

		add("tooltip." + AssortedArmaments.ID + ".knightmetal_weapon", "对有护甲的目标造成额外伤害");
		add("tooltip." + AssortedArmaments.ID + ".knightmetal_tool", "对无护甲目标造成额外伤害");
		add("tooltip." + AssortedArmaments.ID + ".fiery_weapon", "灼烧攻击目标");
		add("tooltip." + AssortedArmaments.ID + ".fiery_smelting", "自动烧炼");

		add("subtitles." + AssortedArmaments.ID + ".flail.fully_charge", "流星锤：充能完毕");
		add("subtitles." + AssortedArmaments.ID + ".flail.spin", "流星锤：旋转");
		add("subtitles." + AssortedArmaments.ID + ".flail.thrown", "流星锤：扔出");
		add("subtitles." + AssortedArmaments.ID + ".javelin.pull_out", "矛：拔出");

		EternalStarlightHelper.addTranslations(this, false);
		TwilightForestHelper.addTranslations(this, false);
		AquacultureHelper.addTranslations(this, false);
	}
}
