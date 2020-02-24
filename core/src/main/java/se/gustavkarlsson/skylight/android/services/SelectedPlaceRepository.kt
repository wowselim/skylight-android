package se.gustavkarlsson.skylight.android.services

import io.reactivex.Observable
import se.gustavkarlsson.skylight.android.entities.Place

interface SelectedPlaceRepository {
    fun set(place: Place)
    fun get(): Place
    fun stream(): Observable<Place>
}