package com.kehxstudios.atlas.managers;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.MusicComponent;
import com.kehxstudios.atlas.components.SoundComponent;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.type.ComponentType;
import com.kehxstudios.atlas.type.MusicType;
import com.kehxstudios.atlas.type.SoundType;

import java.util.ArrayList;

public class SoundManager extends Manager {

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

    public SoundManager() {
        super();
        setup();
    }

    public Music getMusic(MusicType type) {
        return null;
    }

    public Sound getSound(SoundType type) {
        return null;
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
            // music.getClip().play();
        } else if (component.getType() == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (!musicComponents.contains(music)) {
                add(music);
            }
            // music.getClip().play();
        }
    }
    
    public void stop(Component component) {
        if (component.getType() == ComponentType.SOUND) {
            SoundComponent sound = (SoundComponent)component;
            if (!soundComponents.contains(sound)) {
                add(sound);
            }
            //if (sound.getClip().isPlaying()) {
            //    sound.getClip().stop();
            //}
        } else if (component.getType() == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (!musicComponents.contains(music)) {
                add(music);
            }
            //if (music.getClip().isPlaying()) {
            //    music.getClip().stop();
            //}
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
                soundComponents.remove(sound);
            }
        } else if (component.getType() == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (musicComponents.contains(music)) {
                musicComponents.remove(music);
            }
        }
    }
}
