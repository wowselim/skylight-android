package se.gustavkarlsson.skylight.android.services_impl.evaluation

import se.gustavkarlsson.skylight.android.entities.Chance
import se.gustavkarlsson.skylight.android.entities.Chance.Companion.UNKNOWN
import se.gustavkarlsson.skylight.android.entities.GeomagLocation
import se.gustavkarlsson.skylight.android.services.ChanceEvaluator

class GeomagLocationEvaluator : ChanceEvaluator<GeomagLocation> {

    override fun evaluate(value: GeomagLocation): Chance {
        val latitude = value.latitude ?: return UNKNOWN
        val absoluteLatitude = Math.abs(latitude)
        var chance = ((1.0 / FLEX) * absoluteLatitude) - ((BEST - FLEX) / FLEX)
        if (chance > 1.0) {
            chance = 2.0 - chance
        }
        return Chance(chance)
    }

	companion object {
	    const val BEST = 67.0
		const val FLEX = 13.0
	}
}
