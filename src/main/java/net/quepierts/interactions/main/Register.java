package net.quepierts.interactions.main;

import net.quepierts.interactions.main.actions.ActionGiveEffect;
import net.quepierts.interactions.main.conditions.ConditionEquippingItem;
import net.quepierts.interactions.main.config.Branch;
import net.quepierts.interactions.main.config.Entry;
import net.quepierts.interactions.main.data.action.ExecuteType;
import net.quepierts.interactions.main.utils.EntryUtils;

public class Register {
    public static void loadStatic() {
        ExecuteType.register("damaged", 0);
        ExecuteType.register("attack", 0);
        ExecuteType.register("personal_var", 1);
        ExecuteType.register("equipping", 2);
        ExecuteType.register("armor", 2);

        Branch.register("amount", Branch.EnumBranchType.DOUBLE, true);
        Branch.register("chance", Branch.EnumBranchType.DOUBLE, true);

        Branch.register("duration", Branch.EnumBranchType.INT, true);
        Branch.register("level", Branch.EnumBranchType.INT, false, 0);

        Branch.register("compare", Branch.EnumBranchType.BOOLEAN, false, false);
        Branch.register("smaller", Branch.EnumBranchType.BOOLEAN, false, false);
        Branch.register("equal", Branch.EnumBranchType.BOOLEAN, false, false);
        Branch.register("percentage", Branch.EnumBranchType.BOOLEAN, false, false);
        Branch.register("isSet", Branch.EnumBranchType.BOOLEAN, false, false);

        Branch.register("id", Branch.EnumBranchType.STRING, true);
        Branch.register("item", Branch.EnumBranchType.STRING, true);
        Branch.register("slot", Branch.EnumBranchType.STRING, true);
        Branch.register("type", Branch.EnumBranchType.STRING, true);

        Branch.register("varAmountName", Branch.EnumBranchType.STRING, true);
        Branch.register("varTargetName", Branch.EnumBranchType.STRING, true);

        Branch.register("var", Branch.EnumBranchType.SYSTEM, true);
        
        Entry.getBuilder("potion")
                .addBranch(Branch.getInstance("type", "level", "duration"))
                .bind(EntryUtils::getPotionEffect)
                .asBranch().register();

        Entry.getBuilder(ActionGiveEffect.class)
                .addBranch(Branch.getInstance("potion"))
                .bind(ActionGiveEffect::new).register();

        Entry.getBuilder(ConditionEquippingItem.class)
                .addBranch(Branch.getInstance("slot", "item"))
                .executeType(ExecuteType.getByName("equipping"))
                .bind(ConditionEquippingItem::new).register();
    }
}
