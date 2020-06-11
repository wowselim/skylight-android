package se.gustavkarlsson.skylight.android.lib.geocoder

import se.gustavkarlsson.skylight.android.entities.Location

data class PlaceSuggestion(
    val location: Location,
    val fullName: String,
    val simpleName: String
)
