package se.gustavkarlsson.skylight.android.lib.location

import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import dagger.Module
import dagger.Provides
import org.koin.dsl.module.module
import se.gustavkarlsson.skylight.android.extensions.minutes
import se.gustavkarlsson.skylight.android.extensions.seconds
import se.gustavkarlsson.skylight.android.services.LocationProvider
import javax.inject.Singleton

val libLocationModule = module {

    single<LocationProvider> {
        RxLocationLocationProvider(
            fusedLocation = RxLocation(get()).location(),
            timeout = 30.seconds,
            requestAccuracy = LocationRequest.PRIORITY_HIGH_ACCURACY,
            throttleDuration = 1.minutes,
            firstPollingInterval = 10.seconds,
            restPollingInterval = 10.minutes,
            retryDelay = 30.seconds
        )
    }
}

@Module
class LibLocationModule {

    @Provides
    @Singleton
    internal fun locationProvider(context: Context): LocationProvider =
        RxLocationLocationProvider(
            fusedLocation = RxLocation(context).location(),
            timeout = 30.seconds,
            requestAccuracy = LocationRequest.PRIORITY_HIGH_ACCURACY,
            throttleDuration = 1.minutes,
            firstPollingInterval = 10.seconds,
            restPollingInterval = 10.minutes,
            retryDelay = 30.seconds
        )
}
