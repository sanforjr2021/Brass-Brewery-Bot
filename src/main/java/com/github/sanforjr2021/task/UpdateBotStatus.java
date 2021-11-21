package com.github.sanforjr2021.task;

import net.dv8tion.jda.api.entities.Activity;

import java.util.TimerTask;

import static com.github.sanforjr2021.BrassBreweryBot.DISCORD_BOT;

public class UpdateBotStatus extends TimerTask {
    @Override
    public void run() {
        int numOfUsers = DISCORD_BOT.getUsers().size();
        DISCORD_BOT.getPresence().setActivity(Activity.listening("and serving " + numOfUsers + " locals"));

    }
}
