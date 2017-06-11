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

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.google.gwt.geolocation.client.Geolocation;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.BuildManager;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.type.TextureType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Shows information from device
 */

public class DeviceInformationScreen extends AScreen {

    // Display
    private FloatingTextComponent orientationFloatingText, resolutionFloatingText;
    // Compass
    private FloatingTextComponent azmuthFloatingText, pitchFloatingText, rollFloatingText;
    // Accelerometer
    private FloatingTextComponent accelerometerXFloatingText, accelerometerYFloatingText,
            accelerometerZFloatingText;
    // Gyroscope
    private FloatingTextComponent gyroscopeXFloatingText, gyroscopeYFloatingText,
            gyroscopeZFloatingText;
    // Networking
    private FloatingTextComponent networkIPFloatingText, externalIPFloatingText;

    private float sensorTick;
    private float networkTick;

    public DeviceInformationScreen() {
        super(ScreenType.DEVICE_INFORMATION);
        init();
    }

    protected void init() {
        super.init();

        Entity orientationEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y + 320);
        orientationFloatingText = buildManager.createFloatingTextComponent(orientationEntity, true, true,
                "Orientation: ", "", graphicsManager.COLOR_BLUE);
        Entity resolutionEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y + 280);
        resolutionFloatingText = buildManager.createFloatingTextComponent(resolutionEntity, true, true,
                "Resolution: ", "", graphicsManager.COLOR_BLUE);

        Entity azmuthEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y + 200);
        azmuthFloatingText = buildManager.createFloatingTextComponent(azmuthEntity, true, true,
                "Azmuth: ", "", graphicsManager.COLOR_BLUE);
        Entity pitchEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y + 160);
        pitchFloatingText = buildManager.createFloatingTextComponent(pitchEntity, true, true,
                "Pitch: ", "", graphicsManager.COLOR_BLUE);
        Entity rollEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y + 120);
        rollFloatingText = buildManager.createFloatingTextComponent(rollEntity, true, true,
                "Roll: ", "", graphicsManager.COLOR_BLUE);

        Entity accelerometerXEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y + 40);
        accelerometerXFloatingText = buildManager.createFloatingTextComponent(accelerometerXEntity, true, true,
                "Accelerometer X: ", "", graphicsManager.COLOR_BLUE);
        Entity accelerationYEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y);
        accelerometerYFloatingText = buildManager.createFloatingTextComponent(accelerationYEntity, true, true,
                "Accelerometer Y: ", "", graphicsManager.COLOR_BLUE);
        Entity accelerationZEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 40);
        accelerometerZFloatingText = buildManager.createFloatingTextComponent(accelerationZEntity, true, true,
                "Accelerometer Z: ", "", graphicsManager.COLOR_BLUE);

        Entity gyroscopeXEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 120);
        gyroscopeXFloatingText = buildManager.createFloatingTextComponent(gyroscopeXEntity, true, true,
                "Gyroscope X: ", "", graphicsManager.COLOR_BLUE);
        Entity gyroscopeYEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 160);
        gyroscopeYFloatingText  = buildManager.createFloatingTextComponent(gyroscopeYEntity, true, true,
                "Gyroscope Y: ", "", graphicsManager.COLOR_BLUE);
        Entity gyroscopeZEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 200);
        gyroscopeZFloatingText  = buildManager.createFloatingTextComponent(gyroscopeZEntity, true, true,
                "Gyroscope Z: ", "", graphicsManager.COLOR_BLUE);

        Entity networkIPEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 280);
        networkIPFloatingText = buildManager.createFloatingTextComponent(networkIPEntity, true, true,
                "Network IP: ", "", graphicsManager.COLOR_BLUE);
        Entity externalIPEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 320);
        externalIPFloatingText = buildManager.createFloatingTextComponent(externalIPEntity, true, true,
                "External IP: ", "", graphicsManager.COLOR_BLUE);

        Entity platformEntity = buildManager.createEntity(width/2, height - 128);
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            buildManager.createGraphicsComponent(platformEntity, 1, TextureType.PLATFORM_WINDOWS_COMPUTER);
        } else if (Gdx.app.getType() == Application.ApplicationType.Android) {
            buildManager.createGraphicsComponent(platformEntity, 1, TextureType.PLATFORM_ANDROID_PHONE);
        } else if (Gdx.app.getType() == Application.ApplicationType.iOS) {
            buildManager.createGraphicsComponent(platformEntity, 1, TextureType.PLATFORM_APPLE_PHONE);
        }

        networkTick = 29.3f;
        sensorTick = .3f;
        displayUpdate();
    }

    private void networkUpdate() {
        List<String> addresses = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
            for(String ip : addresses) { DebugTool.log("IP: ",ip); }
        } catch (SocketException e) {
            e.printStackTrace();
            networkIPFloatingText.text = "Error";
            externalIPFloatingText.text = "Error";
            networkIPFloatingText.layout.setText(networkIPFloatingText.font, networkIPFloatingText.label +
                    networkIPFloatingText.text, networkIPFloatingText.color, 0, Align.left, true);
            externalIPFloatingText.layout.setText(externalIPFloatingText.font, externalIPFloatingText.label +
                    externalIPFloatingText.text, externalIPFloatingText.color, 0, Align.left, true);
            return;
        }

        addresses.remove("127.0.0.1");

        if (addresses.size() > 0) {
            networkIPFloatingText.text = addresses.get(0);
        } else {
            networkIPFloatingText.text = "Not Connected";
            externalIPFloatingText.text = "Not Connected";
            networkIPFloatingText.layout.setText(networkIPFloatingText.font, networkIPFloatingText.label +
                    networkIPFloatingText.text, networkIPFloatingText.color, 0, Align.left, true);
            externalIPFloatingText.layout.setText(externalIPFloatingText.font, externalIPFloatingText.label +
                    externalIPFloatingText.text, externalIPFloatingText.color, 0, Align.left, true);
            return;
        }

        String externalIP = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new
                    URL("http://checkip.amazonaws.com").openStream()));
            externalIP = in.readLine(); //you get the IP as a String
            externalIPFloatingText.text = externalIP;
        } catch(IOException e) {
            e.printStackTrace();
            externalIPFloatingText.text = "Error";
        }
        networkIPFloatingText.layout.setText(networkIPFloatingText.font, networkIPFloatingText.label +
                networkIPFloatingText.text, networkIPFloatingText.color, 0, Align.left, true);
        externalIPFloatingText.layout.setText(externalIPFloatingText.font, externalIPFloatingText.label +
                externalIPFloatingText.text, externalIPFloatingText.color, 0, Align.left, true);
    }

    public void reset() {
        super.reset();
    }

    public void render(float delta) {
        super.render(delta);
        networkTick += delta;
        sensorTick += delta;
        if (sensorTick > .3f) {
            gyroscopeUpdate();
            compassUpdate();
            accelerometerUpdate();
            sensorTick = 0f;
        }
        if (networkTick > 30f) {
            networkUpdate();
            networkTick = 0;
        }
    }

    public void displayUpdate() {
        if (Gdx.input.getNativeOrientation() == Input.Orientation.Landscape) {
            orientationFloatingText.text = "Landscape";
        } else if (Gdx.input.getNativeOrientation() == Input.Orientation.Portrait){
            orientationFloatingText.text = "Portrait";
        } else {
            orientationFloatingText.text = "Not Available";
        }
        resolutionFloatingText.text = Gdx.graphics.getWidth() + " x " + Gdx.graphics.getHeight();

        orientationFloatingText.layout.setText(orientationFloatingText.font, orientationFloatingText.label +
                orientationFloatingText.text, orientationFloatingText.color, 0, Align.left, true);
        resolutionFloatingText.layout.setText(resolutionFloatingText.font, resolutionFloatingText.label +
                resolutionFloatingText.text, resolutionFloatingText.color, 0, Align.left, true);
    }

    public void gyroscopeUpdate() {
        if (Gdx.input.isPeripheralAvailable(Peripheral.Gyroscope)) {
            gyroscopeXFloatingText.text = String.format(java.util.Locale.US,"%.1f",
                    Gdx.input.getGyroscopeX());
            gyroscopeYFloatingText.text = String.format(java.util.Locale.US,"%.1f",
                    Gdx.input.getGyroscopeY());
            gyroscopeZFloatingText.text = String.format(java.util.Locale.US,"%.1f",
                    Gdx.input.getGyroscopeZ());
        } else {
            gyroscopeXFloatingText.text = "Not Available";
            gyroscopeYFloatingText.text = "Not Available";
            gyroscopeZFloatingText.text = "Not Available";
        }
        gyroscopeXFloatingText.layout.setText(gyroscopeXFloatingText.font, gyroscopeXFloatingText.label +
                gyroscopeXFloatingText.text, gyroscopeXFloatingText.color, 0, Align.left, true);
        gyroscopeYFloatingText.layout.setText(gyroscopeYFloatingText.font, gyroscopeYFloatingText.label +
                gyroscopeYFloatingText.text, gyroscopeYFloatingText.color, 0, Align.left, true);
        gyroscopeZFloatingText.layout.setText(gyroscopeZFloatingText.font, gyroscopeZFloatingText.label +
                gyroscopeZFloatingText.text, gyroscopeZFloatingText.color, 0, Align.left, true);
    }

    public void compassUpdate() {
        if (Gdx.input.isPeripheralAvailable(Peripheral.Compass)) {
            azmuthFloatingText.text = String.format(java.util.Locale.US,"%.1f", Gdx.input.getAzimuth());
            pitchFloatingText.text = String.format(java.util.Locale.US,"%.1f", Gdx.input.getPitch());
            rollFloatingText.text = String.format(java.util.Locale.US,"%.1f", Gdx.input.getRoll());
        } else {
            azmuthFloatingText.text = "Not Available";
            pitchFloatingText.text = "Not Available";
            rollFloatingText.text = "Not Available";
        }
        azmuthFloatingText.layout.setText(azmuthFloatingText.font, azmuthFloatingText.label +
                azmuthFloatingText.text, azmuthFloatingText.color, 0, Align.left, true);
        pitchFloatingText.layout.setText(pitchFloatingText.font, pitchFloatingText.label +
                pitchFloatingText.text, pitchFloatingText.color, 0, Align.left, true);
        rollFloatingText.layout.setText(rollFloatingText.font, rollFloatingText.label +
                rollFloatingText.text, rollFloatingText.color, 0, Align.left, true);
    }

    public void accelerometerUpdate() {
        if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
            accelerometerXFloatingText.text = String.format(java.util.Locale.US,"%.1f", Gdx.input.getAccelerometerX());
            accelerometerYFloatingText.text = String.format(java.util.Locale.US,"%.1f", Gdx.input.getAccelerometerY());
            accelerometerZFloatingText.text = String.format(java.util.Locale.US,"%.1f", Gdx.input.getAccelerometerZ());
        } else {
            accelerometerXFloatingText.text = "Not Available";
            accelerometerYFloatingText.text = "Not Available";
            accelerometerZFloatingText.text = "Not Available";
        }
        accelerometerXFloatingText.layout.setText(accelerometerXFloatingText.font, accelerometerXFloatingText.label +
                accelerometerXFloatingText.text, accelerometerXFloatingText.color, 0, Align.left, true);
        accelerometerYFloatingText.layout.setText(accelerometerYFloatingText.font, accelerometerYFloatingText.label +
                accelerometerYFloatingText.text, accelerometerYFloatingText.color, 0, Align.left, true);
        accelerometerZFloatingText.layout.setText(accelerometerZFloatingText.font, accelerometerZFloatingText.label +
                accelerometerZFloatingText.text, accelerometerZFloatingText.color, 0, Align.left, true);
    }

    public void dispose() {
        if (!disposed) {
            super.dispose();
        }
    }

    @Override
    public void show() { super.show(); }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        displayUpdate();
    }

    @Override
    public void pause() { super.pause(); }

    @Override
    public void resume() { super.resume(); }

    @Override
    public void hide() { super.hide(); }
}
