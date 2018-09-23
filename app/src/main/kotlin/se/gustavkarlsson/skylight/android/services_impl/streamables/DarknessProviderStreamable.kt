package se.gustavkarlsson.skylight.android.services_impl.streamables

import com.hadisatrio.optional.Optional
import io.reactivex.Flowable
import io.reactivex.Single
import org.threeten.bp.Duration
import se.gustavkarlsson.skylight.android.entities.Darkness
import se.gustavkarlsson.skylight.android.entities.Location
import se.gustavkarlsson.skylight.android.entities.Report
import se.gustavkarlsson.skylight.android.extensions.delay
import se.gustavkarlsson.skylight.android.services.Streamable
import se.gustavkarlsson.skylight.android.services.providers.DarknessProvider
import timber.log.Timber

class DarknessProviderStreamable(
	locations: Flowable<Location>,
	darknessProvider: DarknessProvider,
	pollingInterval: Duration
) : Streamable<Report<Darkness>> {

	override val stream: Flowable<Report<Darkness>> = locations
		.switchMap { location ->
			darknessProvider.get(Single.just(Optional.of(location)))
				.repeatWhen { it.delay(pollingInterval) }
		}
		.distinctUntilChanged()
		.doOnNext { Timber.i("Streamed darkness: %s", it) }

}
