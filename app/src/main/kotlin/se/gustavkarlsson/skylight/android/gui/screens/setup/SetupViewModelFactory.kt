package se.gustavkarlsson.skylight.android.gui.screens.setup

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import se.gustavkarlsson.skylight.android.krate.SkylightStore

class SetupViewModelFactory(
	private val store: SkylightStore
) : ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		require(modelClass == CLASS) { "Unsupported ViewModel class: $modelClass, expected: $CLASS" }
		return SetupViewModel(store) as T
	}

	companion object {
		private val CLASS = SetupViewModel::class.java
	}
}
