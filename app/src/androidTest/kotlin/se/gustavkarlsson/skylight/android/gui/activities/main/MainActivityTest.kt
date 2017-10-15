package se.gustavkarlsson.skylight.android.gui.activities.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import se.gustavkarlsson.skylight.android.R
import se.gustavkarlsson.skylight.android.Skylight
import se.gustavkarlsson.skylight.android.dagger.components.DaggerTestApplicationComponent
import se.gustavkarlsson.skylight.android.dagger.modules.ContextModule
import se.gustavkarlsson.skylight.android.dagger.modules.TestSharedPreferencesModule
import se.gustavkarlsson.skylight.android.test.ApplicationComponentActivityTestRule
import se.gustavkarlsson.skylight.android.test.clearCache
import se.gustavkarlsson.skylight.android.test.clearSharedPreferences
import se.gustavkarlsson.skylight.android.test.initMocks

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Rule
	@JvmField
    var testRule = ApplicationComponentActivityTestRule(MainActivity::class, false, false) {
		DaggerTestApplicationComponent.builder()
			.contextModule(ContextModule(Skylight.instance))
			.testSharedPreferencesModule(TestSharedPreferencesModule())
			.build()
	}

	@Before
	fun setUp() {
		initMocks()
		clearCache()
		clearSharedPreferences()
		testRule.launchActivity()
	}

	@Test
    fun auroraFactorsFragmentShown() {
        onView(withId(R.id.auroraFactorsFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun auroraChanceFragmentShown() {
        onView(withId(R.id.auroraChanceFragment)).check(matches(isDisplayed()))
    }
}
