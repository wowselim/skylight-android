package se.gustavkarlsson.skylight.android.services_impl.streamables

import io.reactivex.Flowable
import org.threeten.bp.Duration
import se.gustavkarlsson.skylight.android.entities.KpIndex
import se.gustavkarlsson.skylight.android.services.Streamable
import se.gustavkarlsson.skylight.android.services.providers.KpIndexProvider
import java.util.concurrent.TimeUnit

class KpIndexStreamable(
	kpIndexProvider: KpIndexProvider,
	pollingInterval: Duration
) : Streamable<KpIndex> {
	override val stream: Flowable<KpIndex> = kpIndexProvider.get()
		.repeatWhen { it.delay(pollingInterval.toMillis(), TimeUnit.MILLISECONDS) }
		.replay(1)
		.refCount()
}
