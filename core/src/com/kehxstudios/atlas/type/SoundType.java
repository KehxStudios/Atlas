package com.kehxstudios.atlas.type;

import java.util.HashMap;

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
