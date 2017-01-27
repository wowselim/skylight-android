package se.gustavkarlsson.aurora_notifier.android.background.providers.impl;

import net.e175.klaus.solarpositioning.AzimuthZenithAngle;
import net.e175.klaus.solarpositioning.Grena3;

import java.util.GregorianCalendar;

import se.gustavkarlsson.aurora_notifier.android.background.providers.SunPositionProvider;
import se.gustavkarlsson.aurora_notifier.android.models.factors.SunPosition;

public class KlausBrunnerSunPositionProvider implements SunPositionProvider {
	@Override
	public SunPosition getSunPosition(long timeMillis, double latitude, double longitude) {
		GregorianCalendar date = new GregorianCalendar();
		date.setTimeInMillis(timeMillis);
		AzimuthZenithAngle azimuthZenithAngle = Grena3.calculateSolarPosition(date, latitude, longitude, 0);
		return new SunPosition((float) azimuthZenithAngle.getZenithAngle());
	}
}
