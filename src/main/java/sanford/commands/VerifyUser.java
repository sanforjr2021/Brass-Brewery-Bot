package sanford.commands;

import net.dv8tion.jda.api.entities.*;
import sanford.util.Util;

public class VerifyUser
{
    private String mention, role;
    private User user;

    public VerifyUser(Message msg)
    {
        if (msg.getContentRaw().toLowerCase().equals("!verify"))
        {
            user = msg.getAuthor();
            mention = msg.getAuthor().getAsMention();
            role = "verified";

            Util.deleteMessage(msg);
            System.out.println("Testing");
            if (Util.hasRole(user, role))
            {
                Util.directMessage(user, "You are already verified. We are glad you want to be verified again though.");
            }
            else
            {
            Util.addRole(msg.getAuthor(), role);
            Util.directMessage(user, "You have received the role Verified. Welcome to the Brass Brewery");
            }
        }
        else
        {
            System.err.println("No Verification channel exist. Please create a verification channel.");
        }
    }
}
