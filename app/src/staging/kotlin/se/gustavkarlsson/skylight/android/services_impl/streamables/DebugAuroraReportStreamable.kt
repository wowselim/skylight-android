package se.gustavkarlsson.skylight.android.services_impl.streamables

import io.reactivex.Flowable
import io.reactivex.Single
import org.threeten.bp.Instant
import se.gustavkarlsson.skylight.android.entities.*
import se.gustavkarlsson.skylight.android.extensions.delay
import se.gustavkarlsson.skylight.android.extensions.minutes
import se.gustavkarlsson.skylight.android.services.DebugSettings
import se.gustavkarlsson.skylight.android.services.Streamable

class DebugAuroraReportStreamable(
	private val realStreamable: Streamable<AuroraReport>,
	private val debugSettings: DebugSettings,
	private val now: Single<Instant>
) : Streamable<AuroraReport> {

	override val stream: Flowable<AuroraReport>
		get() = debugSettings.overrideValuesChanges
			.switchMap { enabled ->
				if (enabled) {
					Single.fromCallable {
						val auroraFactors = createDebugFactors()
						val timestamp = now.blockingGet()
						AuroraReport(timestamp, "Fake Location", auroraFactors)
					}.repeatWhen { it.delay(POLLING_INTERVAL) }
				} else {
					realStreamable.stream
				}
			}

	private fun createDebugFactors(): AuroraFactors {
		val kpIndex = KpIndex(debugSettings.kpIndex)
		val geomagLocation = GeomagLocation(debugSettings.geomagLatitude)
		val darkness = Darkness(debugSettings.sunZenithAngle)
		val weather = Weather(debugSettings.cloudPercentage)
		return AuroraFactors(kpIndex, geomagLocation, darkness, weather)
	}

	companion object {
		val POLLING_INTERVAL = 1.minutes
	}
}
