package se.gustavkarlsson.skylight.android.lib.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import se.gustavkarlsson.skylight.android.core.logging.logDebug

internal class AndroidPermissionChecker(
    private val permissionKey: String,
    private val context: Context,
    private val accessRelay: BehaviorRelay<Access>
) : PermissionChecker {

    override val access: Observable<Access> =
        accessRelay
            .distinctUntilChanged()
            .doOnSubscribe { refresh() }

    override fun refresh() {
        val systemPermission = checkSystemPermission()
        if (systemPermission == Access.Denied && accessRelay.value == Access.DeniedForever) {
            logDebug { "Won't change from ${Access.DeniedForever} to ${Access.Denied}" }
            return
        }
        accessRelay.accept(systemPermission)
    }

    private fun checkSystemPermission(): Access {
        val result = ContextCompat.checkSelfPermission(context, permissionKey)
        val access = if (result == PackageManager.PERMISSION_GRANTED)
            Access.Granted
        else
            Access.Denied
        logDebug { "$permissionKey = $access" }
        return access
    }
}
