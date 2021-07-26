package com.github.sanforjr2021.service;

import com.github.sanforjr2021.domain.BuyableRole;
import com.github.sanforjr2021.domain.GuildMember;
import com.github.sanforjr2021.domain.Rank;

public class CurrencyService {

    public static GuildMember removeMembersCurrency(GuildMember member, Rank rank) {
        member.setCurrency(member.getCurrency() - rank.getCost());
        return member;
    }

    public static GuildMember removeMemberCurrency(GuildMember member, int cost) {
        member.setCurrency(member.getCurrency() - cost);
        return member;
    }

    public static GuildMember removeMemberCurrency(GuildMember member, BuyableRole role) {
        member.setCurrency(member.getCurrency() - role.getCost());
        return member;
    }

    public static boolean canAffordRole(GuildMember member, Rank rank) {
        boolean canGetRole = false;
        if (member.getCurrency() >= rank.getCost()) {
            canGetRole = true;
        }
        return canGetRole;
    }

    public static boolean canAffordRole(GuildMember member, BuyableRole role) {
        boolean canGetRole = false;
        if (member.getCurrency() >= role.getCost()) {
            canGetRole = true;
        }
        return canGetRole;
    }

    public static boolean canAffordRole(GuildMember member, int cost) {
        boolean canGetRole = false;
        if (member.getCurrency() >= cost) {
            canGetRole = true;
        }
        return canGetRole;
    }

    public static GuildMember addPoints(GuildMember member, int points){
        member.setCurrency(member.getCurrency()+points);
        return member;
    }

}
