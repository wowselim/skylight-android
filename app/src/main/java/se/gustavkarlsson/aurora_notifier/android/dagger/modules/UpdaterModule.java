package se.gustavkarlsson.aurora_notifier.android.dagger.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import se.gustavkarlsson.aurora_notifier.android.background.Updater;
import se.gustavkarlsson.aurora_notifier.android.cache.AuroraReportCache;

@Module
public abstract class UpdaterModule {

	@Provides
	@Reusable
	static Updater provideUpdater(Context context, AuroraReportCache cache) {
		return new Updater(context, cache);
	}

}
