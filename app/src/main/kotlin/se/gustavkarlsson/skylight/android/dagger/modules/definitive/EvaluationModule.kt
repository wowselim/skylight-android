package se.gustavkarlsson.skylight.android.dagger.modules.definitive

import dagger.Binds
import dagger.Module
import dagger.Reusable
import se.gustavkarlsson.skylight.android.evaluation.*
import se.gustavkarlsson.skylight.android.models.AuroraReport
import se.gustavkarlsson.skylight.android.models.factors.Darkness
import se.gustavkarlsson.skylight.android.models.factors.GeomagActivity
import se.gustavkarlsson.skylight.android.models.factors.GeomagLocation
import se.gustavkarlsson.skylight.android.models.factors.Visibility

@Module
abstract class EvaluationModule {

    @Binds
    @Reusable
    abstract fun bindGeomagActivityEvaluator(impl: GeomagActivityEvaluator): ChanceEvaluator<GeomagActivity>

    @Binds
    @Reusable
    abstract fun bindGeomagLocationEvaluator(impl: GeomagLocationEvaluator): ChanceEvaluator<GeomagLocation>

    @Binds
    @Reusable
    abstract fun bindVisibilityEvaluator(impl: VisibilityEvaluator): ChanceEvaluator<Visibility>

    @Binds
    @Reusable
    abstract fun bindDarknessEvaluator(impl: DarknessEvaluator): ChanceEvaluator<Darkness>

    @Binds
    @Reusable
    abstract fun bindAuroraReportEvaluator(impl: AuroraReportEvaluator): ChanceEvaluator<AuroraReport>

}