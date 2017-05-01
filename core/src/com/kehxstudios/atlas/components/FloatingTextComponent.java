package com.kehxstudios.atlas.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.entities.Entity;

/**
 * Created by ReidC on 2017-04-14.
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
