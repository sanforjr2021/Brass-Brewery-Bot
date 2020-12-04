package sanford.commands;

import net.dv8tion.jda.api.entities.*;
import sanford.data.MemberDataContainer;
import sanford.data.SQLServerHandler;
import sanford.util.Util;

import java.sql.SQLException;

public class Verify extends Command{
    public Verify(Message msg){
        super(msg);

    }

    @Override
    public void executeCommand() {
        String role  = "722678454624976949";
        if (getArguments(0).equals("!verify")) {
            //if has role, inform them they have it after deleting the message
            if (Util.hasRole(getMember(), role)) {
                Util.directMessage(getUser(), "You are already verified. We are glad you want to be verified again though.");

            } else {
                //if they don't have the role, add it to them and register in the DB
                Util.addRole(getMember(), role);
                Util.directMessage(getUser(), "You have received the role Verified. Welcome to the Brass Brewery");
                try {
                    SQLServerHandler.addMember(new MemberDataContainer(getUser().getId()));
                } catch (SQLException throwables) {
                    logError("Failed to add the user " + getUser().getAsTag() +" to the database after verifying");
                    throwables.printStackTrace();
                }
            }
        } else {
            System.err.println("No Verification channel exist. Please create a verification channel.");
        }
    }
}
