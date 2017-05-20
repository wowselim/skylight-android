package se.gustavkarlsson.aurora_notifier.android.gui.activities.main.fragments.aurora_factors;

import se.gustavkarlsson.aurora_notifier.android.R;
import se.gustavkarlsson.aurora_notifier.android.evaluation.VisibilityEvaluator;
import se.gustavkarlsson.aurora_notifier.android.models.factors.Visibility;

class VisibilityPresenter extends AbstractAuroraFactorPresenter<Visibility> {

	VisibilityPresenter(AuroraFactorView factorView) {
		super(factorView, new VisibilityEvaluator());
	}

	@Override
	int getTitleResourceId() {
		return R.string.factor_visibility_title;
	}

	@Override
	int getDescriptionResourceId() {
		return R.string.factor_visibility_desc;
	}

	@Override
	String evaluateText(Visibility visibility) {
		Integer clouds = visibility.getCloudPercentage();
		if (clouds == null) {
			return "?";
		}
		int visibilityPercentage = 100 - clouds;
		return Integer.toString(visibilityPercentage) + '%';
	}
}