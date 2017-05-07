package se.gustavkarlsson.aurora_notifier.android;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import se.gustavkarlsson.aurora_notifier.android.realm.EvaluationCache;

import static java.util.Collections.singletonList;

class RealmSetup {
	private static final String TAG = RealmSetup.class.getSimpleName();

	private RealmSetup() {
	}

	static void run(Context context) {
		Realm.init(context);
		deleteRealmDatabaseIfMigrationNeeded();
		try (Realm realm = Realm.getDefaultInstance()) {
			List<Class<? extends RealmObject>> classes = singletonList(EvaluationCache.class);
			for (Class<? extends RealmObject> clazz : classes) {
				ensureRealmSingletonExists(realm, clazz);
			}
		}
	}

	private static void deleteRealmDatabaseIfMigrationNeeded() {
		RealmConfiguration config = new RealmConfiguration.Builder()
				.deleteRealmIfMigrationNeeded()
				.build();
		Realm.setDefaultConfiguration(config);
	}

	private static void ensureRealmSingletonExists(Realm realm, Class<? extends RealmObject> realmClass) {
		Log.i(TAG, "Ensuring that an instance of " + realmClass.getSimpleName() + " exists.");
		if (realm.where(realmClass).count() == 0) {
			Log.d(TAG, "No instance of " + realmClass.getSimpleName() + " exists. Creating one");
			realm.executeTransaction(r -> realm.createObject(realmClass));
		} else {
			Log.d(TAG, "An instance of " + realmClass + " already exists.");
		}
	}

	static void clearCache() {
		try (Realm realm = Realm.getDefaultInstance()) {
			if (realm.where(EvaluationCache.class).findFirst() != null) {
				EvaluationCache.clear();
				Log.d(TAG, "Cleared EvaluationCache");
			} else {
				Log.d(TAG, "No EvaluationCache found");
			}
		}
	}
}
