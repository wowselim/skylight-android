package se.gustavkarlsson.aurora_notifier.android.models;

import org.parceler.Parcel;

import se.gustavkarlsson.aurora_notifier.android.models.data.GeomagneticLocation;
import se.gustavkarlsson.aurora_notifier.android.models.data.SolarActivity;
import se.gustavkarlsson.aurora_notifier.android.models.data.SunPosition;
import se.gustavkarlsson.aurora_notifier.android.models.data.Weather;

@Parcel
public class AuroraData {
	SolarActivity solarActivity;
	GeomagneticLocation geomagneticLocation;
	SunPosition sunPosition;
	Weather weather;

	AuroraData() {
	}

	public AuroraData(
			SolarActivity solarActivity,
			GeomagneticLocation geomagneticLocation,
			SunPosition sunPosition,
			Weather weather) {
		this.solarActivity = solarActivity;
		this.geomagneticLocation = geomagneticLocation;
		this.sunPosition = sunPosition;
		this.weather = weather;
	}

	public SolarActivity getSolarActivity() {
		return solarActivity;
	}

	public GeomagneticLocation getGeomagneticLocation() {
		return geomagneticLocation;
	}

	public SunPosition getSunPosition() {
		return sunPosition;
	}

	public Weather getWeather() {
		return weather;
	}

	@Override
	public String toString() {
		return "AuroraData{" +
				"solarActivity=" + solarActivity +
				", geomagneticLocation=" + geomagneticLocation +
				", sunPosition=" + sunPosition +
				", weather=" + weather +
				'}';
	}
}