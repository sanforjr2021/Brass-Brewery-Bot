package com.github.sanforjr2021.database.domain;

public class MemberHasRole {
    private String guildMemberId;
    private String roleId;
    private long date;

    public MemberHasRole(String guildMemberId, String roleId, long date) {
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
