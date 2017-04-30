package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.type.TextureType;

/**
 * Created by ReidC on 2017-04-08.
 */

public class AnimationComponent extends Component {

    private GraphicsComponent graphicsComponent;
    private TextureType[] graphics;
    private int graphicIndex;
    private float timePerGraphic;
    private float currentTime;
    private boolean looped, finished;

    public void setLooped(boolean value) { looped = value; }

    public void setTimePerGraphic(float value) { timePerGraphic = value; }

    public void scaleTimePerGraphic(float value) { timePerGraphic *= value; }

    public boolean isFinished() { return finished; }
}
