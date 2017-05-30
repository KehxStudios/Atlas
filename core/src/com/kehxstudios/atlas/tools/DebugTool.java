/*******************************************************************************
 * Copyright 2017 See AUTHORS file.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, 
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial 
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.Gdx;
import com.kehxstudios.atlas.managers.GameManager;

/**
 * Basic debugging tool to log messages to console on any platform
 */

public class DebugTool {

    // Start of line text
    private static String title = "Debug > ";

    // Print a single string to console
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
        if (GameManager.getInstance().showDebugLog) {
            Gdx.app.debug(title, message);
        }
    }
}
