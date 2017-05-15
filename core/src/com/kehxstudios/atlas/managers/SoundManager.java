package com.kehxstudios.atlas.managers;


import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.MusicComponent;
import com.kehxstudios.atlas.components.SoundComponent;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.type.ComponentType;
import com.kehxstudios.atlas.type.MusicType;
import com.kehxstudios.atlas.type.SoundType;

import java.util.ArrayList;
import java.util.HashMap;

public class SoundManager extends Manager {

    // SUPPORTS WAV, MP3 and OGG AUDIO FORMATS

    // Holds instance of class, create new if not set
    private static SoundManager instance;
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    private ArrayList<SoundComponent> soundComponents;
    private ArrayList<MusicComponent> musicComponents;

    private HashMap<String, Sound> rootSounds;

    public SoundManager() {
        super();
        setup();
    }

    public Music getMusic(MusicType type) {
        return Gdx.audio.newMusic(Gdx.files.internal(type.getCompletePath()));
    }

    public Sound getSound(SoundType type) {
        return Gdx.audio.newSound(Gdx.files.internal(type.getCompletePath()));
    }

    @Override
    public void tick(float delta) {
        
    }
    
    @Override
    protected void setup() {
        soundComponents = new ArrayList<SoundComponent>();
        musicComponents = new ArrayList<MusicComponent>();
    }
    
    @Override
    protected void loadScreenSettings() {
        rootSounds = new HashMap<String, Sound>();


        DebugTool.log("SoundManager_loadScreenSettings: Complete");
    }
    
    @Override
    protected void removeScreenSettings() {
        DebugTool.log("SoundManager_loadScreenSettings: Complete");
    }
    
    public void play(Component component) {
        if (component.getType() == ComponentType.SOUND) {
            SoundComponent sound = (SoundComponent)component;
            if (!soundComponents.contains(sound)) {
                add(sound);
            }
            sound.getSound().play(sound.getVolume());
        } else if (component.getType() == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (!musicComponents.contains(music)) {
                add(music);
            }
            music.getMusic().setVolume(music.getVolume());
            music.getMusic().play();
        }
    }
    
    public void stop(Component component) {
        if (component.getType() == ComponentType.SOUND) {
            SoundComponent sound = (SoundComponent)component;
            if (!soundComponents.contains(sound)) {
                add(sound);
            }
            sound.getSound().stop();
        } else if (component.getType() == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (!musicComponents.contains(music)) {
                add(music);
            }
            music.getMusic().stop();
        }
    }
    
    public void add(Component component) {
        if (component.getType() == ComponentType.SOUND) {
            SoundComponent sound = (SoundComponent)component;
            if (!soundComponents.contains(sound)) {
                soundComponents.add(sound);
            }
        } else if (component.getType() == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (!musicComponents.contains(music)) {
                musicComponents.add(music);
            }
        }
    }
    
    public void remove(Component component) {
        if (component.getType() == ComponentType.SOUND) {
            SoundComponent sound = (SoundComponent)component;
            if (soundComponents.contains(sound)) {
                sound.getSound().stop();
                sound.getSound().dispose();
                soundComponents.remove(sound);
            }
        } else if (component.getType() == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (musicComponents.contains(music)) {
                music.getMusic().stop();
                music.getMusic().dispose();
                musicComponents.remove(music);
            }
        }
    }
}
