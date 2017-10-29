package ru.pavel.baira;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by obairka on 29.10.17.
 */

public class TouchpadBuilder {
    private float x;
    private float y;
    private float width;
    private float height;

    private float deadZoneRadius;
    private Touchpad.TouchpadStyle style;

    public TouchpadBuilder()
    {
        this.deadZoneRadius = 50;

        Skin touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));
        //Create TouchPad Style
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        Drawable touchBackground = touchpadSkin.getDrawable("touchBackground");
        Drawable touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style

        this.style = touchpadStyle;
    }

    public TouchpadBuilder withPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public TouchpadBuilder withSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Touchpad build()
    {
        Touchpad touchpad = new Touchpad(this.deadZoneRadius, this.style);
        touchpad.setBounds(this.x, this.y, this.width, this.height);

        return touchpad;
    }
}
