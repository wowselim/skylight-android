package se.gustavkarlsson.skylight.android.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

import dagger.Reusable;
import se.gustavkarlsson.skylight.android.R;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@Reusable
public class SharedPreferencesDebugSettings implements DebugSettings {

	private final SharedPreferences defaultPreferences;
	private final String overrideValuesKey;
	private final String kpIndexKey;
	private final String geomagLatitudeKey;
	private final String sunZenithAngleKey;
	private final String cloudPercentageKey;

	@Inject
	SharedPreferencesDebugSettings(Context context) {
		defaultPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		kpIndexKey = context.getString(R.string.pref_kp_index_key);
		geomagLatitudeKey = context.getString(R.string.pref_geomag_latitude_key);
		sunZenithAngleKey = context.getString(R.string.pref_sun_position_key);
		cloudPercentageKey = context.getString(R.string.pref_cloud_percentage_key);
		overrideValuesKey = context.getString(R.string.pref_override_values_key);
	}

	@Override
	public boolean isOverrideValues() {
		return defaultPreferences.getBoolean(overrideValuesKey, false);
	}

	@Override
	public float getKpIndex() {
		return parseFloat(defaultPreferences.getString(kpIndexKey, "0"));
	}

	@Override
	public float getGeomagLatitude() {
		return parseFloat(defaultPreferences.getString(geomagLatitudeKey, "0"));
	}

	@Override
	public float getSunZenithAngle() {
		return parseFloat(defaultPreferences.getString(sunZenithAngleKey, "0"));
	}

	@Override
	public int getCloudPercentage() {
		return parseInt(defaultPreferences.getString(cloudPercentageKey, "0"));
	}

}