package team.leomc.assortedarmaments.data.gen.lang;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import team.leomc.assortedarmaments.AssortedArmaments;
import team.leomc.assortedarmaments.client.event.AAClientSetupEvents;
import team.leomc.assortedarmaments.integration.eternalstarlight.EternalStarlightHelper;
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
		add(AssortedArmaments.ID + ".configuration.speedBasedAttackDamageModifier", "基于速度的伤害因数");
		add(AssortedArmaments.ID + ".configuration.flailMaxUseDuration", "流星锤最长使用时间");
		add(AssortedArmaments.ID + ".configuration.flailThrowMinUseDuration", "投掷流星锤最短所需时间");
		add(AssortedArmaments.ID + ".configuration.flailSpinCooldown", "流星锤旋转冷却");
		add(AssortedArmaments.ID + ".configuration.flailSpinDamageFactor", "流星锤旋转伤害因数");
		add(AssortedArmaments.ID + ".configuration.flailSpinKnockbackFactor", "流星锤旋转击退因数");
		add(AssortedArmaments.ID + ".configuration.zombieUseWeaponChance", "僵尸使用模组武器几率");
		add(AssortedArmaments.ID + ".configuration.piglinUseWeaponChance", "猪灵使用模组武器几率");

		add("desc." + AssortedArmaments.ID + ".shift", "按[SHIFT]查看更多信息");
		add("desc." + AssortedArmaments.ID + ".can_block", "可以格挡武器伤害一半大小的近战伤害");
		add("desc." + AssortedArmaments.ID + ".strong_sweep", "横扫攻击时范围内所有目标受到与直接攻击一致的伤害");
		add("desc." + AssortedArmaments.ID + ".two_handed", "拿在主手时禁用副手物品");
		add("desc." + AssortedArmaments.ID + ".armor_based_damage", "对有护甲的敌人造成额外伤害");
		add("desc." + AssortedArmaments.ID + ".disables_blocking_on_attack", "攻击被格挡时使目标失去格挡能力");
		add("desc." + AssortedArmaments.ID + ".extra_knockback", "可造成额外击退");
		add("desc." + AssortedArmaments.ID + ".speed_based_damage", "疾跑时根据速度增加伤害");

		add("desc." + AssortedArmaments.ID + ".flails", "可投掷");
		add("desc." + AssortedArmaments.ID + ".javelins", "可投掷并插入敌人体内，从受害者身上拔出时也会造成伤害");
		add("desc." + AssortedArmaments.ID + ".rapiers", "持续攻击同一个目标可逐渐提高伤害");

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

		EternalStarlightHelper.addTranslations(this, false);
	}
}
