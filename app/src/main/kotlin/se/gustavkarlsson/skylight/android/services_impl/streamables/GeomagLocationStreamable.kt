package se.gustavkarlsson.skylight.android.services_impl.streamables

import dagger.Reusable
import io.reactivex.Flowable
import io.reactivex.Single
import se.gustavkarlsson.skylight.android.entities.GeomagLocation
import se.gustavkarlsson.skylight.android.entities.Location
import se.gustavkarlsson.skylight.android.services.Streamable
import se.gustavkarlsson.skylight.android.services.providers.GeomagLocationProvider
import javax.inject.Inject

@Reusable
class GeomagLocationStreamable
@Inject
constructor(
	locations: Flowable<Location>,
	geomagLocationProvider: GeomagLocationProvider
) : Streamable<GeomagLocation> {
	override val stream: Flowable<GeomagLocation> = locations
		.switchMap {
			geomagLocationProvider.get(Single.just(it))
				.toFlowable()
		}
		.replay(1)
		.refCount()
}
