package se.gustavkarlsson.skylight.android.lib.permissions

import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Completable
import io.reactivex.functions.Consumer
import timber.log.Timber

internal class RxPermissionRequester(
    private val permissionKeys: List<String>,
    private val accessChangeConsumer: Consumer<Access>
) : PermissionRequester {

    override fun request(fragment: Fragment): Completable {
        val rxPermissions = RxPermissions(fragment)
            .apply { setLogging(BuildConfig.DEBUG) }

        return rxPermissions.requestEach(*permissionKeys.toTypedArray())
            .doOnNext {
                when {
                    it.granted -> {
                        Timber.i("Permission is granted")
                        accessChangeConsumer.accept(Access.Granted)
                    }
                    it.shouldShowRequestPermissionRationale -> {
                        Timber.i("Permission is denied")
                        accessChangeConsumer.accept(Access.Denied)
                    }
                    else -> {
                        Timber.i("Permission is denied forever")
                        accessChangeConsumer.accept(Access.DeniedForever)
                    }
                }
            }
            .ignoreElements()
    }
}