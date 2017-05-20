package se.gustavkarlsson.aurora_notifier.android.dagger.modules;

import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public abstract class SystemServiceModule {

	@Provides
	@Reusable
	static NotificationManager provideNotificationManager(Context context) {
		return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Provides
	@Reusable
	static ConnectivityManager provideConnectivityManager(Context context) {
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

}