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
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.type.ScreenType;

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
 * Shows information from phone sensors
 */

public class DeviceInformationScreen extends AScreen {

    private FloatingTextComponent rotationFloatingText, orientationFloatingText,
        resolutionFloatingText, yAxisAccelerationFloatingText;

    // Angles
    private FloatingTextComponent azmuthFloatingText, pitchFloatingText, rollFloatingText;
    // Accelerometer
    private FloatingTextComponent acceleromterXFloatingText, acceleromterYFloatingText,
            accelerometerZFloatingText;

    // Gyroscope
    private FloatingTextComponent gyroscopeXFloatingText, gyroscopeYFloatingText,
            gyroscopeZFloatingText;
    // Networking
    private FloatingTextComponent networkIPFloatingText, externalIPFloatingText;

    public DeviceInformationScreen() {
        super(ScreenType.DEVICE_INFORMATION);
        init();
    }

    protected void init() {
        super.init();

        buildManager.createClickableComponent(screenEntity, width, height, true, false,
                buildManager.createLaunchScreenAction(ScreenType.INTRO));

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

        Entity networkIPEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 160);
        networkIPFloatingText = buildManager.createFloatingTextComponent(networkIPEntity, 2,
                "Network IP: ", "", Color.BLUE);

        Entity externalIPEntity = buildManager.createEntity(screenEntity.position.x, screenEntity.position.y - 200);
        externalIPFloatingText = buildManager.createFloatingTextComponent(externalIPEntity, 2,
                "External IP: ", "", Color.BLACK);
        network();
    }

    private void network() {
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
        } catch (SocketException e) {
            e.printStackTrace();
            DebugTool.log("Network Issue");
        }

        // Print the contents of our array to a string.  Yeah, should have used StringBuilder
        String ipAddress = new String("");
        for(String str:addresses)
        {
            ipAddress = ipAddress + str + "\n";
        }
        String externalIP = "";
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            externalIP = in.readLine(); //you get the IP as a String
        } catch(IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 1) {
            networkIPFloatingText.text = addresses.get(1);
            externalIPFloatingText.text = externalIP;
            DebugTool.log("IPs",ipAddress);
            networkIPFloatingText.layout.setText(networkIPFloatingText.font, networkIPFloatingText.label +
                    networkIPFloatingText.text, networkIPFloatingText.color, 0, Align.left, true);
            externalIPFloatingText.layout.setText(externalIPFloatingText.font, externalIPFloatingText.label +
                    externalIPFloatingText.text, externalIPFloatingText.color, 0, Align.left, true);
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                DebugTool.log(gm.getGpsTracker().getLocation());
            }
        } else {
            networkIPFloatingText.text = "Not Connected";
            externalIPFloatingText.text = "Not Connected";
        }
    }

    public void reset() {
        super.reset();
    }

    public void render(float delta) {
        super.render(delta);
        if (screenTime > 0.5f) {
            displayUpdate();
            gyroscopeUpdate();
            compassUdate();
            anglesUpdate();
            updateFloatingText();
            screenTime = 0f;
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
            gyroscopeXFloatingText.text = String.format(java.util.Locale.US,"%.3f",
                    Gdx.input.getGyroscopeX());
            gyroscopeYFloatingText.text = String.format(java.util.Locale.US,"%.3f",
                    Gdx.input.getGyroscopeY());
            gyroscopeZFloatingText.text = String.format(java.util.Locale.US,"%.3f",
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

    public void compassUdate() {
        if (Gdx.input.isPeripheralAvailable(Peripheral.Compass)) {
            azmuthFloatingText.text = String.format(java.util.Locale.US,"%.2f", Gdx.input.getAzimuth());
            pitchFloatingText.text = String.format(java.util.Locale.US,"%.2f", Gdx.input.getPitch());
            rollFloatingText.text = String.format(java.util.Locale.US,"%.2f", Gdx.input.getRoll());
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

    public void acceleromterUpdate() {
        if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
            azmuthFloatingText.text = String.format(java.util.Locale.US,"%.2f", Gdx.input.getGyroscopeZ());
            pitchFloatingText.text = String.format(java.util.Locale.US,"%.2f", Gdx.input.getGyroscopeY());
            rollFloatingText.text = String.format(java.util.Locale.US,"%.2f", Gdx.input.getGyroscopeX());
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
            yAxisAccelerationFloatingText.text = String.format(java.util.Locale.US, "%.2f", Gdx.input.getAccelerometerY());
        } else {
            yAxisAccelerationFloatingText.text = "Not Available";
        }
        yAxisAccelerationFloatingText.layout.setText(yAxisAccelerationFloatingText.font, yAxisAccelerationFloatingText.label +
                yAxisAccelerationFloatingText.text, yAxisAccelerationFloatingText.color, 0, Align.left, true);
    }

    private void updateFloatingText() {
        rotationFloatingText.layout.setText(rotationFloatingText.font, rotationFloatingText.label +
                rotationFloatingText.text, rotationFloatingText.color, 0, Align.left, true);
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
