package com.kehxstudios.atlas.type;

import java.util.HashMap;

public enum MusicType {

    INTRO("Intro", "intro.mp3", "intro/music/"),
    
    MAIN_MENU("MainMenu", "mainMenu.mp3", "mainMenu/music/"),
    
    VOID("Void", "", "");

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
