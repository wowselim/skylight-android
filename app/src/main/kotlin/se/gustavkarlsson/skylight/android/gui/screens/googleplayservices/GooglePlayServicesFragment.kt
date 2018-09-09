package se.gustavkarlsson.skylight.android.gui.screens.googleplayservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.common.GoogleApiAvailability
import com.jakewharton.rxbinding2.view.clicks
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable
import kotlinx.android.synthetic.main.fragment_google_play_services.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import se.gustavkarlsson.skylight.android.R
import se.gustavkarlsson.skylight.android.extensions.appCompatActivity

class GooglePlayServicesFragment : Fragment(), LifecycleObserver {

	private val viewModel: GooglePlayServicesViewModel by viewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		appCompatActivity!!.supportActionBar!!.hide()
		lifecycle.addObserver(this)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? = inflater.inflate(R.layout.fragment_google_play_services, container, false)


	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	private fun bindData() {
		installButton.clicks()
			.autoDisposable(scope(Lifecycle.Event.ON_STOP))
			.subscribe {
				// TODO Wait for https://github.com/ashdavies/rx-tasks/issues/21
				GoogleApiAvailability.getInstance()
					.makeGooglePlayServicesAvailable(requireActivity())
			}
	}

	override fun onDestroy() {
		super.onDestroy()
		lifecycle.removeObserver(this)
	}
}