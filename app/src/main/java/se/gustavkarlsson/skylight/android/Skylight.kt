package se.gustavkarlsson.skylight.android

import android.app.Application
import se.gustavkarlsson.skylight.android.initializers.initDagger
import se.gustavkarlsson.skylight.android.initializers.initDarkMode
import se.gustavkarlsson.skylight.android.initializers.initLogging
import se.gustavkarlsson.skylight.android.initializers.initRxJavaErrorHandling
import se.gustavkarlsson.skylight.android.initializers.initStrictMode
import se.gustavkarlsson.skylight.android.initializers.initThreeThen

@Suppress("unused")
internal class Skylight : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
        initStrictMode()
        initDarkMode()
        initThreeThen()
        initRxJavaErrorHandling()
        initDagger()
    }
}
