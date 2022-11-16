package net.quepierts.interactions.main.config.objects;

public class ItemStackInfo {
    public final String itemName;
    public final int amount;

    public ItemStackInfo(Object[] args) {
        this.itemName = (String) args[0];
        this.amount = (int) args[1];
    }
}
