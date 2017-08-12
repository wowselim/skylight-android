package se.gustavkarlsson.skylight.android.dagger.modules.clean

import dagger.Module
import dagger.Provides
import dagger.Reusable
import se.gustavkarlsson.skylight.android.services.providers.AuroraReportProvider
import se.gustavkarlsson.skylight.android.dagger.LAST_NAME
import se.gustavkarlsson.skylight.android.entities.AuroraReport
import se.gustavkarlsson.skylight.android.services.SingletonCache
import javax.inject.Named

@Module
class LastAuroraReportProviderModule {

	@Provides
	@Reusable
	@Named(LAST_NAME)
	fun provideLastAuroraReportProvider(
		@Named(LAST_NAME) cache: SingletonCache<AuroraReport>
	): AuroraReportProvider {
		// TODO Replace with real implementation
		return object : AuroraReportProvider {
			override fun get(): AuroraReport {
				return cache.value
			}
		}
	}
}