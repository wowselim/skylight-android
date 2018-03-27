package se.gustavkarlsson.skylight.android.services_impl.providers

import dagger.Reusable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import se.gustavkarlsson.skylight.android.R
import se.gustavkarlsson.skylight.android.entities.KpIndex
import se.gustavkarlsson.skylight.android.services.providers.KpIndexProvider
import se.gustavkarlsson.skylight.android.services_impl.providers.kpindex.KpIndexApi
import se.gustavkarlsson.skylight.android.util.UserFriendlyException
import javax.inject.Inject

@Reusable
class RetrofittedKpIndexProvider
@Inject
constructor(
	private val api: KpIndexApi
) : KpIndexProvider, AnkoLogger {

	override fun get(): Single<KpIndex> {
		return api.get()
			.subscribeOn(Schedulers.io())
			.onErrorResumeNext {
				Single.error(UserFriendlyException(R.string.error_could_not_determine_kp_index, it))
			}.map {
				KpIndex(it.value.toDouble())
			}
	}
}
