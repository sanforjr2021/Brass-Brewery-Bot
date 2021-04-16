package sanford.task;

import java.util.Timer;

public class TaskController {
    private static Timer textPointsTimer, voicePointsTimer, updateBotStatus;
    public TaskController(){
        textPointsTimer = new Timer("Text Point");
        voicePointsTimer = new Timer("Voice Point");
        updateBotStatus = new Timer("updateBotStatus");
        textPointsTimer.scheduleAtFixedRate(new RewardTextPointsTask(),300000 , 300000);
        voicePointsTimer.scheduleAtFixedRate(new RewardVoicePointsTask(), 60000, 300000);
        updateBotStatus.scheduleAtFixedRate(new UpdateBotStatusTask(), 3000, 30000);
    }

    public void stopAllTask(){
        textPointsTimer.cancel();
        voicePointsTimer.cancel();
        updateBotStatus.cancel();
    }
}
