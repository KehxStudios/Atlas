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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.entities.Entity;

/**
 * Used to display text on the screen
 */

public class FloatingTextComponent extends Component{

    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private String label;
    private String text;
    private Color color;
    private boolean isUIElement;

    public BitmapFont getFont() { return font; }
    public void setFont(BitmapFont font) { this.font = font;}

    public GlyphLayout getLayout() {
        return glyphLayout;
    }
    public void setLayout(GlyphLayout glyphLayout) { this.glyphLayout = glyphLayout; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getText() { return text; }
    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public void setScale(float scale) {
        font.getData().setScale(scale);
    }

    public boolean getIsUIElement() { return isUIElement; }

    public void setUIElement(boolean value) { isUIElement = value; }
}
