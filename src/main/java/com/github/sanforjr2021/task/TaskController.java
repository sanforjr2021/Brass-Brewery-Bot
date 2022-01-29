package com.github.sanforjr2021.task;

import java.util.Timer;

public class TaskController {
    private static Timer textPointsTimer, voicePointsTimer, updateBotStatusTimer, removeExpiredBuyableRolesTimer;

    public TaskController() {
        textPointsTimer = new Timer("Text Point");
        voicePointsTimer = new Timer("Voice Point");
        updateBotStatusTimer = new Timer("updateBotStatus");
        removeExpiredBuyableRolesTimer = new Timer("Remove expired BuyableRoles");
        textPointsTimer.scheduleAtFixedRate(new AddPointsFromMessage(), 5000, 300000);
        voicePointsTimer.scheduleAtFixedRate(new AddPointsFromVoice(), 2000, 300000);
        updateBotStatusTimer.scheduleAtFixedRate(new UpdateBotStatus(), 10, 60000);
        removeExpiredBuyableRolesTimer.scheduleAtFixedRate(new RemoveExpiredBuyableRoles(), 10000, 3600000);

    }

    public void stopAllTask() {
        textPointsTimer.cancel();
        voicePointsTimer.cancel();
        updateBotStatusTimer.cancel();
        removeExpiredBuyableRolesTimer.cancel();
    }
}
