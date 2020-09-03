package sanford.data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sanford.data.structure.UserProfile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//Used https://stackabuse.com/reading-and-writing-json-in-java/
public class JsonManipulator {
    private static final String userProfilesDirectory = "/data/userProfiles";

    public JsonManipulator() {
        checkForFolders();
    }

    //Called on bootup to check to see if the folders exist
    private void checkForFolders() {
        File userProfilesFolder = new File(userProfilesDirectory);
        File dataFolder = new File("/data");
        if (!dataFolder.exists()) {
            System.out.println("Generating all folders");
            dataFolder.mkdir();
            userProfilesFolder.mkdir();
        } else {
            if (!userProfilesFolder.exists()) {
                userProfilesFolder.mkdir();
                System.out.println("Generating" + userProfilesDirectory);
            }
        }
    }

    public void writeUserProfile(UserProfile user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId());
        jsonObject.put("currency", user.getCurrency());
        jsonObject.put("autoRenew", user.getAutoRenew());
        jsonObject.put("autoUpgrade", user.getAutoUpgrade());
        try {
            Files.write(Paths.get(userProfilesDirectory +"/"+ user.getId() + ".json"), jsonObject.toJSONString().getBytes());
        } catch (IOException e) {
            System.out.println("Failed to write user " + user.getId());
        }
    }
    /*
    public UserProfile getUser(String id) throws IOException, ParseException {
        FileReader reader;
        reader = new FileReader(userProfilesDirectory + "/" + id + ".json");
        JSONParser jsonParser = new JSONParser();
        jsonParser.parse(reader);
    }
     */
}
