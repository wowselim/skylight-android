package se.gustavkarlsson.aurora_notifier.android.dagger.modules;

import android.content.Context;
import android.location.Geocoder;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import se.gustavkarlsson.aurora_notifier.android.background.providers.AddressProvider;
import se.gustavkarlsson.aurora_notifier.android.background.providers.LocationProvider;
import se.gustavkarlsson.aurora_notifier.android.background.providers.impl.GeocoderAddressProvider;
import se.gustavkarlsson.aurora_notifier.android.background.providers.impl.GoogleLocationProvider;

@Module
public class GoogleLocationModule {
	private final Context context;

	public GoogleLocationModule(Context context) {
		this.context = context;
	}

	@Provides
	@Reusable
	LocationProvider provideLocationProvider() {
		GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
				.addApi(LocationServices.API)
				.build();
		return new GoogleLocationProvider(googleApiClient);
	}

	@Provides
	@Reusable
	AddressProvider provideAddressProvider() {
		Geocoder geocoder = new Geocoder(context);
		return new GeocoderAddressProvider(geocoder);
	}

}