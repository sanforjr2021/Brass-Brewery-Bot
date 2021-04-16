package sanford.task;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import sanford.BrassBreweryBot;

import java.util.TimerTask;

public class UpdateBotStatusTask extends TimerTask {


    public UpdateBotStatusTask() {
    }

    @Override
    public void run() {
        JDA jda = BrassBreweryBot.getJDA();
        int numOfUsers = jda.getUsers().size();
        jda.getPresence().setActivity(Activity.playing("Servering " + numOfUsers + " of users"));

    }
}
