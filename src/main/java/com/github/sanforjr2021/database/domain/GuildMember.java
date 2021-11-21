package com.github.sanforjr2021.database.domain;

public class GuildMember {
    private String id;
    private int currency;

    public GuildMember(String id, int currency) {
        this.id = id;
        this.currency = currency;
    }

    public GuildMember(String id) {
        this.id = id;
        currency = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "GuildMember{" +
                "id='" + id + '\'' +
                ", currency=" + currency +
                '}';
    }
}
