package com.github.sanforjr2021.database.domain;

public class DiscordInvite {
    private String inviteCode;
    private String roleId;
    private int inviteCount;

    public DiscordInvite(String inviteCode, String roleId, int inviteCount) {
        this.inviteCode = inviteCode;
        this.roleId = roleId;
        this.inviteCount = inviteCount;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public int getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(int inviteCount) {
        this.inviteCount = inviteCount;
    }
}
