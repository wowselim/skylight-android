package se.gustavkarlsson.skylight.android.weather

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import org.threeten.bp.Duration
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.gustavkarlsson.skylight.android.extensions.minutes
import se.gustavkarlsson.skylight.android.extensions.seconds
import se.gustavkarlsson.skylight.android.services.providers.WeatherProvider
import java.util.concurrent.TimeUnit

val weatherModule = module {

	single<OpenWeatherMapApi> {
		val timeout = 30.seconds
		Retrofit.Builder()
			.client(
				OkHttpClient.Builder()
					.connectTimeout(timeout)
					.readTimeout(timeout)
					.writeTimeout(timeout)
					.build()
			)
			.baseUrl("https://api.openweathermap.org/data/2.5/")
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
			.build()
			.create(OpenWeatherMapApi::class.java)
	}

	single<WeatherProvider> {
		RetrofittedOpenWeatherMapWeatherProvider(
			api = get(),
			appId = BuildConfig.OPENWEATHERMAP_API_KEY,
			time = get(),
			retryDelay = 15.seconds,
			pollingInterval = 15.minutes
		)
	}

}

// TODO Extract this and OkHttpClient to separate module
private fun OkHttpClient.Builder.connectTimeout(timeout: Duration): OkHttpClient.Builder =
	this.connectTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)

private fun OkHttpClient.Builder.readTimeout(timeout: Duration): OkHttpClient.Builder =
	this.readTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)

private fun OkHttpClient.Builder.writeTimeout(timeout: Duration): OkHttpClient.Builder =
	this.writeTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
