package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.components.SoundComponent;
import com.kehxstudios.atlas.managers.SoundManager;

/**
 * Created by ReidC on 2017-06-10.
 */

public class SoundAction extends Action {

    public SoundManager soundManager;
    public SoundComponent soundComponent;

    public void trigger() {
        soundManager.play(soundComponent);
    }
}
