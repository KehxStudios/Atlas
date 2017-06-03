package com.kehxstudios.atlas.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.kehxstudios.atlas.managers.GameManager;
import com.kehxstudios.atlas.tools.GPSTracker;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new GameManager(new GPSTracker() {
                        @Override
                        public String getLocation() {
                                return "HTML - Unable to get location";
                        }
                });
        }
}