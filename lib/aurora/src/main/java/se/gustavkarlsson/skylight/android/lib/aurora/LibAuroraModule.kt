package se.gustavkarlsson.skylight.android.lib.aurora

import dagger.Module
import dagger.Provides
import dagger.Reusable
import se.gustavkarlsson.skylight.android.core.entities.ChanceLevel
import se.gustavkarlsson.skylight.android.core.services.ChanceEvaluator
import se.gustavkarlsson.skylight.android.core.services.Formatter
import se.gustavkarlsson.skylight.android.lib.darkness.Darkness
import se.gustavkarlsson.skylight.android.lib.darkness.DarknessProvider
import se.gustavkarlsson.skylight.android.lib.geomaglocation.GeomagLocation
import se.gustavkarlsson.skylight.android.lib.geomaglocation.GeomagLocationProvider
import se.gustavkarlsson.skylight.android.lib.kpindex.KpIndex
import se.gustavkarlsson.skylight.android.lib.kpindex.KpIndexProvider
import se.gustavkarlsson.skylight.android.lib.reversegeocoder.ReverseGeocoder
import se.gustavkarlsson.skylight.android.lib.weather.Weather
import se.gustavkarlsson.skylight.android.lib.weather.WeatherProvider

@Module
object LibAuroraModule {

    @Provides
    @Reusable
    internal fun chanceLevelFormatter(): Formatter<ChanceLevel> = ChanceLevelFormatter

    @Provides
    @Reusable
    internal fun auroraReportProvider(
        reverseGeocoder: ReverseGeocoder,
        darknessProvider: DarknessProvider,
        geomagLocationProvider: GeomagLocationProvider,
        kpIndexProvider: KpIndexProvider,
        weatherProvider: WeatherProvider
    ): AuroraReportProvider =
        CombiningAuroraReportProvider(
            reverseGeocoder,
            darknessProvider,
            geomagLocationProvider,
            kpIndexProvider,
            weatherProvider
        )

    @Provides
    @Reusable
    internal fun completeAuroraReportChanceEvaluator(
        kpIndexEvaluator: ChanceEvaluator<KpIndex>,
        geomagLocationEvaluator: ChanceEvaluator<GeomagLocation>,
        weatherEvaluator: ChanceEvaluator<Weather>,
        darknessEvaluator: ChanceEvaluator<Darkness>
    ): ChanceEvaluator<CompleteAuroraReport> =
        CompleteAuroraReportEvaluator(
            kpIndexEvaluator,
            geomagLocationEvaluator,
            weatherEvaluator,
            darknessEvaluator
        )
}
