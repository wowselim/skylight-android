package se.gustavkarlsson.aurora_notifier.android;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import se.gustavkarlsson.aurora_notifier.android.background.BootReceiver;
import se.gustavkarlsson.aurora_notifier.android.realm.RealmGeomagneticCoordinates;
import se.gustavkarlsson.aurora_notifier.android.realm.RealmKpIndex;
import se.gustavkarlsson.aurora_notifier.android.realm.RealmSunPosition;
import se.gustavkarlsson.aurora_notifier.android.realm.RealmWeather;

public class AuroraNotifier extends Application {
	private static final String TAG = AuroraNotifier.class.getSimpleName();

	@Override
	public void onCreate() {
		Log.v(TAG, "onCreate");
		super.onCreate();
		setupRealm();
		setupAlarms();
	}

	private void setupRealm() {
		Realm.init(this);
		RealmConfiguration config = new RealmConfiguration.Builder()
				.deleteRealmIfMigrationNeeded()
				.build();
		Realm.setDefaultConfiguration(config);
		Realm realm = Realm.getDefaultInstance();
		List<Class<? extends RealmObject>> classes = Arrays.asList(
				RealmKpIndex.class,
				RealmWeather.class,
				RealmSunPosition.class,
				RealmGeomagneticCoordinates.class);
		for (Class<? extends RealmObject> clazz : classes) {
			ensureRealmSingletonExists(realm, clazz);
		}
		realm.close();
	}

	private void ensureRealmSingletonExists(Realm realm, Class<? extends RealmObject> realmClass) {
		Log.i(TAG, "Ensuring that an instance of " + realmClass.getSimpleName() + " exists.");
		if (realm.where(realmClass).count() == 0) {
			Log.d(TAG, "No instance of " + realmClass.getSimpleName() + " exists. Creating one");
			realm.beginTransaction();
			realm.createObject(realmClass);
			realm.commitTransaction();
		} else {
			Log.d(TAG, "An instance of " + realmClass + " already exists.");
		}
	}

	private void setupAlarms() {
		Log.v(TAG, "setupAlarms");
		LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
		Intent intent = new Intent(BootReceiver.ACTION_SETUP_ALARMS, null, this, BootReceiver.class);
		localBroadcastManager.registerReceiver(new BootReceiver(), new IntentFilter(BootReceiver.ACTION_SETUP_ALARMS));
		localBroadcastManager.sendBroadcast(intent);
	}
}
