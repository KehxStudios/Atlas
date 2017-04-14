package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.Gdx;

/**
 * Created by ReidC on 2017-04-06.
 */

public class DebugTool {

    public static String title = "Debug";

    public static void log(String message) {
        Gdx.app.error(title, message);
    }

    public static void log(String message1, String message2) {
        Gdx.app.error(title +" - "+ message1, message2);
    }
}
