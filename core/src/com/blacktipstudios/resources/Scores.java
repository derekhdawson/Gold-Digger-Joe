package com.blacktipstudios.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Scores {

    private static Preferences preferences = Gdx.app.getPreferences(Scores.class.getName());

    public static void load() {

    }

    public static int getScore(int index) {
        return preferences.getInteger("scores_" + index);
    }

    public static void addScore(int score) {
        int index = -1;
        for(int i = 4; i >= 0; i--) {
            int temp = preferences.getInteger("scores_" + i);
            if(score > temp) {
                index = i;
            }
        }
        //there is a new value to be placed
        if(index != -1) {
            //shift values
            for(int i = 3; i >= index; i--) {
                int temp = preferences.getInteger("scores_" + i);
                preferences.putInteger("scores_" + (i + 1), temp);
            }
            preferences.putInteger("scores_" + index, score);
            preferences.flush();
        }
    }




}
