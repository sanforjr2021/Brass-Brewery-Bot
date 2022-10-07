package com.github.sanforjr2021.util;

import com.github.sanforjr2021.database.domain.DiscordInvite;
import com.github.sanforjr2021.service.RoleService;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.util.ArrayList;
import java.util.List;

import static com.github.sanforjr2021.BrassBreweryBot.GUILD;

public class OnGuildMemberJoin{
    public OnGuildMemberJoin(GuildMemberJoinEvent event){
        ArrayList<DiscordInvite> discordInviteArrayList = RoleService.getAllDiscordInvites();
        List<Invite> inviteList =  GUILD.retrieveInvites().complete();
        for (DiscordInvite discordInvite: discordInviteArrayList) {
            for(Invite invite : inviteList){
                if(invite.getUrl().contains(discordInvite.getInviteCode())){
                    if(invite.getUses() > discordInvite.getInviteCount() && invite.isTemporary() == false){
                        discordInvite.setInviteCount(invite.getUses());
                        RoleService.addRoleToUser(event.getUser(), discordInvite.getRoleId());
                        RoleService.updateDiscordInvite(discordInvite);
                    }
                }
            }
        }
    }
}
