package se.gustavkarlsson.skylight.android.dagger.modules

import android.content.Context
import android.view.View
import android.widget.TextView
import dagger.Module
import dagger.Provides
import se.gustavkarlsson.skylight.android.R
import se.gustavkarlsson.skylight.android.dagger.FRAGMENT_ROOT_NAME
import se.gustavkarlsson.skylight.android.dagger.scopes.FragmentScope
import se.gustavkarlsson.skylight.android.services.Presenter
import se.gustavkarlsson.skylight.android.services_impl.presenters.LocationTextViewPresenter
import javax.inject.Named

@Module
class LocationPresenterModule {

    @Provides
    @FragmentScope
    fun provideLocationPresenter(
			@Named(FRAGMENT_ROOT_NAME) rootView: View,
			context: Context
	): Presenter<String?> {
        val locationView = rootView.findViewById<TextView>(R.id.location)
		val defaultName = context.getString(R.string.your_location)
        return LocationTextViewPresenter(locationView, defaultName)
    }
}
