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

    private String textureName;
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

    public GraphicsComponent(Entity entity, String textureName, int layer){
        super(entity);
        this.textureName = textureName;
        this.layer = layer;
        init();
    }

    public GraphicsComponent(Entity entity, ComponentData componentData) {
        super(entity, componentData);
        textureName = componentData.getString("textureName","-");
        layer = componentData.getInt("layer",0);
        init();
    }

    protected void init() {
        type = ComponentType.GRAPHICS;
        super.init();
        loadTexture();
        GraphicsManager.getInstance().add(this);
    }

    private void loadTexture() {
        texture = GraphicsManager.getInstance().getTexture(textureName);
    }

    public ComponentData getComponentData() {
        ComponentData componentData = super.getComponentData();
        componentData.putString("textureName", textureName);
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

    public void setLayer(int value) { layer = value; }

    @Override
    public void dispose() {
        super.dispose();
        texture.dispose();
        DebugTool.log("GraphicComponent disposal");
        GraphicsManager.getInstance().remove(this);
    }
}
