package se.gustavkarlsson.skylight.android.background.providers;

import se.gustavkarlsson.skylight.android.models.AuroraReport;

public interface AuroraReportProvider {
	AuroraReport getReport(long timeoutMillis);
}
