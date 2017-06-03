package se.gustavkarlsson.skylight.android.dagger.modules.replaceable;

import android.view.View;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import se.gustavkarlsson.skylight.android.R;
import se.gustavkarlsson.skylight.android.dagger.scopes.FragmentScope;
import se.gustavkarlsson.skylight.android.evaluation.ChanceEvaluator;
import se.gustavkarlsson.skylight.android.gui.activities.main.fragments.aurora_factors.AuroraFactorView;
import se.gustavkarlsson.skylight.android.gui.activities.main.fragments.aurora_factors.ChanceToColorConverter;
import se.gustavkarlsson.skylight.android.gui.activities.main.fragments.aurora_factors.GeomagActivityPresenter;
import se.gustavkarlsson.skylight.android.models.factors.GeomagActivity;

import static se.gustavkarlsson.skylight.android.dagger.Names.FRAGMENT_ROOT_NAME;

@Module
public class GeomagActivityPresenterModule {

	// Published
	@Provides
	@FragmentScope
	GeomagActivityPresenter provideGeomagActivityPresenter(@Named(FRAGMENT_ROOT_NAME) View rootView, ChanceEvaluator<GeomagActivity> chanceEvaluator, ChanceToColorConverter chanceToColorConverter) {
		AuroraFactorView geomagActivityView = (AuroraFactorView) rootView.findViewById(R.id.aurora_factor_geomag_activity);
		return new GeomagActivityPresenter(geomagActivityView, chanceEvaluator, chanceToColorConverter);
	}
}
