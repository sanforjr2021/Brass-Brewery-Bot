package com.github.sanforjr2021.task;

import com.github.sanforjr2021.database.dao.MemberHasRoleDao;
import com.github.sanforjr2021.database.domain.MemberHasRole;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.TimerTask;

import static com.github.sanforjr2021.BrassBreweryBot.GUILD;
import static com.github.sanforjr2021.service.MessageService.sendAuditLog;
import static com.github.sanforjr2021.service.MessageService.sendPrivateMessage;
import static com.github.sanforjr2021.service.RoleService.getExpiredRoles;
import static com.github.sanforjr2021.service.RoleService.removeRole;

public class RemoveExpiredBuyableRoles extends TimerTask {
    @Override
    public void run() {
        ArrayList<MemberHasRole> memberHasRoles;
        memberHasRoles = getExpiredRoles();
        int num = 0;
        for (int i = 0; i < memberHasRoles.size(); i++) {
            num += removeBuyableRole(memberHasRoles.get(i));
        }
        System.out.println(num + " buyable roles removed from users.");
        if(num > 0){
            sendAuditLog(new EmbedBuilder()
                    .setTitle("Brass Brewery Bot")
                    .setDescription(num + " buyable roles removed from users.")
                    .setColor(Color.orange)
                    .build());
        }
    }

    private int removeBuyableRole(MemberHasRole memberHasRole) {
        Member member = GUILD.getMemberById(memberHasRole.getGuildMemberId());
        String roleId = memberHasRole.getRoleId();
        removeRole(member.getUser(), roleId);
        sendPrivateMessage(member.getUser(), messageEmbed());
        return MemberHasRoleDao.delete(memberHasRole);
    }

    private MessageEmbed messageEmbed(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.red);
        embedBuilder.setTitle("Role Expired");
        embedBuilder.setDescription("Your role in the server" + GUILD.getName() + " has expired.");
        embedBuilder.setThumbnail(GUILD.getIconUrl());
        return embedBuilder.build();
    }
}
