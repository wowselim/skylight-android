package se.gustavkarlsson.aurora_notifier.android.realm;

import io.realm.Realm;
import io.realm.RealmObject;

public class Requirements extends RealmObject {
	private boolean fulfilled;

	public static boolean isFulfilled() {
		try (Realm realm = Realm.getDefaultInstance()) {
			Requirements requirements = getSingleton(realm);
			return requirements.fulfilled;
		}
	}

	public static void setFulfilled(boolean fulfilled) {
		try (Realm realm = Realm.getDefaultInstance()) {
			Requirements requirements = getSingleton(realm);
			if (requirements.fulfilled != fulfilled) {
				realm.executeTransaction(r -> requirements.fulfilled = fulfilled);
			}
		}
	}

	private static Requirements getSingleton(Realm realm) {
		return realm.where(Requirements.class).findFirst();
	}
}