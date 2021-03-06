package se.gustavkarlsson.skylight.android.lib.weather

import se.gustavkarlsson.skylight.android.core.services.ChanceEvaluator
import se.gustavkarlsson.skylight.android.core.services.Formatter

interface WeatherComponent {

    fun weatherProvider(): WeatherProvider

    fun weatherChanceEvaluator(): ChanceEvaluator<Weather>

    fun weatherFormatter(): Formatter<Weather>

    interface Setter {
        fun setWeatherComponent(component: WeatherComponent) {
            instance = component
        }
    }

    companion object {
        lateinit var instance: WeatherComponent
            private set
    }
}
