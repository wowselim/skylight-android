package se.gustavkarlsson.skylight.android.gui.activities.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import se.gustavkarlsson.skylight.android.R
import se.gustavkarlsson.skylight.android.Skylight
import se.gustavkarlsson.skylight.android.actions.GetNewAuroraReport
import se.gustavkarlsson.skylight.android.actions.PresentingErrors
import se.gustavkarlsson.skylight.android.actions.SetUpdateSchedule
import se.gustavkarlsson.skylight.android.dagger.components.MainActivityComponent
import se.gustavkarlsson.skylight.android.dagger.modules.ActivityModule
import se.gustavkarlsson.skylight.android.extensions.observe
import se.gustavkarlsson.skylight.android.gui.activities.AuroraRequirementsCheckingActivity
import se.gustavkarlsson.skylight.android.gui.activities.settings.SettingsActivity
import se.gustavkarlsson.skylight.android.gui.viewmodels.AuroraReportViewModel
import javax.inject.Inject

class MainActivity : AuroraRequirementsCheckingActivity() {

	@Inject
	lateinit var swipeToRefreshController: SwipeToRefreshController

	@Inject
	lateinit var presentingErrors: PresentingErrors

	@Inject
	lateinit var setUpdateSchedule: SetUpdateSchedule

	@Inject
	lateinit var auroraReportViewModel: AuroraReportViewModel

	@Inject
	lateinit var getNewAuroraReport: GetNewAuroraReport

	lateinit var component: MainActivityComponent
		private set

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		component = Skylight.instance.component.getMainActivityComponent(ActivityModule(this))
		setContentView(R.layout.activity_main)
		component.inject(this)
		bindData()
	}

	private fun bindData() {
		auroraReportViewModel.locationName.observe(this) {
			it?.let {
				supportActionBar!!.title = it
			}
		}
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

	public override fun onStart() {
		super.onStart()
		presentingErrors.start()
		swipeToRefreshController.disable()
		ensureRequirementsMet()
	}

	override fun onRequirementsMet() {
		setUpdateSchedule()
		swipeToRefreshController.enable()
		getNewAuroraReport() // TODO Call only when necessary
			.subscribeOn(Schedulers.io())
			.onErrorComplete()
			.subscribe()
	}

	override fun onStop() {
		super.onStop()
		presentingErrors.stop()
	}
}
