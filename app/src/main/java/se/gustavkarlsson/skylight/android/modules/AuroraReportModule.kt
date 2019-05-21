package se.gustavkarlsson.skylight.android.modules

import org.koin.dsl.module.module
import se.gustavkarlsson.skylight.android.services.AuroraReportProvider
import se.gustavkarlsson.skylight.android.services_impl.providers.CombiningAuroraReportProvider

val auroraReportModule = module {

	single {
		CombiningAuroraReportProvider(
			locationProvider = get(),
			reverseGeocoder = get(),
			darknessProvider = get(),
			geomagLocationProvider = get(),
			kpIndexProvider = get(),
			weatherProvider = get()
		)
	}

	single<AuroraReportProvider> {
		get<CombiningAuroraReportProvider>()
	}
}