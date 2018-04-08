package se.gustavkarlsson.skylight.android.services_impl.evaluation

import se.gustavkarlsson.skylight.android.entities.Chance
import se.gustavkarlsson.skylight.android.entities.Chance.Companion.UNKNOWN
import se.gustavkarlsson.skylight.android.entities.KpIndex
import se.gustavkarlsson.skylight.android.services.ChanceEvaluator
import kotlin.math.pow

class KpIndexEvaluator : ChanceEvaluator<KpIndex> {

    override fun evaluate(value: KpIndex): Chance {
        val kpIndex = value.value ?: return UNKNOWN
		if (kpIndex < WORST || kpIndex > BEST) {
			return UNKNOWN
        }
		val chance = (COEFF_A * kpIndex.pow(2)) + (COEFF_B * kpIndex)
		return Chance(chance)
    }

	// https://www.wolframalpha.com/input/?i=y%3D(-0.009876543*x%5E2)%2B(0.2*x)
	companion object {
		const val WORST = 0.0
		const val BEST = 9.0
		private const val COEFF_A = -0.009876543
		private const val COEFF_B = 0.2
	}
}
