package se.gustavkarlsson.skylight.android.lib.darkness

import com.ioki.textref.TextRef
import se.gustavkarlsson.skylight.android.core.services.Formatter
import kotlin.math.roundToInt

/**
 * 0% at 90°. 100% at 108°
 */
internal object DarknessFormatter : Formatter<Darkness> {
    override fun format(value: Darkness): TextRef {
        val zenithAngle = value.sunZenithAngle
        val darknessFactor = 1.0 / 18.0 * zenithAngle - 5.0
        val darknessPercentage = (darknessFactor * 100.0)
            .roundToInt()
            .coerceIn(0..100)
        return TextRef.string("$darknessPercentage%")
    }
}
