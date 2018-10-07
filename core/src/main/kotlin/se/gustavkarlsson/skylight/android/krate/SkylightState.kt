package se.gustavkarlsson.skylight.android.krate

import se.gustavkarlsson.skylight.android.entities.AuroraReport
import se.gustavkarlsson.skylight.android.entities.ChanceLevel

data class SkylightState(
	val settings: Settings,
	val isFirstRun: Boolean? = null,
	val isLocationPermissionGranted: Boolean? = null,
	val isGooglePlayServicesAvailable: Boolean? = null,
	val auroraReport: AuroraReport? = null,
	val throwable: Throwable? = null
) {

	data class Settings(
		val notificationsEnabled: Boolean,
		val triggerLevel: ChanceLevel
	)
}
