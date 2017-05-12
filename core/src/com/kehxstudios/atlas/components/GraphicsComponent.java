package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.type.TextureType;

/**
 * Created by ReidC on 2017-04-06.
 */

public class GraphicsComponent extends Component {

    private TextureType textureType;
    private Texture texture;
    private float width, height;
    private int layer;
    private float rotation

    public TextureType getTextureType() { return textureType; }
    public void setTextureType(TextureType textureType) {
        this.textureType = textureType;
        width = textureType.getWidth();
        height = textureType.getHeight();
        if (textureType != TextureType.VOID) {
            texture = GraphicsManager.getInstance().getTexture(textureType);
        }
    }
    
    public Texture getTexture() { return texture; }

    public float getWidth() { return width; }
    public void setWidth(float width) { this.width = width; }

    public float getHeight() {
        return height;
    }
    public void setHeight(float height) { this.height = height; }

    public int getLayer() { return layer; }
    public void setLayer(int layer) { this.layer = layer; }
    
    public float getRotation() { return rotation; }
    public void addRotation(float value) { setRotation(rotation + value); }
    public void setRotation(float rotation) { 
        while (rotation > 360f) {
            rotation -= 360f;
        }
        while (rotation < 0f) {
            rotation += 360f;   
        }
        this.rotation = rotation; 
    }
}
