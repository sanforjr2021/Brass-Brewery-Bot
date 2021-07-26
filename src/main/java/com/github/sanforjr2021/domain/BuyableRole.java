package com.github.sanforjr2021.domain;

public class BuyableRole {
    private String id;
    private int cost;
    private String name;

    public BuyableRole(String id, int cost, String name) {
        this.id = id;
        this.cost = cost;
        this.name = name;
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
}
