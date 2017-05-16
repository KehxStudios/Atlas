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

package com.kehxstudios.atlas.type;

import java.util.HashMap;

/**
 * Used to store all types of Sound's
 */

public enum SoundType {

    INTRO("Intro", "intro.mp3", "Intro", "sounds/intro/"),
    
    MAIN_MENU("MainMenu", "mainMenu.mp3", "Main Menu", "sounds/mainMenu/"),
    
    VOID("Void", "intro.mp3", "Intro", "sounds/intro/");

    private String id, fileName, filePath;
    private float length;

    private SoundType(String id, String fileName, String screen, String filePath) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getId() { return id; }

    public String getFileName() { return fileName; }
    
    public String getFilePath() { return filePath; }
    
    public String getCompletePath() { return filePath + fileName; }

    public static SoundType getTypeById(String id) { return soundTypes.get(id); }

    private static HashMap<String, SoundType> soundTypes;

    static {
        soundTypes = new HashMap<String, SoundType>();
        for (SoundType type : SoundType.values()) {
            soundTypes.put(type.id, type);
        }
    }
}
