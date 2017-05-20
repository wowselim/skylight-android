package se.gustavkarlsson.aurora_notifier.android.gui.activities.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import se.gustavkarlsson.aurora_notifier.android.R;
import se.gustavkarlsson.aurora_notifier.android.background.UpdateScheduler;

import static se.gustavkarlsson.aurora_notifier.android.AuroraNotifier.getApplicationComponent;

public class SettingsFragment extends PreferenceFragment {
	private static final String TAG = SettingsFragment.class.getSimpleName();

	private NotificationsChangedListener notificationsChangedListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);

		UpdateScheduler updateScheduler = getApplicationComponent(getActivity()).getUpdateScheduler();
		notificationsChangedListener = new NotificationsChangedListener(updateScheduler, getResources().getString(R.string.pref_notifications_key));
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(notificationsChangedListener);
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestroy");
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(notificationsChangedListener);
		super.onDestroy();
	}

	private static class NotificationsChangedListener implements SharedPreferences.OnSharedPreferenceChangeListener {
		private final UpdateScheduler updateScheduler;
		private final String notificationsKey;

		private NotificationsChangedListener(UpdateScheduler updateScheduler, String notificationsKey) {
			this.updateScheduler = updateScheduler;
			this.notificationsKey = notificationsKey;
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			if (key.equals(this.notificationsKey)) {
				updateScheduler.setupBackgroundUpdates();
			}
		}
	}
}