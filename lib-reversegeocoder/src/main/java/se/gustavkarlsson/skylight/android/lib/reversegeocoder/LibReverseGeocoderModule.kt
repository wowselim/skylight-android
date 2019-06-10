package se.gustavkarlsson.skylight.android.lib.reversegeocoder

import android.location.Geocoder
import org.koin.dsl.module.module
import se.gustavkarlsson.skylight.android.extensions.seconds

val libReverseGeocoderModule = module {

	single<ReverseGeocoder> {
		AndroidReverseGeocoder(
			geocoder = Geocoder(get()),
			retryDelay = 10.seconds
		)
	}

}