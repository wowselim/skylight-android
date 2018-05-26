package se.gustavkarlsson.skylight.android.gui.activities.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.visibility
import com.jakewharton.rxbinding2.widget.text
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import se.gustavkarlsson.skylight.android.R
import se.gustavkarlsson.skylight.android.appComponent
import se.gustavkarlsson.skylight.android.entities.Chance
import se.gustavkarlsson.skylight.android.extensions.indefiniteErrorSnackbar
import se.gustavkarlsson.skylight.android.gui.activities.AuroraRequirementsCheckingActivity
import se.gustavkarlsson.skylight.android.gui.activities.settings.SettingsActivity
import se.gustavkarlsson.skylight.android.gui.views.AuroraFactorView
import se.gustavkarlsson.skylight.android.services.Analytics
import timber.log.Timber


class MainActivity : AuroraRequirementsCheckingActivity(), LifecycleObserver {

	init {
	    lifecycle.addObserver(this)
	}

	private var snackbar: Snackbar? = null

	private var dialog: DialogInterface? = null

	private val viewModel: MainViewModel by lazy {
		appComponent.mainViewModel(this)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		ensureRequirementsMet()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.menu_main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.action_settings -> {
				startActivity<SettingsActivity>(); true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onRequirementsMet() = Unit

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	private fun bindData() {
		swipeRefreshLayout.refreshes()
			.doOnNext {
				Timber.i("Triggering refresh")
				Analytics.logManualRefresh()
			}
			.autoDisposable(scope())
			.subscribe(viewModel.swipedToRefresh)

		viewModel.locationName
			.doOnNext { Timber.d("Updating locationName view: %s", it) }
			.autoDisposable(scope())
			.subscribe(supportActionBar!!::setTitle)

		viewModel.isRefreshing
			.doOnNext { Timber.i("Refreshing: $it") }
			.autoDisposable(scope())
			.subscribe(swipeRefreshLayout::setRefreshing)

		viewModel.errorMessages
			.doOnNext { Timber.d("Showing error message") }
			.autoDisposable(scope())
			.subscribe { toast(it) }

		viewModel.connectivityMessages
			.autoDisposable(scope())
			.subscribe {
				snackbar?.run {
					Timber.d("Hiding connectivity message")
					dismiss()
				}
				it.ifPresent {
					Timber.d("Showing connectivity message: %s", it)
					snackbar = indefiniteErrorSnackbar(coordinatorLayout, it).apply { show() }
				}
			}

		viewModel.chanceLevel
			.doOnNext { Timber.d("Updating chanceLevel view: %s", it) }
			.autoDisposable(scope())
			.subscribe(chance.text())

		viewModel.timeSinceUpdate
			.doOnNext { Timber.d("Updating timeSinceUpdate view: %s", it) }
			.autoDisposable(scope())
			.subscribe(timeSinceUpdate.text())

		viewModel.timeSinceUpdateVisibility
			.doOnNext { Timber.d("Updating timeSinceUpdate weather: %s", it) }
			.autoDisposable(scope())
			.subscribe(timeSinceUpdate.visibility())

		viewModel.showDialog
			.doOnNext { Timber.d("Showing dialog: %s", it) }
			.autoDisposable(scope())
			.subscribe {
				dialog?.dismiss()
				dialog = alert {
					iconResource = R.drawable.info_white_24dp
					titleResource = it.titleResource
					messageResource = it.messageResource
					okButton { viewModel.hideDialogClicked.accept(Unit) }
					onCancelled { viewModel.hideDialogClicked.accept(Unit) }
				}.show()
			}

		viewModel.hideDialog
			.doOnNext { Timber.d("Hiding dialog") }
			.autoDisposable(scope())
			.subscribe { dialog?.dismiss() }

		bindFactor(
			viewModel.darknessValue, viewModel.darknessChance, darkness,
			viewModel.darknessFactorClicked,
			"darkness"
		)
		bindFactor(
			viewModel.geomagLocationValue, viewModel.geomagLocationChance, geomagLocation,
			viewModel.geomagLocationFactorClicked,
			"geomagLocation"
		)
		bindFactor(
			viewModel.kpIndexValue, viewModel.kpIndexChance, kpIndex,
			viewModel.kpIndexFactorClicked,
			"kpIndex"
		)
		bindFactor(
			viewModel.weatherValue, viewModel.weatherChance, weather,
			viewModel.weatherFactorClicked,
			"weather"
		)
	}

	private fun bindFactor(
		values: Observable<CharSequence>,
		chances: Observable<Chance>,
		view: AuroraFactorView,
		clickConsumer: Consumer<Unit>,
		factorDebugName: String
	) {
		values
			.doOnNext { Timber.d("Updating %s value view: %s", factorDebugName, it) }
			.autoDisposable(scope())
			.subscribe { view.value = it }

		chances
			.doOnNext { Timber.d("Updating %s chance view: %s", factorDebugName, it) }
			.autoDisposable(scope())
			.subscribe { view.chance = it }

		view.clicks()
			.autoDisposable(scope())
			.subscribe(clickConsumer)
	}

	override fun onDestroy() {
		super.onDestroy()
		dialog?.dismiss()
	}
}
