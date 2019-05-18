package se.gustavkarlsson.skylight.android.krate

import se.gustavkarlsson.skylight.android.entities.Place

sealed class Command {
	object RefreshAll : Command()
	object Bootstrap : Command()
	object SignalLocationPermissionGranted : Command()
	object SignalGooglePlayServicesInstalled : Command()
	object SignalFirstRunCompleted : Command()
	data class SelectPlace(val place: Place?) : Command()
}

