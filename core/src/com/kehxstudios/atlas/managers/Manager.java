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

import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.screens.AScreen;

/**
 * Abstract class used by all Managers, excluding the GameManager.
 */

public abstract class Manager {

    protected GameManager gm;
    protected AScreen screen;

    public Manager() {
        gm = GameManager.getInstance();
        screen = null;
    }

    public abstract void tick(float delta);
    public abstract void add(Component component);
    public abstract void remove(Component component);
    
    protected abstract void init();
    protected abstract void loadSettings();
    protected abstract void removeSettings();
    
    public void setScreen(AScreen newScreen) { 
        if (screen != null) {
            removeSettings();
        }
        screen = newScreen;
        loadSettings();
    }
}
