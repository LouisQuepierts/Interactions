package net.quepierts.interactions.main.utils.math.number;

import net.quepierts.interactions.main.config.objects.VarData;
import net.quepierts.interactions.main.data.var.VarManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IPointedNumber extends IMutableNumber<Number> {
    protected final VarData varData;
    private Player player;

    protected IPointedNumber(String varInfo) {
        this.varData = VarData.parse(varInfo);
        player = null;
    }

    @Override
    public int intValue() {
        return VarManager.getInteger(player, varData.varID);
    }

    @Override
    public long longValue() {
        return VarManager.getLong(player, varData.varID);
    }

    @Override
    public float floatValue() {
        return VarManager.getFloat(player, varData.varID);
    }

    @Override
    public double doubleValue() {
        return VarManager.getDouble(player, varData.varID);
    }

    @Override
    public void increment() {

    }

    @Override
    public void decrement() {

    }

    @Override
    public void add(Number number) {

    }

    @Override
    public void subtract(Number number) {

    }

    @Override
    public void multiply(Number number) {

    }

    @Override
    public void divide(Number number) {

    }

    @Override
    public void setValue(Number number) {

    }

    @Override
    public Number value() {
        return VarManager.getINumber(player, varData.varID);
    }

    @Override
    public void update(@Nullable Player player) {
        if (!varData.global) {
            this.player = player;
        }
    }

    @Override
    public int compareTo(@NotNull Number o) {
        return VarManager.getINumber(player, varData.varID).compareTo(o);
    }
}
