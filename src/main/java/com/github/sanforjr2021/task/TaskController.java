package com.github.sanforjr2021.task;

import java.util.Timer;

public class TaskController {
    private static Timer textPointsTimer, voicePointsTimer, updateBotStatus;

    public TaskController() {
        textPointsTimer = new Timer("Text Point");
        voicePointsTimer = new Timer("Voice Point");
        updateBotStatus = new Timer("updateBotStatus");
        textPointsTimer.scheduleAtFixedRate(new AddPointsFromMessage(), 5000, 300000);
        voicePointsTimer.scheduleAtFixedRate(new AddPointsFromVoice(), 2000, 300000);
        updateBotStatus.scheduleAtFixedRate(new UpdateBotStatus(), 10, 60000);
    }

    public void stopAllTask() {
        textPointsTimer.cancel();
        voicePointsTimer.cancel();
        updateBotStatus.cancel();
    }
}
