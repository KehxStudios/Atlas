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
 * Used to store all types of Music's
 */

public enum MusicType {

    INTRO("Intro", "intro.mp3", "music/"),
    MAIN_MENU("Main Menu", "mainMenu.mp3", "music/"),
    FLAPPY_BAT("Flappy Bat", "flappyBird.mp3", "music/"),
    LOADING("Loading", "music.mp3", "loading/"),
    GENE_ROCKETS("Gene Rockets", "geneRockets.mp3", "music/"),
    PHONE_INFORMATION("Phone Information", "intro.mp3", "music/"),

    VOID("Void", "music.mp3", "intro/");

    private String id, fileName, filePath;

    private MusicType(String id, String fileName, String filePath) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getId() { return id; }

    public String getFileName() { return fileName; }
    
    public String getFilePath() { return filePath; }
    
    public String getCompletePath() { return filePath + fileName; }
    
    public static MusicType getTypeById(String id) { return musicTypes.get(id); }

    private static HashMap<String, MusicType> musicTypes;

    static {
        musicTypes = new HashMap<String, MusicType>();
        for (MusicType type : MusicType.values()) {
            musicTypes.put(type.id, type);
        }
    }
}
