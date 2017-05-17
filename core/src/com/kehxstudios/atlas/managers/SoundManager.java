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

/**
 * Used to control Music along with all Sounds
 */

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
    
    private HashMap<int, SoundComponent> soundComponents;
    private HashMap<int, MusicComponent> musicComponents;

    public SoundManager() {
        super();
        init();
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
    protected void init() {
        soundComponents = new HashMap<int, SoundComponent>();
        musicComponents = new HashMap<int, MusicComponent>();
    }
    
    @Override
    protected void loadSettings() {
        DebugTool.log("SoundManager_loadScreenSettings: Complete");
    }
    
    @Override
    protected void removeSettings() {
        DebugTool.log("SoundManager_loadScreenSettings: Complete");
    }
    
    public void play(Component component) {
        if (component.type == ComponentType.SOUND) {
            SoundComponent sound = (SoundComponent)component;
            if (!soundComponents.contains(sound)) {
                add(sound);
            }
            sound.sound.play(sound.volume);
        } else if (component.type == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (!musicComponents.contains(music)) {
                add(music);
            }
            music.music.volume(music.volume);
            music.music.play();
        }
    }
    
    public void stop(Component component) {
        if (component.type == ComponentType.SOUND) {
            SoundComponent sound = (SoundComponent)component;
            if (!soundComponents.contains(sound)) {
                add(sound);
            }
            sound.sound.stop();
        } else if (component.type == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (!musicComponents.contains(music)) {
                add(music);
            }
            music.music.stop();
        }
    }
    
    public void add(Component component) {
        if (component.type == ComponentType.SOUND) {
            SoundComponent sound = (SoundComponent)component;
            if (!soundComponents.contains(sound)) {
                soundComponents.add(sound);
            }
        } else if (component.type == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (!musicComponents.contains(music)) {
                musicComponents.add(music);
            }
        }
    }
    
    public void remove(Component component) {
        if (component.type == ComponentType.SOUND) {
            SoundComponent sound = (SoundComponent)component;
            if (soundComponents.contains(sound)) {
                sound.sound.stop();
                sound.sound.dispose();
                soundComponents.values().remove(sound);
            }
        } else if (component.type == ComponentType.MUSIC) {
            MusicComponent music = (MusicComponent)component;
            if (musicComponents.contains(music)) {
                music.music.stop();
                music.music.dispose();
                musicComponents.values().remove(music);
            }
        }
    }
}
