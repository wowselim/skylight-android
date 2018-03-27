package se.gustavkarlsson.skylight.android.services

import io.reactivex.Flowable
import se.gustavkarlsson.skylight.android.entities.ChanceLevel

interface Settings {
    val notificationsEnabled: Boolean
	val notificationsEnabledChanges: Flowable<Boolean>

    val triggerLevel: ChanceLevel
	val triggerLevelChanges: Flowable<ChanceLevel>
}
