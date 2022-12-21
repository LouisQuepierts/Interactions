package net.quepierts.interactions.main.utils;

import net.quepierts.interactions.main.config.objects.VarData;
import net.quepierts.interactions.main.data.var.VarManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IPointedString extends IString {
    private final VarData varData;
    private Player player;
    public IPointedString(String varInfo) {
        super();

        this.varData = VarData.parse(varInfo);
    }

    @Override
    public String getValue() {
        return VarManager.getString(player, varData.varID);
    }

    @Override
    public void update(@NotNull Player player) {
        if (!varData.global) {
            this.player = player;
        }
    }
}
