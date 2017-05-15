package com.kehxstudios.atlas.type;

import java.util.HashMap;

public enum MusicType {

    INTRO("Intro", "music.mp3", "intro/"),
    MAIN_MENU("Main Menu", "music.mp3", "mainMenu/"),
    FLAPPY_BAT("Flappy Bat", "music.mp3", "flappyBat/"),
    LOADING("Loading", "music.mp3", "loading/"),
    GENE_ROCKETS("Gene Rockets", "music.mp3", "geneRockets/"),

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
