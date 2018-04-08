package se.gustavkarlsson.skylight.android.di.modules

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import se.gustavkarlsson.skylight.android.gui.activities.main.MainViewModel
import se.gustavkarlsson.skylight.android.gui.activities.main.fragments.aurora_chance.AuroraChanceViewModel
import se.gustavkarlsson.skylight.android.gui.activities.main.fragments.aurora_factors.DarknessViewModel
import se.gustavkarlsson.skylight.android.gui.activities.main.fragments.aurora_factors.GeomagLocationViewModel
import se.gustavkarlsson.skylight.android.gui.activities.main.fragments.aurora_factors.KpIndexViewModel
import se.gustavkarlsson.skylight.android.gui.activities.main.fragments.aurora_factors.VisibilityViewModel

interface ViewModelsModule {
	fun auroraChanceViewModel(fragment: Fragment): AuroraChanceViewModel
	fun darknessViewModel(fragment: Fragment): DarknessViewModel
	fun geomagLocationViewModel(fragment: Fragment): GeomagLocationViewModel
	fun kpIndexViewModel(fragment: Fragment): KpIndexViewModel
	fun visibilityViewModel(fragment: Fragment): VisibilityViewModel
	fun mainViewModel(activity: FragmentActivity): MainViewModel
}
