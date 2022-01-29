package com.github.sanforjr2021.util;

import java.util.ArrayList;

//Todo: Set it to use a text file for each type.
public class RandomMessageGenerator {
    private static final ArrayList<String> positiveMessages = new ArrayList<String>();
    private static final ArrayList<String> negativeMessages = new ArrayList<String>();

    public static String getPositiveMessage() {
        if (positiveMessages.size() == 0) {
            generatePositiveMessages();
        }
        return  positiveMessages.get((int) (Math.random() * (positiveMessages.size() - 1)));
    }

    public static String getNegativeMessage() {
        if (negativeMessages.size() == 0) {
            generateNegativeMessages();
        }
        return negativeMessages.get((int) (Math.random() * (negativeMessages.size() - 1)));
    }

    private static void generatePositiveMessages() {
        positiveMessages.add("Cheers Mate!");
        positiveMessages.add("Salute!");
        positiveMessages.add("You're our favorite local");
        positiveMessages.add("Wow, you are beautiful when you are drunk");
        positiveMessages.add("Hold my beer!");
        positiveMessages.add("I'm going to buy everyone a round of shots after this!");
        positiveMessages.add("Ha Ha Ha! You ara drunk and glorious");
        positiveMessages.add("I love you, bro!");
        positiveMessages.add("Chug! Chug! Chug!");
        positiveMessages.add("High Five Bro!");

    }

    private static void generateNegativeMessages() {
        negativeMessages.add("You're not looking too good, chap.");
        negativeMessages.add("Looks like you need another beer.");
        negativeMessages.add("I'm guessing you a beer half empty sort of person");
        negativeMessages.add("You're drunk... Go home!");
        negativeMessages.add("Was that one of those famous 'Hold My Beer' moments I've heard so much about?");
        negativeMessages.add("Now that's just embarrassing, lad.");
        negativeMessages.add("You may want to lay off the whiskey for a while");
        negativeMessages.add("That's a party foul!");
        negativeMessages.add("This hurts me more than it hurts you.");
        negativeMessages.add("You look like you tried to buttchug a keg");
        negativeMessages.add("At the rate you're going, you'll be drinking hand sanatizer by midnight");
        negativeMessages.add("Help, I'm trapped inside a discord bot!");
    }
}
