package se.gustavkarlsson.skylight.android.notifications;

import javax.inject.Inject;

import dagger.Reusable;
import se.gustavkarlsson.skylight.android.cache.ReportNotificationCache;
import se.gustavkarlsson.skylight.android.evaluation.ChanceEvaluator;
import se.gustavkarlsson.skylight.android.evaluation.ChanceLevel;
import se.gustavkarlsson.skylight.android.models.AuroraReport;
import se.gustavkarlsson.skylight.android.settings.Settings;

@Reusable
public class NotificationDecider {
	private final ReportNotificationCache reportNotificationCache;
	private final ChanceEvaluator<AuroraReport> chanceEvaluator;
	private final Settings settings;
	private final ReportOutdatedEvaluator outdatedEvaluator;

	@Inject
	public NotificationDecider(ReportNotificationCache reportNotificationCache, ChanceEvaluator<AuroraReport> chanceEvaluator, Settings settings, ReportOutdatedEvaluator outdatedEvaluator) {
		this.reportNotificationCache = reportNotificationCache;
		this.chanceEvaluator = chanceEvaluator;
		this.settings = settings;
		this.outdatedEvaluator = outdatedEvaluator;
	}

	boolean shouldNotify(AuroraReport newReport) {
		if (!settings.isEnableNotifications()) {
			return false;
		}
		AuroraReport lastReport = reportNotificationCache.getLastNotified();
		ChanceLevel newReportLevel = ChanceLevel.fromChance(chanceEvaluator.evaluate(newReport));
		if (lastReport == null) {
			return isHighEnoughChance(newReportLevel);
		}
		ChanceLevel lastReportLevel = ChanceLevel.fromChance(chanceEvaluator.evaluate(lastReport));
		return isHighEnoughChance(newReportLevel) && (outdatedEvaluator.isOutdated(lastReport) || isHigherThan(newReportLevel, lastReportLevel));
	}

	private boolean isHighEnoughChance(ChanceLevel chanceLevel) {
		ChanceLevel triggerLevel = settings.getTriggerLevel();
		return chanceLevel.ordinal() >= triggerLevel.ordinal();
	}

	private static boolean isHigherThan(ChanceLevel first, ChanceLevel second) {
		return first.ordinal() > second.ordinal();
	}
}
