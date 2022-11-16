package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.utils.BlockUtils;
import net.quepierts.interactions.main.utils.EntryUtils;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ConditionInteract extends AbstractCondition {
    private final Action action;
    private final String block;
    private final BlockFace blockFace;
    public ConditionInteract(Object[] args) {
        super(args);

        this.action = EntryUtils.getInteractAction((String) args[0]);
        this.block = (String) args[1];
        this.blockFace = EntryUtils.getBlockFace((String) args[2]);
    }

    @Override
    protected boolean condition(Player player, Event event) {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interactEvent = (PlayerInteractEvent) event;
            if (interactEvent.getAction().equals(action)) {
                Block clickedBlock = interactEvent.getClickedBlock();
                if (clickedBlock != null && !BlockUtils.isSame(clickedBlock, block)) {
                    return false;
                }

                return blockFace == null || blockFace.equals(interactEvent.getBlockFace());
            }
        }
        return false;
    }
}
