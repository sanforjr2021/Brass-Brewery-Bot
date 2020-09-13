package sanford.commands;

import net.dv8tion.jda.api.entities.*;
import sanford.util.Util;

public class Verify {
    private String role;
    private User user;

    public Verify(Message msg) {
        if (msg.getContentRaw().toLowerCase().equals("!verify")) {
            user = msg.getAuthor();
            role = "722678454624976949";

            Util.deleteMessage(msg);
            //if has role, inform them they have it after deleting the message
            if (Util.hasRole(msg.getMember(), role)) {
                Util.directMessage(user, "You are already verified. We are glad you want to be verified again though.");
            } else {
            //if they don't have the role, add it to them
                Util.addRole(msg.getMember(), role);
                Util.directMessage(user, "You have received the role Verified. Welcome to the Brass Brewery");
            }
        } else {
            System.err.println("No Verification channel exist. Please create a verification channel.");
        }
    }
}
