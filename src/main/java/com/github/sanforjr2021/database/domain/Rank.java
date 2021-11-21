package com.github.sanforjr2021.database.domain;

public class Rank {
    private String id;
    private int cost;
    private String name;
    private int tier;

    public Rank(String id, int cost, String name, int tier) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.tier = tier;
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "id='" + id + '\'' +
                ", cost=" + cost +
                ", name='" + name + '\'' +
                ", tier=" + tier +
                '}';
    }
}
