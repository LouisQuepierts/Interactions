package net.quepierts.interactions.main.data.var;

public final class VarInfo {
    final String def;
    final int index;
    final VarType type;

    VarInfo(String def, int index, VarType type) {
        this.index = index;
        this.def = def;
        this.type = type;
    }

    @Override
    public String toString() {
        return "{type: " + type + ", def: " + def + "}";
    }
}
