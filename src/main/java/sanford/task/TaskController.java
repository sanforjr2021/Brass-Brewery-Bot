package sanford.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;

public class TaskController {
    private static Timer textPointsTimer, voicePointsTimer,updateRolesTimer;
    public TaskController(){
        textPointsTimer = new Timer("Text Point");
        voicePointsTimer = new Timer("Voice Point");
        updateRolesTimer = new Timer("Update Role");
        textPointsTimer.scheduleAtFixedRate(new RewardTextPoints(),290000 , 290000);
        voicePointsTimer.scheduleAtFixedRate(new RewardVoicePoints(), 60000, 290000);
        updateRolesTimer.scheduleAtFixedRate(new UpdateRolesDaily(), 60000, 43200000);
    }

    public void stopAllTask(){
        textPointsTimer.cancel();
        voicePointsTimer.cancel();
        updateRolesTimer.cancel();
    }
}
