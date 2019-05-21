package se.gustavkarlsson.skylight.android.gui.screens.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import se.gustavkarlsson.skylight.android.R

class InnerSettingsFragment : PreferenceFragmentCompat() {

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		addPreferencesFromResource(R.xml.preferences)
	}
}