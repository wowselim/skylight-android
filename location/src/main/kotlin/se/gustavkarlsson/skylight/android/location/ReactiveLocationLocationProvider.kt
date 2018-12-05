package se.gustavkarlsson.skylight.android.location

import android.annotation.SuppressLint
import com.google.android.gms.location.LocationRequest
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.Duration
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import se.gustavkarlsson.koptional.Absent
import se.gustavkarlsson.koptional.Optional
import se.gustavkarlsson.koptional.optionalOf
import se.gustavkarlsson.skylight.android.entities.Location
import se.gustavkarlsson.skylight.android.extensions.timeout
import se.gustavkarlsson.skylight.android.services.providers.LocationProvider
import timber.log.Timber

internal class ReactiveLocationLocationProvider(
	private val reactiveLocationProvider: ReactiveLocationProvider,
	private val timeout: Duration
) : LocationProvider {

	private val locationRequest = LocationRequest().apply {
		priority = LocationRequest.PRIORITY_HIGH_ACCURACY
		interval = timeout.toMillis() / 2
		numUpdates = 1
	}

	@SuppressLint("MissingPermission")
	override fun get(): Single<Optional<Location>> {
		return reactiveLocationProvider
			.getUpdatedLocation(locationRequest)
			.subscribeOn(Schedulers.io())
			.firstOrError()
			.map { optionalOf(Location(it.latitude, it.longitude)) }
			.timeout(timeout)
			.doOnError { Timber.w(it, "Failed to get location") }
			.onErrorReturnItem(Absent)
			.doOnSuccess { Timber.i("Provided location: %s", it) }
	}
}