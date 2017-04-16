package com.kehxstudios.atlas.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-06.
 */

public class GraphicsComponent extends Component {

    private String path;
    private Texture texture;
    private int layer;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        if (this.texture != null)
            this.texture.dispose();
        this.texture = texture;
    }

    public GraphicsComponent(Entity entity){
        super(entity);
        type = ComponentType.GRAPHICS;
        layer = 1;
        GraphicsManager.getInstance().add(this);
    }

    public GraphicsComponent(Entity entity, float x, float y) {
        super(entity);
        position = new Vector2(x,y);
        type = ComponentType.GRAPHICS;
        layer = 1;
        GraphicsManager.getInstance().add(this);
    }

    public ComponentData getComponentData() {
        ComponentData componentData = super.getComponentData();
        componentData.putString("path", path);
        componentData.putInt("layer", layer);
        return componentData;
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }

    public int getLayer() { return layer; }

    @Override
    public void dispose() {
        super.dispose();
        texture.dispose();
        DebugTool.log("GraphicComponent disposal");
        GraphicsManager.getInstance().remove(this);
    }
}
