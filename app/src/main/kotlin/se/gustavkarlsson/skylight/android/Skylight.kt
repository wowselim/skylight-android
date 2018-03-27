package se.gustavkarlsson.skylight.android

import android.support.multidex.MultiDexApplication
import com.evernote.android.job.JobManager
import com.jakewharton.threetenabp.AndroidThreeTen
import se.gustavkarlsson.skylight.android.dagger.components.ApplicationComponent
import se.gustavkarlsson.skylight.android.dagger.components.DaggerApplicationComponent
import se.gustavkarlsson.skylight.android.dagger.modules.ContextModule
import se.gustavkarlsson.skylight.android.services_impl.scheduling.UpdateJob

class Skylight : MultiDexApplication() {

	lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
		instance = this
        component = DaggerApplicationComponent.builder()
                .contextModule(ContextModule(this))
                .build()
		bootstrap()
		setupNotifications()
    }

	private fun bootstrap() {
		AndroidThreeTen.init(this)
		initJobManager()
	}

	private fun initJobManager() {
		JobManager.create(this).run {
			addJobCreator { tag ->
				when (tag) {
					UpdateJob.UPDATE_JOB_TAG -> component.getUpdateJob()
					else -> null
				}
			}
		}
	}

	private fun setupNotifications() {
		val settings = component.getSettings()
		val scheduler = component.getScheduler()
		settings.notificationsEnabledChanges
			.subscribe { enabled ->
				if (enabled) {
					scheduler.schedule()
				} else {
					scheduler.unschedule()
				}
			}
	}

	companion object {
		lateinit var instance: Skylight
	}

}
