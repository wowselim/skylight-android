package se.gustavkarlsson.aurora_notifier.android.evaluation;

import java8.util.stream.RefStreams;
import se.gustavkarlsson.aurora_notifier.android.models.AuroraFactors;
import se.gustavkarlsson.aurora_notifier.android.models.AuroraReport;
import se.gustavkarlsson.aurora_notifier.android.models.factors.GeomagActivity;
import se.gustavkarlsson.aurora_notifier.android.models.factors.GeomagLocation;

import static se.gustavkarlsson.aurora_notifier.android.evaluation.AuroraChance.HIGH;
import static se.gustavkarlsson.aurora_notifier.android.evaluation.AuroraChance.LOW;
import static se.gustavkarlsson.aurora_notifier.android.evaluation.AuroraChance.MEDIUM;
import static se.gustavkarlsson.aurora_notifier.android.evaluation.AuroraChance.NONE;

public class AuroraReportEvaluator implements AuroraChanceEvaluator {

	@Override
	public AuroraChance evaluate(AuroraReport auroraReport) {
		AuroraFactors factors = auroraReport.getFactors();
		AuroraChance weatherChance = new WeatherEvaluator().evaluate(factors.getWeather());
		AuroraChance sunPositionChance = new SunPositionEvaluator().evaluate(factors.getSunPosition());
		AuroraChance geomagAndLocationChance = evaluateGeomagAndLocation(factors.getGeomagActivity(), factors.getGeomagLocation());
		return RefStreams.of(weatherChance, sunPositionChance, geomagAndLocationChance)
				.min(AuroraChance::compareTo)
				.orElseThrow(() -> new IllegalStateException("No AuroraChance supplied"));
	}

	private AuroraChance evaluateGeomagAndLocation(GeomagActivity geomagActivity, GeomagLocation geomagLocation) {
		double geomagChance = (1.0/9.0) * geomagActivity.getKpIndex() + 0.0;
		double locationChance = (-1.0/12.0) * geomagLocation.getDegreesFromClosestPole() + (32/12);
		if (geomagChance <= 0 || locationChance <= 0) {
			return NONE;
		}
		double chance = geomagChance * locationChance;
		if (chance < 0.33) {
			return LOW;
		} else if (chance < 0.66) {
			return MEDIUM;
		} else {
			return HIGH;
		}
	}
}
