package net.quepierts.interactions.main;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.actions.*;
import net.quepierts.interactions.main.conditions.*;
import net.quepierts.interactions.main.config.objects.EffectCompareInfo;
import net.quepierts.interactions.main.config.Branch;
import net.quepierts.interactions.main.config.Entry;
import net.quepierts.interactions.main.config.objects.VarData;
import net.quepierts.interactions.main.data.action.ExecuteType;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import net.quepierts.interactions.main.utils.EntryUtils;
import net.quepierts.interactions.main.utils.ItemUtils;
import net.quepierts.interactions.main.utils.math.threshold.Threshold;

import static net.quepierts.interactions.main.config.Branch.EnumBranchType.*;

/*
* Register the default things
* */
public class Register {
    public static void loadStatic() {
        Interactions.logger.info("Register");
//      Register Target Slots
        TargetSlot.register("hand", -1);
        TargetSlot.register("off_hand", 40);
        TargetSlot.register("head", 39);
        TargetSlot.register("chest", 38);
        TargetSlot.register("legs", 37);
        TargetSlot.register("feet", 36);

//      Register Execute Types
        ExecuteType.register("kill", 0);
        ExecuteType.register("damaged", 0);
        ExecuteType.register("attack", 0);
        ExecuteType.register("harvest", 0);
        ExecuteType.register("interact", 0);
        ExecuteType.register("update", 1);

//      Register Branches
        Branch.register("amount", NUMBER, true);

        Branch.register("chance", NUMBER, true);
        Branch.register("x", NUMBER, false, 0.0d);
        Branch.register("y", NUMBER, false, 0.0d);
        Branch.register("z", NUMBER, false, 0.0d);
        
        Branch.register("duration", NUMBER, false, 0);
        Branch.register("amplifier", NUMBER, false, 0);

        Branch.register("compare", BOOLEAN, false, false);
        Branch.register("smaller", BOOLEAN, false, false);
        Branch.register("equal", BOOLEAN, false, false);
        Branch.register("percentage", BOOLEAN, false, false);
        Branch.register("isSet", BOOLEAN, false, false);
        Branch.register("target", BOOLEAN, false, false);
        Branch.register("drop", BOOLEAN, false, false);
        Branch.register("global", BOOLEAN, false, false);

        Branch.register("name", STRING, true);
        Branch.register("item", STRING, true).<String>bind(ItemUtils::isAvailableItem);
        Branch.register("block", STRING, false, "AIR").bind(EntryUtils::isBlock);
        Branch.register("slot", STRING, true).<String>bind(TargetSlot::isAvailableSlot);
        Branch.register("effectType", STRING, true).bind(EntryUtils::isAvailableEffect);
        Branch.register("damageCause", STRING, false).bind(EntryUtils::isAvailableDamageCause);
        Branch.register("entityType", STRING, true).bind(EntryUtils::isEntityType);
        Branch.register("interactionAction", STRING, true).bind(EntryUtils::isInteractAction);
        Branch.register("blockFace", STRING, false, null).bind(EntryUtils::isBlockFace);
        Branch.register("biome", STRING, true).bind(EntryUtils::isBiome);

//        Branch.register("lore", STRING_ARRAY, true);

        Branch.register("var", STRING, true).<String>bind(VarData::isLegalValue);
        Branch.register("conditions", CONDITIONS, true);


        Entry.getRegistrar("potion")
                .addBranch(Branch.getInstance("effectType", "amplifier", "duration"))
                .bind(EntryUtils::getPotionEffect)
                .asBranch().register();

//        Entry.getRegistrar("threshold")
//                .addBranch(Branch.getInstance("amountD", "smaller", "equal"))
//                .bind(Threshold::new)
//                .asBranch().register();

        Entry.getRegistrar("threshold")
                .addBranch(Branch.getInstance("amount", "smaller", "equal"))
                .bind(Threshold::new)
                .asBranch().register();

        Entry.getRegistrar("amplifierThreshold")
                .addBranch(Branch.getInstance("amount", "smaller", "equal"))
                .bind(Threshold::new)
                .asBranch().register();

        Entry.getRegistrar("durationThreshold")
                .addBranch(Branch.getInstance("amount", "smaller", "equal"))
                .bind(Threshold::new)
                .asBranch().register();

        Entry.getRegistrar("effectInfo")
                .addBranch(Branch.getInstance("effectType", "amplifierThreshold", "durationThreshold"))
                .bind(EffectCompareInfo::new)
                .asBranch().register();

        Entry.getRegistrar("vector")
                .addBranch(Branch.getInstance("x", "y", "z"))
                .bind(EntryUtils::getVector)
                .asBranch().register();

        Entry.getRegistrar("itemStack")
                .addBranch(Branch.getInstance("item", "amount"))
                .bind(ItemUtils::getItemStack)
                .asBranch().register();

        Entry.getRegistrar("varData")
                .addBranch(Branch.getInstance("name", "global"))
                .bind(VarData::getInstance)
                .asBranch().register();

//      Register Conditions
        Entry.getRegistrar(ConditionContainsItem.class)
                .addBranch(Branch.getInstance("slot", "item"))
                .asRoot("update")
                .bind(ConditionContainsItem::new).register();

        Entry.getRegistrar(ConditionChance.class)
                .addBranch(Branch.getInstance("chance"))
                .bind(ConditionChance::new).register();

        Entry.getRegistrar(ConditionDamage.class)
                .addBranch(Branch.getInstance("threshold", "damageCause"))
                .asRoot("attack")
                .bind(ConditionDamage::new).registerSub("Attack", true)
                .asRoot("damaged")
                .registerSub("Receive", true);

        Entry.getRegistrar(ConditionOR.class)
                .addBranch("conditions")
                .multiRoots()
                .bind(ConditionOR::new).register();

        Entry.getRegistrar(ConditionEffects.class)
                .addBranch(Branch.getInstance("effectType"))
                .bind(ConditionEffects::getPlayer).registerSub("Player", true)
                .asRoot("attack")
                .bind(ConditionEffects::getTarget).registerSub("Target", true);

        Entry.getRegistrar(ConditionHealth.class)
                .addBranch(Branch.getInstance("threshold", "compare"))
                .bind(ConditionHealth::getPlayer).registerSub("Player", true)
                .asRoot("attack")
                .bind(ConditionHealth::getTarget).registerSub("Target", true);

        Entry.getRegistrar(ConditionEffectsInfo.class)
                .addBranch("effectInfo")
                .bind(ConditionEffectsInfo::getPlayer).registerSub("Player", true)
                .asRoot("attack")
                .bind(ConditionEffectsInfo::getTarget).registerSub("Target", true);

        Entry.getRegistrar(ConditionBlock.class)
                .addBranch(Branch.getInstance("block", "vector"))
                .asRoot("update")
                .bind(ConditionBlock::getPlayer).registerSub("Player", true)
                .asRoot("attack")
                .bind(ConditionBlock::getTarget).registerSub("Target", true);

        Entry.getRegistrar(ConditionWorldName.class)
                .addBranch("name")
                .asRoot("update")
                .bind(ConditionWorldName::new).register();

        Entry.getRegistrar(ConditionBreakBlock.class)
                .addBranch("block")
                .asRoot("harvest")
                .bind(ConditionBreakBlock::new).register();

        Entry.getRegistrar(ConditionInteract.class)
                .addBranch(Branch.getInstance("interactionAction", "block", "blockFace"))
                .asRoot("interact")
                .bind(ConditionInteract::new).register();

        Entry.getRegistrar(ConditionBiome.class)
                .addBranch(Branch.getInstance("biome", "vector"))
                .bind(ConditionBiome::new).register();

        Entry.getRegistrar(ConditionEntityType.class)
                .addBranch("entityType")
                .asRoot("attack")
                .bind(ConditionEntityType::getTarget).registerSub("Target", true)
                .asRoot("damaged")
                .bind(ConditionEntityType::getAttacker).registerSub("Attacker", true);

        Entry.getRegistrar(ConditionRidingEntityType.class)
                .addBranch("entityType")
                .asRoot("update").bind(ConditionRidingEntityType.CPlayer::new).registerSub("Player", true)
                .asRoot("attack").bind(ConditionRidingEntityType.CTarget::new).registerSub("Target", true)
                .asRoot("damaged").bind(ConditionRidingEntityType.CAttacker::new).registerSub("Attacker", true);

        Entry.getRegistrar(ConditionVar.class)
                .addBranch(Branch.getInstance("varData", "var"))
                .asRoot("update")
                .bind(ConditionVar::new).register();

        Entry.getRegistrar(ConditionVarBoolean.class)
                .addBranch(Branch.getInstance("varData", "var"))
                .asRoot("update")
                .bind(ConditionVarBoolean::new).register();

        Entry.getRegistrar(ConditionVarString.class)
                .addBranch(Branch.getInstance("varData", "var"))
                .asRoot("update")
                .bind(ConditionVarString::new).register();

        Entry.getRegistrar(ConditionTimeDelta.class)
                .addBranch(Branch.getInstance("name", "threshold"))
                .bind(ConditionTimeDelta::new).register();

//      Register Actions
        Entry.getRegistrar(ActionGivePlayerEffects.class)
                .addBranch("potion")
                .bind(ActionGivePlayerEffects::new).register();

        Entry.getRegistrar(ActionRemovePlayerEffects.class)
                .addBranch("effectType")
                .bind(ActionRemovePlayerEffects::new).register();

        Entry.getRegistrar(ActionDamageManipulate.class)
                .addBranch(Branch.getInstance("amount", "percentage"))
                .bind(ActionDamageManipulate::new)
                .asRoot("attack").registerSub("Attack", true)
                .asRoot("damaged").registerSub("Receive", true);

        Entry.getRegistrar(ActionDurabilityManipulate.class)
                .addBranch(Branch.getInstance("slot", "amount", "percentage"))
                .bind(ActionDurabilityManipulate::new).register();

        Entry.getRegistrar(ActionBlockDrop.class)
                .addBranch(Branch.getInstance("itemStack", "isSet"))
                .asRoot("harvest")
                .bind(ActionBlockDrop::new).register();

        Entry.getRegistrar(ActionFoodLevelManipulate.class)
                .addBranch(Branch.getInstance("amount"))
                .bind(ActionFoodLevelManipulate::getPlayer).registerSub("Player", true)
                .asRoot("attack")
                .bind(ActionFoodLevelManipulate::getTarget).registerSub("Target", true);

        Entry.getRegistrar(ActionGiveItem.class)
                .addBranch("itemStack")
                .bind(ActionGiveItem::new).register();

        Entry.getRegistrar(ActionApplyDamage.class).
                addBranch(Branch.getInstance("amount", "percentage", "damageCause"))
                .bind(ActionApplyDamage::new).register();

        Entry.getRegistrar(ActionVarManipulate.class).addBranch(Branch.getInstance("varData", "var"))
                .bind(ActionVarManipulate::new).register();

        Entry.getRegistrar(ActionVarNumberManipulate.class)
                .addBranch(Branch.getInstance("varData", "amount"))
                .bind(ActionVarNumberManipulate::new).register();

        Interactions.logger.info("Register Finished");
    }
}
