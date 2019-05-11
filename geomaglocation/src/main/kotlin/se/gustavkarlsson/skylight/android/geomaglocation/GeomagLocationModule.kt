package se.gustavkarlsson.skylight.android.geomaglocation

import org.koin.dsl.module.module
import se.gustavkarlsson.skylight.android.services.providers.GeomagLocationProvider

val geomagLocationModule = module {

	single<GeomagLocationProvider> {
		GeomagLocationProviderImpl(get())
	}

}
