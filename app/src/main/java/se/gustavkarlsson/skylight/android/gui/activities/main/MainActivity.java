package se.gustavkarlsson.skylight.android.gui.activities.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.threeten.bp.Clock;
import org.threeten.bp.Duration;

import javax.inject.Inject;
import javax.inject.Named;

import se.gustavkarlsson.skylight.android.R;
import se.gustavkarlsson.skylight.android.background.Updater;
import se.gustavkarlsson.skylight.android.dagger.components.MainActivityComponent;
import se.gustavkarlsson.skylight.android.dagger.modules.definitive.ActivityModule;
import se.gustavkarlsson.skylight.android.evaluation.ChanceEvaluator;
import se.gustavkarlsson.skylight.android.gui.activities.AuroraRequirementsCheckingActivity;
import se.gustavkarlsson.skylight.android.gui.activities.settings.SettingsActivity;
import se.gustavkarlsson.skylight.android.models.AuroraReport;
import se.gustavkarlsson.skylight.android.observers.ObservableData;

import static se.gustavkarlsson.skylight.android.Skylight.getApplicationComponent;
import static se.gustavkarlsson.skylight.android.background.UpdateJob.BACKGROUND_UPDATE_TIMEOUT;
import static se.gustavkarlsson.skylight.android.background.Updater.RESPONSE_UPDATE_ERROR;
import static se.gustavkarlsson.skylight.android.background.Updater.RESPONSE_UPDATE_ERROR_EXTRA_MESSAGE;
import static se.gustavkarlsson.skylight.android.dagger.modules.replaceable.LatestAuroraReportCacheModule.LATEST_NAME;

public class MainActivity extends AuroraRequirementsCheckingActivity {
	private static final String TAG = MainActivity.class.getSimpleName();

	private static final Duration REPORT_LIFETIME = Duration.ofMinutes(15);

	@Inject
	Updater updater;

	@Inject
	LocalBroadcastManager broadcastManager;

	@Inject
	Clock clock;

	@Inject
	SwipeToRefreshPresenter swipeToRefreshPresenter;

	@Inject
	@Named(LATEST_NAME)
	ObservableData<AuroraReport> latestAuroraReport;

	@Inject
	ChanceEvaluator<AuroraReport> auroraChanceEvaluator;

	private MainActivityComponent component;

	private BroadcastReceiver broadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		component = getApplicationComponent().getMainActivityComponent(new ActivityModule(this));
		setContentView(R.layout.activity_main);
		component.inject(this);

		broadcastReceiver = createBroadcastReceiver();
		// TODO Keep daggerifying
	}

	private BroadcastReceiver createBroadcastReceiver() {
		return new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (RESPONSE_UPDATE_ERROR.equals(action)) {
					String message = intent.getStringExtra(RESPONSE_UPDATE_ERROR_EXTRA_MESSAGE);
					Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
				}
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.v(TAG, "onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.v(TAG, "onOptionsItemSelected");
		switch (item.getItemId()) {
			case R.id.action_settings: {
				Intent intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		Log.v(TAG, "onStart");
		super.onStart();
		broadcastManager.registerReceiver(broadcastReceiver, new IntentFilter(RESPONSE_UPDATE_ERROR));
		swipeToRefreshPresenter.disable();
		ensureRequirementsMet();
	}

	@Override
	protected void onRequirementsMet() {
		swipeToRefreshPresenter.enable();
		AuroraReport latestReport = latestAuroraReport.getData();
		if (needsUpdate(latestReport)) {
			updateInBackground();
		}
	}

	private boolean needsUpdate(AuroraReport report) {
		boolean hasExpired = clock.millis() - report.getTimestampMillis() > REPORT_LIFETIME.toMillis();
		boolean isUnknown = !auroraChanceEvaluator.evaluate(report).isKnown();
		return hasExpired || isUnknown;
	}

	private void updateInBackground() {
		AsyncTask.execute(() -> updater.update(BACKGROUND_UPDATE_TIMEOUT.toMillis()));
	}

	@Override
	protected void onStop() {
		Log.v(TAG, "onStop");
		super.onStop();
		broadcastManager.unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onDestroy() {
		Log.v(TAG, "onDestroy");
		updater = null;
		broadcastManager = null;
		swipeToRefreshPresenter = null;
		broadcastReceiver = null;
		component = null;
		latestAuroraReport = null;
		super.onDestroy();
	}

	public MainActivityComponent getComponent() {
		return component;
	}
}
