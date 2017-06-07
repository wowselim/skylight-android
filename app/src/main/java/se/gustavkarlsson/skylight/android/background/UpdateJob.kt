package se.gustavkarlsson.skylight.android.background

import android.app.NotificationManager
import android.content.pm.PackageManager
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import com.evernote.android.job.Job
import com.evernote.android.job.Job.Result.FAILURE
import com.evernote.android.job.Job.Result.SUCCESS
import org.threeten.bp.Duration
import se.gustavkarlsson.skylight.android.R
import se.gustavkarlsson.skylight.android.dagger.Names.BACKGROUND_UPDATE_TIMEOUT_NAME
import se.gustavkarlsson.skylight.android.gui.activities.AuroraRequirementsCheckingActivity
import javax.inject.Inject
import javax.inject.Named

internal class UpdateJob
@Inject
constructor(
		private val notificationManager: NotificationManager,
		private val updateScheduler: UpdateScheduler,
		private val updater: Updater,
		@param:Named(BACKGROUND_UPDATE_TIMEOUT_NAME) private val timeout: Duration
) : Job() {

    override fun onRunJob(params: Job.Params): Job.Result {
        if (!hasLocationPermission()) {
            updateScheduler.cancelBackgroundUpdates()
            sendLocationPermissionMissingNotification(notificationManager)
            return FAILURE
        }
        val successful = updater.update(timeout)
        return if (successful) SUCCESS else FAILURE
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, AuroraRequirementsCheckingActivity.LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

    // TODO Add better handling
    private fun sendLocationPermissionMissingNotification(notificationManager: NotificationManager) {
        val context = context

        val notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_logo_small)
                .setContentTitle(context.getString(R.string.error_aurora_notifications_disabled_title))
                .setContentText(context.getString(R.string.error_aurora_notifications_disabled_content))
                .setCategory(NotificationCompat.CATEGORY_ERROR)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build()

        notificationManager.notify(24656, notification)
    }

    companion object {
        private val TAG = UpdateJob::class.java.simpleName
        val UPDATE_JOB_TAG = TAG + ".UPDATE_JOB"
    }
}
