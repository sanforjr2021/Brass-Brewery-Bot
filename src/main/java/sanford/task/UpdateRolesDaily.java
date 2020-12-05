package sanford.task;

import java.util.TimerTask;

public class UpdateRolesDaily extends TimerTask {

    @Override
    public void run() {
        System.out.println("I updated!");
    }
}
