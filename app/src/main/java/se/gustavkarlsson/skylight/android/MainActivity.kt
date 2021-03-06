package se.gustavkarlsson.skylight.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import se.gustavkarlsson.skylight.android.lib.navigation.Backstack
import se.gustavkarlsson.skylight.android.lib.navigation.BackstackListener
import se.gustavkarlsson.skylight.android.lib.navigation.NavigationComponent
import se.gustavkarlsson.skylight.android.lib.navigation.Navigator
import se.gustavkarlsson.skylight.android.lib.navigation.NavigatorHost
import se.gustavkarlsson.skylight.android.lib.navigation.Screen
import se.gustavkarlsson.skylight.android.lib.navigation.ScreensHost
import se.gustavkarlsson.skylight.android.lib.navigationsetup.BackButtonController
import se.gustavkarlsson.skylight.android.lib.navigationsetup.NavigationSetupComponent
import se.gustavkarlsson.skylight.android.lib.scopedservice.ScopedServiceComponent
import se.gustavkarlsson.skylight.android.lib.scopedservice.ServiceHost
import se.gustavkarlsson.skylight.android.lib.scopedservice.ServiceRegistry
import se.gustavkarlsson.skylight.android.navigation.DefaultScreens
import se.gustavkarlsson.skylight.android.navigation.animationConfig

internal class MainActivity : AppCompatActivity(), NavigatorHost, ScreensHost, ServiceHost, BackstackListener {

    override lateinit var navigator: Navigator private set

    private lateinit var backButtonController: BackButtonController

    override val serviceRegistry: ServiceRegistry =
        ScopedServiceComponent.instance.serviceRegistry()

    override val screens =
        DefaultScreens

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        val installer = NavigationSetupComponent.instance.navigationInstaller()
        val (navigator, backButtonController) = installer.install(
            this,
            R.id.fragmentContainer,
            listOf(screens.main),
            NavigationComponent.instance.navigationOverrides(),
            listOf(this),
            animationConfig
        )
        this.navigator = navigator
        this.backButtonController = backButtonController
    }

    override fun finish() {
        serviceRegistry.clear()
        super.finish()
    }

    override fun onBackstackChanged(old: Backstack, new: Backstack) {
        val tags = new.map(Screen::tag)
        serviceRegistry.onTagsChanged(tags)
    }

    override fun onBackPressed() = backButtonController.onBackPressed()
}
