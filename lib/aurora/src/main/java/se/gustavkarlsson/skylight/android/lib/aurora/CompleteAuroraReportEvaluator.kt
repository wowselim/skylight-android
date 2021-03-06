package se.gustavkarlsson.skylight.android.lib.aurora

import se.gustavkarlsson.skylight.android.core.entities.Chance
import se.gustavkarlsson.skylight.android.core.entities.Report
import se.gustavkarlsson.skylight.android.core.services.ChanceEvaluator
import se.gustavkarlsson.skylight.android.lib.darkness.Darkness
import se.gustavkarlsson.skylight.android.lib.geomaglocation.GeomagLocation
import se.gustavkarlsson.skylight.android.lib.kpindex.KpIndex
import se.gustavkarlsson.skylight.android.lib.weather.Weather

internal class CompleteAuroraReportEvaluator(
    private val kpIndexEvaluator: ChanceEvaluator<KpIndex>,
    private val geomagLocationEvaluator: ChanceEvaluator<GeomagLocation>,
    private val weatherEvaluator: ChanceEvaluator<Weather>,
    private val darknessEvaluator: ChanceEvaluator<Darkness>
) : ChanceEvaluator<CompleteAuroraReport> {

    override fun evaluate(value: CompleteAuroraReport): Chance {
        val activityChance = value.kpIndex.getChance(kpIndexEvaluator)
        val locationChance = value.geomagLocation.getChance(geomagLocationEvaluator)
        val weatherChance = value.weather.getChance(weatherEvaluator)
        val darknessChance = value.darkness.getChance(darknessEvaluator)

        val chances = listOf(activityChance, locationChance, weatherChance, darknessChance)

        if (chances.any { !it.isKnown }) {
            return Chance.UNKNOWN
        }

        if (chances.any { !it.isPossible }) {
            return Chance.IMPOSSIBLE
        }

        return listOf(weatherChance, darknessChance, activityChance * locationChance).min()!!
    }
}

private fun <T : Any> Report<T>.getChance(evaluator: ChanceEvaluator<T>): Chance =
    when (this) {
        is Report.Success -> evaluator.evaluate(value)
        is Report.Error -> Chance.UNKNOWN
    }
