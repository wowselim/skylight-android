package se.gustavkarlsson.skylight.android.lib.navigationsetup

import com.zhuinden.simplestack.Backstack as SSBackstack
import se.gustavkarlsson.skylight.android.navigation.Backstack
import se.gustavkarlsson.skylight.android.navigation.NavigationOverride
import se.gustavkarlsson.skylight.android.navigation.Navigator
import se.gustavkarlsson.skylight.android.navigation.Screen
import timber.log.Timber

internal class SimpleStackNavigator(
    private val simpleStackBackstack: SSBackstack,
    private val backstackOverride: NavigationOverride,
    private val directionsCalculator: DirectionsCalculator,
    private val onBackstackEmpty: () -> Unit
) : Navigator {

    override val backstack: Backstack get() = simpleStackBackstack.getHistory()

    override fun setBackstack(backstack: Backstack) = changeBackstack {
        Timber.i("Setting backstack to $backstack")
        backstack
    }

    override fun goTo(screen: Screen) = changeBackstack { stack ->
        Timber.i("Going to $screen")
        stack + screen
    }

    override fun closeScreenAndGoTo(screen: Screen) = changeBackstack { stack ->
        Timber.i("Closing screen and going to $screen")
        val remaining = stack.dropLast(1)
        remaining + screen
    }

    override fun closeScopeAndGoTo(scope: String, screen: Screen) = changeBackstack { stack ->
        Timber.i("Closing scope '$scope' and going to $screen")
        val remaining = stack.takeWhile { it.scopeStart != scope }
        remaining + screen
    }

    override fun closeScreen() = changeBackstack { stack ->
        Timber.i("Closing screen")
        stack.dropLast(1)
    }

    override fun closeScope(scope: String) = changeBackstack { stack ->
        Timber.i("Closing scope '$scope'")
        stack.takeWhile { it.scopeStart != scope }
    }

    private fun changeBackstack(change: (stack: Backstack) -> Backstack) {
        val oldBackstack = simpleStackBackstack.getHistory<Screen>()
        val changedBackstack = change(oldBackstack)
        val newBackstack = backstackOverride.override(changedBackstack) ?: changedBackstack
        if (newBackstack.isEmpty()) {
            Timber.i("Backstack is empty")
            onBackstackEmpty()
        } else {
            val direction = directionsCalculator.getDirection(oldBackstack, newBackstack)
            simpleStackBackstack.setHistory(newBackstack, direction)
        }
    }
}
