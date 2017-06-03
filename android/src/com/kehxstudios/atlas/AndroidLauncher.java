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

		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		GPSTracker gpsTracker = new GPSTracker() {
			@Override
			public String getLocation() {
				if (checkCallingPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
					List<String> providers = locationManager.getAllProviders();
					if (providers.size() > 0) {
						return locationManager.getLastKnownLocation(providers.get(0)).toString();
					} else {
						return "Location Error - providers list is at 0";
					}
				}
				return "Location Error - Cannot pass permissions... " + (Manifest.permission.ACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED + "");
			}
		};
		initialize(new GameManager(gpsTracker), config);
	}
}
