package com.kehxstudios.atlas.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kehxstudios.atlas.data.SpriteType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-06.
 */

public class GraphicsComponent extends Component {

    private String spriteName;
    private Sprite sprite;
    private int layer;

    public void setTexture(SpriteType spriteType) {
        spriteName = spriteType.getId();
        loadTexture();
    }

    public GraphicsComponent(Entity entity, SpriteType spriteType, int layer){
        super(entity);
        this.spriteName = spriteType.getId();
        this.layer = layer;
        init();
    }

    public GraphicsComponent(Entity entity, ComponentData componentData) {
        super(entity, componentData);
        spriteName = componentData.getString("textureName","-");
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
        sprite = GraphicsManager.getInstance().getSprite(spriteName);
    }

    public ComponentData getComponentData() {
        ComponentData componentData = super.getComponentData();
        componentData.putString("textureName", spriteName);
        componentData.putInt("layer", layer);
        return componentData;
    }

    public Sprite getSprite() { return sprite; }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
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
