package com.kehxstudios.atlas;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kehxstudios.atlas.managers.GameManager;
import com.kehxstudios.atlas.tools.GPSTracker;

import java.util.List;

public class AndroidLauncher extends AndroidApplication {

	private LocationManager locationManager;
	private List<String> providers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;
		config.useCompass = true;
		config.useGyroscope = true;

		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		providers = locationManager.getProviders(true);

		GPSTracker gpsTracker = new GPSTracker() {
			@Override
			public String getLocation() {
				if (checkCallingPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					return locationManager.getLastKnownLocation(providers.get(0)).toString();
				}
				return null;
			}
		};
		initialize(new GameManager(gpsTracker), config);
	}
}
