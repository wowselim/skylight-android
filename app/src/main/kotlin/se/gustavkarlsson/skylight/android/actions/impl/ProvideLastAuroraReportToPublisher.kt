package se.gustavkarlsson.skylight.android.actions.impl

import se.gustavkarlsson.skylight.android.actions.ShowLastAuroraReport
import se.gustavkarlsson.skylight.android.entities.AuroraReport
import se.gustavkarlsson.skylight.android.services.Provider
import se.gustavkarlsson.skylight.android.services.StreamPublisher
import se.gustavkarlsson.skylight.android.util.UserFriendlyException

class ProvideLastAuroraReportToPublisher(
	provider: Provider<AuroraReport>,
	publisher: StreamPublisher<AuroraReport>,
	errorPublisher: StreamPublisher<UserFriendlyException>
) : ProvideToPublisher<AuroraReport>(provider, publisher, errorPublisher), ShowLastAuroraReport
