package com.github.sanforjr2021.bot.command;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;
import static com.github.sanforjr2021.BrassBreweryBot.GUILD;

public class SlashCommandHandler extends ListenerAdapter {
    private Ping ping = new Ping();
    private CommandListUpdateAction commands;
    public SlashCommandHandler(){
        commands = GUILD.updateCommands();
        commands.addCommands(ping.getCommandData());
        commands.queue();
        System.out.println("Commands Registered");
    }
    /*
    Smart commands with options example:
    https://stackoverflow.com/questions/67871884/add-multiple-possible-options-in-a-slash-command
     */
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        switch (event.getName()){
            case "ping":
                ping.executeCommand(event);
                break;
            default:
                System.out.println("No Commands enter");
                break;
        }
    }
}
