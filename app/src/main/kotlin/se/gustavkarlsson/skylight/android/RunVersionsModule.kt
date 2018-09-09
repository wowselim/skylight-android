package se.gustavkarlsson.skylight.android

import org.koin.dsl.module.module
import se.gustavkarlsson.skylight.android.services.RunVersionManager
import se.gustavkarlsson.skylight.android.services_impl.SharedPreferencesRunVersionManager

val runVersionsModule = module {

	single<RunVersionManager> {
		SharedPreferencesRunVersionManager(get())
	}

}
