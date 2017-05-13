package com.kehxstudios.atlas.components;


import com.badlogic.gdx.audio.Sound;
import com.kehxstudios.atlas.managers.SoundManager;
import com.kehxstudios.atlas.type.SoundType;

public class SoundComponent extends Component {

    private SoundType soundType;
    private Sound sound;
    private float volume;
    
    public SoundComponent() {
        super();   
    }
    
    public void setSoundType(SoundType soundType) {
        this.soundType = soundType;
        if (soundType == SoundType.VOID) {
            setEnabled(false);
        } else {
            sound = SoundManager.getInstance().getSound(soundType);
        }
    }
    public SoundType getSoundType() { return soundType; }
    
    public Sound getSound() { return sound; }
    
    public void setVolume(float volume) { this.volume = volume; }
    public void changeVolume(float value) { volume += value; }
    public float getVolume() { return volume; }
}
