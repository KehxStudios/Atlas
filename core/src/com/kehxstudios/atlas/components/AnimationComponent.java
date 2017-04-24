package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.data.SpriteType;
import com.kehxstudios.atlas.data.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GraphicsManager;

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

    public AnimationComponent(Entity entity) {
        super(entity);
    }

    public AnimationComponent(Entity entity, GraphicsComponent graphicsComponent, TextureType[] graphics, float timePerGraphic, boolean looped) {
        super(entity);
        this.graphicsComponent = graphicsComponent;
        this.graphics = graphics;
        this.timePerGraphic = timePerGraphic;
        this.looped = looped;
        init();
    }

    @Override
    protected void init() {
        type = ComponentType.ANIMATION;
        super.init();
        currentTime = 0f;
        graphicIndex = 0;
        finished = false;
        GraphicsManager.getInstance().add(this);
    }

    public void update(float delta) {
        if (finished) {
            return;
        }
        currentTime += delta;
        if (currentTime >= timePerGraphic) {
            if (graphicIndex + 1 < graphics.length) {
                graphicIndex++;
            } else if (!looped) {
                finished = true;
                return;
            } else {
                graphicIndex = 0;
            }
            currentTime = 0f;
            graphicsComponent.setTexture(graphics[graphicIndex]);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        GraphicsManager.getInstance().remove(this);
    }

    public void resetAnimation() {
        currentTime = 0f;
        graphicIndex = 0;
        graphicsComponent.setTexture(graphics[graphicIndex]);
    }

    public void setLooped(boolean value) { looped = value; }

    public void setTimePerGraphic(float value) { timePerGraphic = value; }

    public void scaleTimePerGraphic(float value) { timePerGraphic *= value; }

    public boolean isFinished() { return finished; }
}
