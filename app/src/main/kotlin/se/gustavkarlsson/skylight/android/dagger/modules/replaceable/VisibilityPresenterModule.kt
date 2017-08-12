package se.gustavkarlsson.skylight.android.dagger.modules.replaceable

import android.view.View
import dagger.Module
import dagger.Provides
import se.gustavkarlsson.skylight.android.R
import se.gustavkarlsson.skylight.android.dagger.FRAGMENT_ROOT_NAME
import se.gustavkarlsson.skylight.android.dagger.scopes.FragmentScope
import se.gustavkarlsson.skylight.android.entities.Visibility
import se.gustavkarlsson.skylight.android.gui.activities.main.fragments.aurora_factors.AuroraFactorView
import se.gustavkarlsson.skylight.android.util.ChanceToColorConverter
import se.gustavkarlsson.skylight.android.services_impl.presenters.factors.VisibilityFactorViewPresenter
import se.gustavkarlsson.skylight.android.services.Presenter
import se.gustavkarlsson.skylight.android.services.evaluation.ChanceEvaluator
import javax.inject.Named

@Module
class VisibilityPresenterModule {

    // Published
    @Provides
    @FragmentScope
    fun provideVisibilityPresenter(
		@Named(FRAGMENT_ROOT_NAME) rootView: View,
		chanceEvaluator: ChanceEvaluator<Visibility>,
		chanceToColorConverter: ChanceToColorConverter
	): Presenter<Visibility> {
        val geomagActivityView = rootView.findViewById(R.id.visibility) as AuroraFactorView
        return VisibilityFactorViewPresenter(geomagActivityView, chanceEvaluator, chanceToColorConverter)
    }
}
