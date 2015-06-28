package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blacktipstudios.resources.Settings;


/**
 * Created by derekdawson on 2/2/15.
 */
public class StateButton {

    private Button button1;
    private Button button2;

    public StateButton(Button button1, Button button2) {
        this.button1 = button1;
        this.button2 = button2;
    }

    public void draw(SpriteBatch batch) {
        if(Settings.isSoundOn()) {
            button1.draw(batch);
        } else {
            button2.draw(batch);
        }
    }

    public void setPosition(float x, float y) {
        button1.setX(x); button1.setY(y);
        button2.setX(x); button2.setY(y);
    }

    public boolean isTouched(float x, float y) {
        float buttonX = button1.getX();
        float buttonY = button1.getY();
        float disX = (buttonX - x) * (buttonX - x);
        float disY = (buttonY - y) * (buttonY - y);
        float dis = (float) Math.sqrt(disX + disY);
        return dis < button1.getRadius();
    }
}
