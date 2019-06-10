package se.gustavkarlsson.skylight.android.lib.geocoder

import org.koin.dsl.module.module

val libGeocoderModule = module {

	single<Geocoder> {
		MapboxGeocoder(BuildConfig.MAPBOX_API_KEY)
	}
}