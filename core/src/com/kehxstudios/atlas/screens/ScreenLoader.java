package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.kehxstudios.atlas.main.GameManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by ReidC on 2017-04-07.
 */

public class ScreenLoader {

    private static Json json = new Json();

    public static AScreen loadScreen(ScreenType type) {
        Gdx.files.local("screens/").file().mkdirs();
        FileHandle file = Gdx.files.internal("screens/" + type.getId() + ".screen");
        if (file.exists()) {
            ScreenSnapShot snapShot = json.fromJson(ScreenSnapShot.class, file.readString());
            AScreen screen = ScreenType.createScreenUsingSnapShot(snapShot, GameManager.getInstance());
            return screen;
        } else {
            // Preferences preferences = Gdx.app.getPreferences(type.getId());
            // ScreenSnapShot snapShot = json.fromJson(ScreenSnapShot.class, preferences.getString("json"));
            return null;
        }


    }

    public static void createJsonFile(String id) {
        //AScreen screen = ScreenType.createScreenUsingSnapShot(new ScreenSnapShot(id), GameManager.getInstance());
        //screen.backgroundPaths = new String[] { "intro/devLogo.png", "intro/gameLogo.png", "mainMenu/background.png"};
        //screen.backgroundTimes = new float[] {5.0f, 5.0f, 6.0f};
        //aveScreen(id, screen);
        //screen.dispose();
    }

    public static void saveScreen(AScreen screen) {
        ScreenSnapShot snapShot = screen.getSaveSnapShot();
        Preferences preferences = Gdx.app.getPreferences(screen.getType().getId());
        preferences.putString("json", json.prettyPrint(snapShot));
        preferences.flush();
    }
}
