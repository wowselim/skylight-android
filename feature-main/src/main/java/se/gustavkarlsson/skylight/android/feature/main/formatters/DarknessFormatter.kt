package se.gustavkarlsson.skylight.android.feature.main.formatters

import com.ioki.textref.TextRef
import se.gustavkarlsson.skylight.android.entities.Darkness
import se.gustavkarlsson.skylight.android.services.Formatter

/**
 * 0% at 90°. 100% at 108°
 */
internal object DarknessFormatter : Formatter<Darkness> {
	override fun format(value: Darkness): TextRef {
		val zenithAngle = value.sunZenithAngle
		val darknessFactor = 1.0 / 18.0 * zenithAngle - 5.0
		var darknessPercentage = Math.round(darknessFactor * 100.0)
		darknessPercentage = Math.max(0, darknessPercentage)
		darknessPercentage = Math.min(100, darknessPercentage)
		return TextRef("$darknessPercentage%")
	}
}