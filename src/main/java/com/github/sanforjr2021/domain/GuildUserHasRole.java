package com.github.sanforjr2021.domain;

import java.util.Date;

//TODO: Create Corresponding DAO for it

public class GuildUserHasRole {
    private String guildMemberId;
    private String roleId;
    private Date date;

    public GuildUserHasRole(String guildMemberId, String roleId, Date date) {
        this.guildMemberId = guildMemberId;
        this.roleId = roleId;
        this.date = date;
    }

    public String getGuildMemberId() {
        return guildMemberId;
    }

    public void setGuildMemberId(String guildMemberId) {
        this.guildMemberId = guildMemberId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
