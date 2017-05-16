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

package com.kehxstudios.atlas.components;


import com.badlogic.gdx.audio.Music;
import com.kehxstudios.atlas.managers.SoundManager;
import com.kehxstudios.atlas.type.MusicType;

/**
 * Used to hold the current music
 */

public class MusicComponent extends Component {

    private MusicType musicType;
    private Music music;
    private float volume;
    
    public MusicComponent() {
        super();   
    }
    
    public void setMusicType(MusicType musicType) {
        this.musicType = musicType;
        if (musicType == MusicType.VOID) {
            setEnabled(false);
        } else {
            music = SoundManager.getInstance().getMusic(musicType);
        }
    }
    public MusicType getMusicType() { return musicType; }
    
    public Music getMusic() { return music; }
    
    public void setVolume(float volume) { 
        this.volume = volume; 
        if (volume > 1f) {
            volume = 1f;   
        } else if (volume < 0f) {
            volume = 0f;   
        }
        music.setVolume(volume);
    }
    public void changeVolume(float value) {
        setVolume(volume + value);
    }
    public float getVolume() { return volume; }
}
