package se.gustavkarlsson.skylight.android.background.providers;

import android.location.Address;

import java.util.concurrent.Future;

public interface AsyncAddressProvider {
	Future<Address> execute(double latitude, double longitude);
}