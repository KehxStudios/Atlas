package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.Gdx;

/**
 * Basic debugging tool to log messages to console on any platform
 */

public class DebugTool {

    // Start of line text
    public static String title = "Debug";

    // Print a single string to console
    public static void log(String message) {
        Gdx.app.error(title, message);
    }

    // Prints two strings to console
    public static void log(String message1, String message2) {
        Gdx.app.error(title +" - "+ message1, message2);
    }
}
