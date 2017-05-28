package com.kehxstudios.atlas;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kehxstudios.atlas.managers.GameManager;
import com.kehxstudios.atlas.tools.GPSTracker;

public class AndroidLauncher extends AndroidApplication {

	private LocationManager locationManager;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = true;
        config.useCompass = true;

		GPSTracker gpsTracker = new GPSTracker() {
			@Override
			public String getLocation() {
				return null;
			}
		};
		initialize(new GameManager(gpsTracker), config);
	}
}
