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
    private GlyphLayout layout;
    private String label;
    private String text;

    public FloatingTextComponent(Entity entity, String label, String text, float scale, Color color) {
        super(entity);
        font = new BitmapFont();
        font.getData().setScale(scale);
        layout = new GlyphLayout(font, label+text);
        layout.setText(font, label+text, color, 0, Align.center, false);
    }

    public GlyphLayout getLayout() {
        return layout;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setScale(float scale) {
        font.getData().setScale(scale);
    }
}
