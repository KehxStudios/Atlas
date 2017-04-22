package com.kehxstudios.atlas.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kehxstudios.atlas.data.SpriteType;
import com.kehxstudios.atlas.data.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-06.
 */

public class GraphicsComponent extends Component {

    private TextureType textureType;
    private Texture texture;
    private int width, height;
    private int layer;

    public void setTexture(TextureType textureType) {
        this.textureType = textureType;
        loadTexture();
    }

    public GraphicsComponent(Entity entity, TextureType textureType, int layer){
        super(entity);
        this.textureType = textureType;
        this.layer = layer;
        init();
    }

    public GraphicsComponent(Entity entity, ComponentData componentData) {
        super(entity, componentData);
        textureType = TextureType.getTypeFromId(componentData.getString("textureId","-"));
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
        texture = GraphicsManager.getInstance().getTexture(textureType);
    }

    public ComponentData getComponentData() {
        ComponentData componentData = super.getComponentData();
        componentData.putString("textureId", textureType.getId());
        componentData.putInt("layer", layer);
        return componentData;
    }

    public boolean hasTexture() {
        return texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getHeight() {
        return texture.getHeight();
    }

    public float getWidth() {
        return texture.getWidth();
    }

    public int getLayer() { return layer; }

    public void setLayer(int value) { layer = value; }

    @Override
    public void dispose() {
        super.dispose();
        DebugTool.log("GraphicComponent disposal");
        GraphicsManager.getInstance().remove(this);
    }
}
