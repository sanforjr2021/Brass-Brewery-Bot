package com.github.sanforjr2021.bot;

import com.github.sanforjr2021.BrassBreweryBot;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.rest.RestClient;
import discord4j.rest.entity.RestChannel;

public class D4JBot {
    protected static RestChannel auditLog;
    protected static GatewayDiscordClient client;
    protected static RestClient restClient;

    //Referenced Tutorial Here: https://docs.discord4j.com/basic-bot-tutorial
    public D4JBot(String token, String auditLogID) {
        client = DiscordClientBuilder.create(token).build().login().block();
        restClient = client.getRestClient();
        auditLog = restClient.getChannelById(Snowflake.of(auditLogID));
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> this.onReady());

    }

    /**
     * When the bot is turned on completely, send a message to the audit log
     */
    public void onReady() {
        auditLog.createMessage("Brass Brewery Bot(V" + BrassBreweryBot.VERSION + ") is turning on.").block();
    }

    /**
     * When the bot disconnects, send a message to the audit log
     */
    public void onShutdown() {
        auditLog.createMessage("Brass Brewery Bot(V" + BrassBreweryBot.VERSION + ") is shutting down.").block();
        client.onDisconnect().block();
    }
}
