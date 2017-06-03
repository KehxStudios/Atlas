package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.components.FloatingTextComponent;

/**
 * Created by ReidC on 2017-06-03.
 */

public class ChangeFloatingTextAction extends Action {

    public FloatingTextComponent floatingTextComponent;
    public String[] changeTextArray;
    public int changeTextIndex;
    public boolean changeLabelOverride;
    public boolean loopTextArray;

    @Override
    public void trigger() {
        if (++changeTextIndex >= changeTextArray.length) {
            if (loopTextArray)
                changeTextIndex = 0;
            else
                changeTextIndex = changeTextArray.length - 1;
        }

        if (changeLabelOverride) {
            floatingTextComponent.label = changeTextArray[changeTextIndex];
        } else {
            floatingTextComponent.text = changeTextArray[changeTextIndex];
        }

        floatingTextComponent.layout.setText(floatingTextComponent.font, floatingTextComponent.label
                        + floatingTextComponent.text, floatingTextComponent.color, 0, Align.left, true);
    }
}
