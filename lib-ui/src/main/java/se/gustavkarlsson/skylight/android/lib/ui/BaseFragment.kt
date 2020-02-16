package se.gustavkarlsson.skylight.android.lib.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.get
import se.gustavkarlsson.skylight.android.lib.navigation.Navigator
import se.gustavkarlsson.skylight.android.services.Analytics

abstract class BaseFragment : Fragment() {

	final override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? = inflater.inflate(layoutId, container, false)

	override fun onViewStateRestored(savedInstanceState: Bundle?) {
		super.onViewStateRestored(savedInstanceState)
		initView()
		bindData()
		toolbar?.let(::setupBackNavigation)
	}

	private fun setupBackNavigation(toolbar: Toolbar) {
		val navigator = get<Navigator>()
		if (navigator.backStackSize > 1) {
			toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
			toolbar.setNavigationOnClickListener { navigator.pop() }
		}
	}

	override fun onStart() {
		super.onStart()
		get<Analytics>().logScreen(requireActivity(), this::class.java.simpleName)
	}

	@get:LayoutRes
	protected abstract val layoutId: Int

	protected open val toolbar: Toolbar? = null

	protected open fun initView() = Unit

	protected open fun bindData() = Unit
}
