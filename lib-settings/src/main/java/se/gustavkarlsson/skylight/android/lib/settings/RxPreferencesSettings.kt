package se.gustavkarlsson.skylight.android.lib.settings

import android.content.Context
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import se.gustavkarlsson.skylight.android.entities.ChanceLevel

internal class RxPreferencesSettings(
	context: Context,
	rxSharedPreferences: RxSharedPreferences
) : Settings {

	private val notificationsEnabledPreference by lazy {
		val key = context.getString(R.string.pref_notifications_key)
		val default = context.resources.getBoolean(R.bool.pref_notifications_default)
		rxSharedPreferences.getBoolean(key, default)
	}

	private val triggerLevelPreference by lazy {
		val key = context.getString(R.string.pref_trigger_level_key)
		val default = context.getString(R.string.pref_trigger_level_default)
		rxSharedPreferences.getString(key, default)
	}

	override val notificationsEnabled: Boolean
		get() = notificationsEnabledPreference.get()

	override val notificationsEnabledChanges: Flowable<Boolean> = notificationsEnabledPreference
		.asObservable()
		.toFlowable(BackpressureStrategy.LATEST)

	override val triggerLevel: ChanceLevel
		get() = triggerLevelPreference.get().let { ChanceLevel.values()[it.toInt()] }

	override val triggerLevelChanges: Flowable<ChanceLevel> = triggerLevelPreference
		.asObservable()
		.map { ChanceLevel.values()[it.toInt()] }
		.toFlowable(BackpressureStrategy.LATEST)
}