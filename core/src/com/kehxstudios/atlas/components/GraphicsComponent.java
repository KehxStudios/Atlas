package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.data.TextureType;

/**
 * Created by ReidC on 2017-04-06.
 */

public class GraphicsComponent extends Component {

    private TextureType textureType;
    private float width, height;
    private int layer;

    public TextureType getTextureType() { return textureType; }
    public void setTextureType(TextureType textureType) {
        this.textureType = textureType;
        width = textureType.getWidth();
        height = textureType.getHeight();
    }

    public float getWidth() { return width; }
    public void setWidth(float width) { this.width = width; }

    public float getHeight() {
        return height;
    }
    public void setHeight(float height) { this.height = height; }

    public int getLayer() { return layer; }
    public void setLayer(int layer) { this.layer = layer; }


}
