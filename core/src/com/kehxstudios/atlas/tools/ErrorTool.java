package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.Gdx;

/**
 * Basic error reporting tool to log messages to console on any platform
 */

public class ErrorTool {

    // Start of line text
    private static String title = "Error";

    // Prints a single string to console
    public static void log(String message) {
        print(message);
    }

    // Prints two strings to console
    public static void log(String message1, String message2) {
        print(message1 + ", " + message2);
    }
    
    // Prints an array of strings to console
    public static void log(String[] messages) {
        String finalMessage = "";
        for (String message : messages) {
            finalMessage += ", " + message;
        }
        print(finalMessage);
    }
    
    // Prints the title with message
    private static void print(String message) {
        Gdx.app.error(title, message);
    }
}
