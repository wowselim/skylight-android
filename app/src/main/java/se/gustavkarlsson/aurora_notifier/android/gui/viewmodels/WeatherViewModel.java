package se.gustavkarlsson.aurora_notifier.android.gui.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import se.gustavkarlsson.aurora_notifier.android.realm.RealmWeather;

public class WeatherViewModel extends BaseObservable {
	private final RealmWeather realmWeather;

	public WeatherViewModel(RealmWeather realmWeather) {
		this.realmWeather = realmWeather;
	}

	@Bindable
	public String getCloudPercentage() {
		return realmWeather.getCloudPercentage() == null ? "-" : "" + realmWeather.getCloudPercentage() + "% @ " + realmWeather.getTimestamp();
	}
}
