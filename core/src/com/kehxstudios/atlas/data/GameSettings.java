package com.kehxstudios.atlas.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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


/**
 * Created by ReidC on 2017-05-30.
 */

public class GameSettings {

    private String preferenceName = "GameSettings";
    private Preferences preferences;
    public float musicVolume;
    public float soundVolume;
    public boolean loadingMusic;

    public GameSettings() {
        preferences = Gdx.app.getPreferences(preferenceName);
        musicVolume = preferences.getFloat("musicVolume", 0.5f);
        soundVolume = preferences.getFloat("soundVolume", 0.8f);
        loadingMusic = preferences.getBoolean("loadingMusic", true);
    }

    public void saveSettings() {
        preferences.putFloat("musicVolume", musicVolume);
        preferences.putFloat("soundVolume", soundVolume);
        preferences.putBoolean("loadingMusic", loadingMusic);
        preferences.flush();
    }
}
