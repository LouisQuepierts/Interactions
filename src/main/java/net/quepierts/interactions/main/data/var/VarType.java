package net.quepierts.interactions.main.data.var;

public enum VarType {
    STRING(0), NUMBER(1), BOOLEAN(2);

    private final int index;

    VarType(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }

    public static VarType parse(String id) {
        try {
            return valueOf(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean legal(String id) {
        return parse(id) != null;
    }
}
