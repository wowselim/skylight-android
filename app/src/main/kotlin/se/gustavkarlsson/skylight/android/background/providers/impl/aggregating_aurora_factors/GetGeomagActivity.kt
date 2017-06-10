package se.gustavkarlsson.skylight.android.background.providers.impl.aggregating_aurora_factors

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import se.gustavkarlsson.skylight.android.background.providers.GeomagActivityProvider
import se.gustavkarlsson.skylight.android.models.factors.GeomagActivity
import java.util.concurrent.Callable
import java.util.concurrent.TimeoutException

class GetGeomagActivity(
		private val provider: GeomagActivityProvider
) : ErrorHandlingTask<GeomagActivity>, AnkoLogger {

    override val callable: Callable<GeomagActivity>
        get() = Callable { this.call() }

    private fun call(): GeomagActivity {
        info("Getting geomagnetic activity...")
        val geomagActivity = provider.getGeomagActivity()
        debug("Geomagnetic activity is: $geomagActivity")
        return geomagActivity
    }

    override fun handleInterruptedException(e: InterruptedException): GeomagActivity {
        return GeomagActivity()
    }

    override fun handleThrowable(e: Throwable): GeomagActivity {
        return GeomagActivity()
    }

    override fun handleTimeoutException(e: TimeoutException): GeomagActivity {
        return GeomagActivity()
    }
}