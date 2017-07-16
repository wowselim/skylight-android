package se.gustavkarlsson.skylight.android.background

import android.content.Context
import dagger.Reusable
import io.reactivex.subjects.Subject
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import se.gustavkarlsson.skylight.android.R
import se.gustavkarlsson.skylight.android.Skylight.Companion.applicationComponent
import se.gustavkarlsson.skylight.android.entities.AuroraReport
import se.gustavkarlsson.skylight.android.notifications.NotificationHandler
import se.gustavkarlsson.skylight.android.util.UserFriendlyException
import javax.inject.Inject

@Reusable
class Updater
@Inject
constructor(
	private val context: Context,
	private val notificationHandler: NotificationHandler,
	private val latestAuroraReportSubject: Subject<AuroraReport>,
	private val userFriendlyExceptionSubject: Subject<UserFriendlyException>
) : AnkoLogger {

	fun update(): Boolean {
		val provider = applicationComponent.getAuroraReportProvider()
		try {
			val report = provider.get()
			latestAuroraReportSubject.onNext(report)
			notificationHandler.handle(report)
			return true
		} catch (e: UserFriendlyException) {
			val errorMessage = context.getString(e.stringResourceId)
			error("A user friendly exception occurred: $errorMessage", e)
			userFriendlyExceptionSubject.onNext(e)
			return false
		} catch (e: Exception) {
			error("An unexpected error occurred", e)
			userFriendlyExceptionSubject.onNext(UserFriendlyException(R.string.error_unknown_update_error, e))
			return false
		}
	}
}
