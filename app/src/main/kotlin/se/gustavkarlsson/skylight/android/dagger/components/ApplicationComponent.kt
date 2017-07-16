package se.gustavkarlsson.skylight.android.dagger.components

import dagger.Component
import org.threeten.bp.Clock
import se.gustavkarlsson.skylight.android.background.UpdateJob
import se.gustavkarlsson.skylight.android.background.UpdateScheduler
import se.gustavkarlsson.skylight.android.services.providers.AuroraReportProvider
import se.gustavkarlsson.skylight.android.dagger.modules.definitive.ActivityModule
import se.gustavkarlsson.skylight.android.dagger.modules.definitive.EvaluationModule
import se.gustavkarlsson.skylight.android.dagger.modules.definitive.LatestAuroraReportStreamModule
import se.gustavkarlsson.skylight.android.dagger.modules.definitive.UserFriendlyExceptionStreamModule
import se.gustavkarlsson.skylight.android.dagger.modules.replaceable.*
import javax.inject.Singleton

@Component(modules = arrayOf(
		LatestAuroraReportCacheModule::class,
		EvaluationModule::class,
		AuroraReportModule::class,
		ClockModule::class,
		LatestAuroraReportStreamModule::class,
		UserFriendlyExceptionStreamModule::class,
		BackgroundUpdateTimeoutModule::class,
		ForegroundUpdateTimeoutModule::class,
		UpdateSchedulerModule::class
))
@Singleton
interface ApplicationComponent {
	fun getAuroraReportProvider(): AuroraReportProvider
	fun getUpdateJob(): UpdateJob
	fun getUpdateScheduler(): UpdateScheduler
	fun getClock(): Clock

    fun getMainActivityComponent(activityModule: ActivityModule): MainActivityComponent
	fun getSettingsActivityComponent(): SettingsActivityComponent
}
