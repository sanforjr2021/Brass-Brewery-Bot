package sanford.data.structure;

import net.dv8tion.jda.api.entities.Role;
import sanford.BrassBreweryBot;

public class Game {
    private String roleID;
    private String game;
    private Role role;
    public Game(String roleID, String game){
        this.roleID = roleID;
        role = BrassBreweryBot.getGuild().getRoleById(roleID);
        this.game = game;
    }

    public String getRoleId() {
        return roleID;
    }

    public String getGame() {
        return game;
    }

    public Role getRole() {
        return role;
    }
    public String getMentionString(){
        return role.getAsMention();
    }
}
