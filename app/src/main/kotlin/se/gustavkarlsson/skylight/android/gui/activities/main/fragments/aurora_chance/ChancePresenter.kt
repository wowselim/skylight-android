package se.gustavkarlsson.skylight.android.gui.activities.main.fragments.aurora_chance

import android.widget.TextView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import se.gustavkarlsson.skylight.android.services.Presenter
import se.gustavkarlsson.skylight.android.services.evaluation.Chance
import se.gustavkarlsson.skylight.android.services.evaluation.ChanceLevel

class ChancePresenter(
		private val chanceTextView: TextView
) : Presenter<Chance> {

    override fun present(value: Chance) {
        val chanceLevel = ChanceLevel.fromChance(value)
		async(UI) {
			chanceTextView.setText(chanceLevel.resourceId)
		}
    }
}
