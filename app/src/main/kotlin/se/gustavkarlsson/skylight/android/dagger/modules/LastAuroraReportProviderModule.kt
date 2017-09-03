package se.gustavkarlsson.skylight.android.dagger.modules

import dagger.Module
import dagger.Provides
import dagger.Reusable
import se.gustavkarlsson.skylight.android.dagger.LAST_NAME
import se.gustavkarlsson.skylight.android.entities.AuroraReport
import se.gustavkarlsson.skylight.android.services.SingletonCache
import se.gustavkarlsson.skylight.android.services.providers.AuroraReportProvider
import se.gustavkarlsson.skylight.android.services_impl.providers.AuroraReportFromCacheProvider
import javax.inject.Named

@Module
class LastAuroraReportProviderModule {

    @Provides
    @Reusable
    @Named(LAST_NAME)
    fun provideLastAuroraReportProvider(@Named(LAST_NAME) cache: SingletonCache<AuroraReport>): AuroraReportProvider = AuroraReportFromCacheProvider(cache)
}
