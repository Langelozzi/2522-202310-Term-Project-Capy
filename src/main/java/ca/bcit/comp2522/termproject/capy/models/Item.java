package ca.bcit.comp2522.termproject.capy.models;

public abstract class Item {
    private int cost;
    private int level;

    public Item(int cost, int level) {
        this.cost = cost;
        this.level = level;
    }
}
