package com.kehxstudios.atlas.type;

import java.util.HashMap;

public enum SoundType {

    INTRO("Intro", "intro.mp3", "sounds/intro/", 3.0f),
    
    MAIN_MENU("MainMenu", "mainMenu.mp3", "sounds/mainMenu/", 5.0f),
    
    VOID("Void", "", "", 0.0f);

    private String id, fileName, filePath;
    private float length;

    private SoundType(String id, String fileName, String filePath, float length) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.length = length;
    }

    public String getId() { return id; }

    public String getFileName() { return fileName; }
    
    public String getFilePath() { return filePath; }
    
    public String getCompletePath() { return filePath + fileName; }
    
    public float getLength() { return length; }

    public SoundType getTypeById(String id) { return soundTypes.get(id); }

    private static HashMap<String, SoundType> soundTypes;

    static {
        soundTypes = new HashMap<String, SoundType>();
        for (SoundType type : SoundType.values()) {
            soundTypes.put(type.id, type);
        }
    }
}
