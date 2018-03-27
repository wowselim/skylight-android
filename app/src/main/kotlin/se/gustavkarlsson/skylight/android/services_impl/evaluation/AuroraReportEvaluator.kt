package se.gustavkarlsson.skylight.android.services_impl.evaluation

import dagger.Reusable
import se.gustavkarlsson.skylight.android.entities.*
import se.gustavkarlsson.skylight.android.services.ChanceEvaluator
import javax.inject.Inject

@Reusable
class AuroraReportEvaluator
@Inject
constructor(
	private val kpIndexEvaluator: ChanceEvaluator<KpIndex>,
	private val geomagLocationEvaluator: ChanceEvaluator<GeomagLocation>,
	private val visibilityEvaluator: ChanceEvaluator<Visibility>,
	private val darknessEvaluator: ChanceEvaluator<Darkness>
) : ChanceEvaluator<AuroraReport> {

	override fun evaluate(value: AuroraReport): Chance {
		val factors = value.factors
		val activityChance = kpIndexEvaluator.evaluate(factors.kpIndex)
		val locationChance = geomagLocationEvaluator.evaluate(factors.geomagLocation)
		val visibilityChance = visibilityEvaluator.evaluate(factors.visibility)
		val darknessChance = darknessEvaluator.evaluate(factors.darkness)

		val chances = listOf(activityChance, locationChance, visibilityChance, darknessChance)

		if (chances.any { !it.isKnown }) {
			return Chance.UNKNOWN
		}

		if (chances.any { !it.isPossible }) {
			return Chance.IMPOSSIBLE
		}

		return listOf(visibilityChance, darknessChance, activityChance * locationChance).min()!!
	}
}
