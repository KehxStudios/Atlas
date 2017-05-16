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

package com.kehxstudios.atlas.components;

import com.badlogic.gdx.graphics.Texture;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.type.TextureType;

/**
 * Used to hold any graphics to be rendered
 */

public class GraphicsComponent extends Component {

    private TextureType textureType;
    private Texture texture;
    private float width, height;
    private int layer;
    private float rotation;

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
