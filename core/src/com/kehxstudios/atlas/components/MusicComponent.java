package com.kehxstudios.atlas.components;


import com.badlogic.gdx.audio.Music;
import com.kehxstudios.atlas.managers.SoundManager;
import com.kehxstudios.atlas.type.MusicType;

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
