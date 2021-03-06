package se.gustavkarlsson.skylight.android.lib.ui

import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar
import de.halfbit.edgetoedge.EdgeToEdgeBuilder
import de.halfbit.edgetoedge.edgeToEdge
import se.gustavkarlsson.skylight.android.lib.analytics.AnalyticsComponent
import se.gustavkarlsson.skylight.android.lib.navigation.navigator

abstract class ScreenFragment : BaseFragment() {

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        edgeToEdge(setupEdgeToEdge())
        toolbar?.let(::setupBackNavigation)
    }

    private fun setupBackNavigation(toolbar: MaterialToolbar) {
        if (navigator.backstack.size > 1) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { navigator.closeScreen() }
        }
    }

    override fun onStart() {
        super.onStart()
        AnalyticsComponent.instance.analytics().logScreen(requireActivity(), this::class.java.simpleName)
    }

    protected open val toolbar: MaterialToolbar? = null

    protected abstract fun setupEdgeToEdge(): EdgeToEdgeBuilder.() -> Unit
}
