package se.gustavkarlsson.aurora_notifier.android.gui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import se.gustavkarlsson.aurora_notifier.android.R;
import se.gustavkarlsson.aurora_notifier.android.background.UpdateScheduler;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static se.gustavkarlsson.aurora_notifier.android.AuroraNotifier.getApplicationComponent;


public abstract class AuroraRequirementsCheckingActivity extends AppCompatActivity {
	private static final String TAG = AuroraRequirementsCheckingActivity.class.getSimpleName();

	public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
	private static final int REQUEST_CODE_LOCATION_PERMISSION = 1973;

	private UpdateScheduler updateScheduler;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		updateScheduler = getApplicationComponent(this).getUpdateScheduler();
	}

	protected final void ensureRequirementsMet() {
		if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
			ensureLocationPermission();
		} else {
			showGooglePlayServicesNotAvailable();
		}
	}

	private void showGooglePlayServicesNotAvailable() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.error_google_play_services_is_not_available_title)
				.setMessage(R.string.error_google_play_services_is_not_available_desc)
				.setPositiveButton(R.string.exit, (dialog, which) -> System.exit(1))
				.setCancelable(false)
				.show();
	}

	private void showLocationPermissionRequiredDialog() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(getString(R.string.location_permission_required_title))
				.setMessage(R.string.location_permission_required_desc)
				.setPositiveButton(android.R.string.yes, (dialog, which) -> showLocationPermissionRequest())
				.setNegativeButton(R.string.exit, (dialog, which) -> System.exit(2))
				.setCancelable(false)
				.show();
	}

	private void showLocationPermissionDeniedDialog() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(getString(R.string.error_location_permission_denied_title))
				.setMessage(R.string.error_location_permission_denied_desc)
				.setPositiveButton(R.string.exit, (dialog, which) -> System.exit(3))
				.setCancelable(false)
				.show();
	}

	private void ensureLocationPermission() {
		boolean hasPermission = ContextCompat.checkSelfPermission(this, LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED;
		if (!hasPermission) {
			showLocationPermissionRequest();
		} else {
			onRequirementsMet();
		}
	}

	private void showLocationPermissionRequest() {
		ActivityCompat.requestPermissions(this, new String[]{LOCATION_PERMISSION}, REQUEST_CODE_LOCATION_PERMISSION);
	}

	@Override
	public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		Log.v(TAG, "onRequestPermissionsResult");
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (REQUEST_CODE_LOCATION_PERMISSION == requestCode) {
			for (int i = 0; i < permissions.length; i++) {
				String permission = permissions[i];
				int result = grantResults[i];
				if (LOCATION_PERMISSION.equals(permission)) {
					if (PERMISSION_GRANTED != result) {
						handlePermissionDenied();
					} else {
						updateScheduler.setupBackgroundUpdates();
						onRequirementsMet();
					}
				}
			}
		}
	}

	private void handlePermissionDenied() {
		Log.i(TAG, LOCATION_PERMISSION + " permission denied");
		if (ActivityCompat.shouldShowRequestPermissionRationale(this, LOCATION_PERMISSION)) {
			Log.i(TAG, "Showing rationale to user for another chance");
			showLocationPermissionRequiredDialog();
		} else {
			Log.i(TAG, "Showing permission denied dialog and exiting");
			showLocationPermissionDeniedDialog();
		}
	}

	protected void onRequirementsMet() {
	}
}
