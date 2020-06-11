package se.gustavkarlsson.skylight.android.lib.weather

import assertk.assert
import assertk.assertions.isBetween
import assertk.assertions.isEqualTo
import org.junit.Test
import se.gustavkarlsson.skylight.android.entities.Chance
import se.gustavkarlsson.skylight.android.entities.Chance.Companion.IMPOSSIBLE
import se.gustavkarlsson.skylight.android.entities.Chance.Companion.MAX
import se.gustavkarlsson.skylight.android.entities.Chance.Companion.UNKNOWN

class WeatherEvaluatorTest {

    private val impl = WeatherEvaluator

    @Test
    fun _0CloudPercentageEvaluatesToMax() {
        val chance = impl.evaluate(Weather(0))

        assert(chance).isEqualTo(MAX)
    }

    @Test
    fun _100CloudPercentageEvaluatesToImpossible() {
        val chance = impl.evaluate(Weather(100))

        assert(chance).isEqualTo(IMPOSSIBLE)
    }

    @Test
    fun minus1CloudPercentageEvaluatesToUnknown() {
        val chance = impl.evaluate(Weather(-1))

        assert(chance).isEqualTo(UNKNOWN)
    }

    @Test
    fun _101CloudPercentageEvaluatesToUnknown() {
        val chance = impl.evaluate(Weather(101))

        assert(chance).isEqualTo(UNKNOWN)
    }

    @Test
    fun _50CloudPercentageEvaluatesToMediumChance() {
        val chance = impl.evaluate(Weather(25))

        assert(chance).isBetween(Chance(0.4), Chance(0.6))
    }
}
