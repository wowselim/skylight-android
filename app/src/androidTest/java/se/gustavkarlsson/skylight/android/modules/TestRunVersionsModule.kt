package se.gustavkarlsson.skylight.android.modules

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import org.koin.dsl.module.module
import se.gustavkarlsson.skylight.android.feature.intro.RunVersionManager

val testRunVersionsModule = module {

	single {
		TestRunVersionManager(false)
	}

	single<RunVersionManager>(override = true) {
		get<TestRunVersionManager>()
	}

}

class TestRunVersionManager(
	initialIsFirstRun: Boolean
) : RunVersionManager {

	val isFirstRunRelay = BehaviorRelay.createDefault(initialIsFirstRun)

	override val isFirstRun: Observable<Boolean> =
		isFirstRunRelay.toObservable(BackpressureStrategy.LATEST)

	override fun signalFirstRunCompleted() = isFirstRunRelay.accept(false)

}
