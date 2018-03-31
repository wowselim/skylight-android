package se.gustavkarlsson.skylight.android.services_impl.formatters

import se.gustavkarlsson.skylight.android.entities.Darkness
import se.gustavkarlsson.skylight.android.services.formatters.SingleValueFormatter

/**
 * 0% at 90°. 100% at 108°
 */
class DarknessFormatter : SingleValueFormatter<Darkness> {
	override fun format(value: Darkness): CharSequence {
		val zenithAngle = value.sunZenithAngle ?: return "?"
		val darknessFactor = 1.0 / 18.0 * zenithAngle - 5.0
		var darknessPercentage = Math.round(darknessFactor * 100.0)
		darknessPercentage = Math.max(0, darknessPercentage)
		darknessPercentage = Math.min(100, darknessPercentage)
		return "$darknessPercentage%"
	}
}