package com.blacktipstudios.resources;

/**
 * Created by derekdawson on 2/2/15.
 */
public class Settings {

    private static boolean soundOn = true;
    private static float position;

    public static boolean isSoundOn() {
        return soundOn;
    }

    public static void setSoundOn(boolean soundOn) {
        Settings.soundOn = soundOn;
    }


    public static float getPosition() {
        return position;
    }

    public static void setPosition(float position) {
        Settings.position = position;
    }
}
