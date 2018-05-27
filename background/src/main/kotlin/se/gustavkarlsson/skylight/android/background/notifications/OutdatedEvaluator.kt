package se.gustavkarlsson.skylight.android.background.notifications

import org.threeten.bp.Instant
import org.threeten.bp.LocalTime.NOON
import se.gustavkarlsson.skylight.android.extensions.until
import se.gustavkarlsson.skylight.android.services.providers.TimeProvider

internal class OutdatedEvaluator(
	private val timeProvider: TimeProvider
) {

	fun isOutdated(time: Instant): Boolean {
		val currentZoneId = timeProvider.getZoneId().blockingGet()
		val now = timeProvider.getTime().blockingGet()
		val today = timeProvider.getLocalDate().blockingGet()
		val noonToday = NOON.atDate(today).atZone(currentZoneId).toInstant()
		val age = time until now
		return age.toHours() > 12 || now > noonToday && time < noonToday
	}
}