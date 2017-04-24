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
    private float width, height;
    private int layer;

    public void setTexture(TextureType textureType) {
        this.textureType = textureType;
        loadTexture();
    }

    public GraphicsComponent(Entity entity, TextureType textureType,
                             float width, float height, int layer){
        super(entity);
        this.textureType = textureType;
        this.width = width;
        this.height = height;
        this.layer = layer;
        init();
    }

    public GraphicsComponent(Entity entity, ComponentData componentData) {
        super(entity, componentData);
        textureType = TextureType.getTypeFromId(componentData.getString("textureId","-"));
        width = componentData.getFloat("width", 0);
        height = componentData.getFloat("height", 0);
        layer = componentData.getInt("layer",0);
        init();
    }

    public GraphicsComponent(Entity entity) {
        super(entity);
        type = ComponentType.GRAPHICS;
        GraphicsManager.getInstance().add(this);
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
        componentData.putFloat("width", width);
        componentData.putFloat("height", height);
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
        return height;
    }

    public void setHeight(float value) { height = value; }

    public float getWidth() {
        return width;
    }

    public void setWidth(float value) { width = value; }

    public int getLayer() { return layer; }

    public void setLayer(int value) { layer = value; }

    public TextureType getTextureType() { return textureType; }

    @Override
    public void dispose() {
        super.dispose();
        DebugTool.log("GraphicComponent disposal");
        GraphicsManager.getInstance().remove(this);
    }
}
