package se.gustavkarlsson.skylight.android.evaluation

import dagger.Reusable
import se.gustavkarlsson.skylight.android.models.factors.GeomagLocation
import javax.inject.Inject

@Reusable
class GeomagLocationEvaluator
@Inject
internal constructor() : ChanceEvaluator<GeomagLocation> {

    override fun evaluate(value: GeomagLocation): Chance {
        val latitude = value.latitude ?: return Chance.UNKNOWN
        val absoluteLatitude = Math.abs(latitude).toDouble()
        var chance = 1.0 / 12.0 * absoluteLatitude - 55.0 / 12.0 // 55-67
        if (chance > 1.0) {
            chance = 2.0 - chance
        }
        return Chance.of(chance)
    }
}