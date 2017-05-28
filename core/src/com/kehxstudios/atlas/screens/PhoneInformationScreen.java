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

package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.BuildManager;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Shows information from phone sensors
 */

public class PhoneInformationScreen extends AScreen {

    private FloatingTextComponent rotationFloatingText, orientationFloatingText,
        resolutionFloatingText, yAxisAccelerationFloatingText, azmuthFloatingText,
        pitchFloatingText, rollFloatingText;

    public PhoneInformationScreen() {
        super(ScreenType.PHONE_INFORMATION);
        init();
    }

    protected void init() {
        super.init();
        Entity rotationEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 120);
        rotationFloatingText = buildManager.createFloatingTextComponent(rotationEntity, 2,
                "Rotation: ", "", Color.BLACK);

        Entity orientationEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 80);
        orientationFloatingText = buildManager.createFloatingTextComponent(orientationEntity, 2,
                "Orientation: ", "", Color.BLUE);

        Entity resolutionEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 40);
        resolutionFloatingText = buildManager.createFloatingTextComponent(resolutionEntity, 2,
                "Resolution: ", "", Color.BLACK);

        Entity accelerationEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y);
        yAxisAccelerationFloatingText = buildManager.createFloatingTextComponent(accelerationEntity, 2,
                "Y-Axis Acceleration: ", "", Color.BLUE);

        Entity azmuthEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y + 40);
        azmuthFloatingText = buildManager.createFloatingTextComponent(azmuthEntity, 2,
                "Azmuth: ", "", Color.BLACK);

        Entity pitchEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y + 80);
        pitchFloatingText = buildManager.createFloatingTextComponent(pitchEntity, 2,
                "Pitch: ", "", Color.BLUE);

        Entity rollEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y + 120);
        rollFloatingText = buildManager.createFloatingTextComponent(rollEntity, 2,
                "Roll: ", "", Color.BLACK);
    }

    public void reset() {
        super.reset();
    }

    public void render(float delta) {
        super.render(delta);
        if (screenTime > 0.5f) {
            rotationFloatingText.text = Gdx.input.getRotation() + "";
            if (Gdx.input.getNativeOrientation() == Input.Orientation.Landscape) {
                orientationFloatingText.text = "Landscape";
            } else {
                orientationFloatingText.text = "Portrait";
            }
            resolutionFloatingText.text = Gdx.graphics.getWidth() + " x " + Gdx.graphics.getHeight();
            yAxisAccelerationFloatingText.text = String.format(java.util.Locale.US,"%.2f", Gdx.input.getAccelerometerY());
            if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass)) {
                azmuthFloatingText.text = String.format(java.util.Locale.US,"%.2f", Gdx.input.getAzimuth());
                pitchFloatingText.text = String.format(java.util.Locale.US,"%.2f", Gdx.input.getPitch());
                rollFloatingText.text = String.format(java.util.Locale.US,"%.2f", Gdx.input.getRoll());
            }
            updateFloatingText();
            screenTime = 0f;
        }
    }

    private void updateFloatingText() {
        rotationFloatingText.layout.setText(rotationFloatingText.font, rotationFloatingText.label +
                rotationFloatingText.text, rotationFloatingText.color, 0, Align.left, true);
        orientationFloatingText.layout.setText(orientationFloatingText.font, orientationFloatingText.label +
                orientationFloatingText.text, orientationFloatingText.color, 0, Align.left, true);
        resolutionFloatingText.layout.setText(resolutionFloatingText.font, resolutionFloatingText.label +
                resolutionFloatingText.text, resolutionFloatingText.color, 0, Align.left, true);
        yAxisAccelerationFloatingText.layout.setText(yAxisAccelerationFloatingText.font, yAxisAccelerationFloatingText.label +
                yAxisAccelerationFloatingText.text, yAxisAccelerationFloatingText.color, 0, Align.left, true);
        azmuthFloatingText.layout.setText(azmuthFloatingText.font, azmuthFloatingText.label +
                azmuthFloatingText.text, azmuthFloatingText.color, 0, Align.left, true);
        pitchFloatingText.layout.setText(pitchFloatingText.font, pitchFloatingText.label +
                pitchFloatingText.text, pitchFloatingText.color, 0, Align.left, true);
        rollFloatingText.layout.setText(rollFloatingText.font, rollFloatingText.label +
                rollFloatingText.text, rollFloatingText.color, 0, Align.left, true);
    }

    public void dispose() { super.dispose(); }

    @Override
    public void show() { super.show(); }

    @Override
    public void resize(int width, int height) { super.resize(width, height); }

    @Override
    public void pause() { super.pause(); }

    @Override
    public void resume() { super.resume(); }

    @Override
    public void hide() { super.hide(); }
}
