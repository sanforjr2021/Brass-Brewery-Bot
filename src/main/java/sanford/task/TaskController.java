package sanford.task;

import java.util.Timer;

public class TaskController {
    private static Timer textPointsTimer, voicePointsTimer,updateRolesTimer;
    public TaskController(){
        textPointsTimer = new Timer("Text Point");
        voicePointsTimer = new Timer("Voice Point");
        updateRolesTimer = new Timer("Update Role");
        textPointsTimer.scheduleAtFixedRate(new RewardTextPoints(),300000 , 300000);
        voicePointsTimer.scheduleAtFixedRate(new RewardVoicePoints(), 60000, 300000);
        updateRolesTimer.scheduleAtFixedRate(new UpdateRolesDaily(), 60000, 43200000);
    }

    public void stopAllTask(){
        textPointsTimer.cancel();
        voicePointsTimer.cancel();
        updateRolesTimer.cancel();
    }
}
