package com.github.sanforjr2021.database.domain;

public class GameRole {
    private String id;
    private String name;
    private String description;
    private String icon;
    private String serverURL;

    public GameRole(String id, String name, String description, String icon, String serverURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.serverURL = serverURL;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getServerURL() {
        return serverURL;
    }

    @Override
    public String toString() {
        return "GameRole{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", serverURL='" + serverURL + '\'' +
                '}';
    }
}
