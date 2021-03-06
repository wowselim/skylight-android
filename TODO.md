## Soon
* Bug: If location is granted (while using app), then go to add place, and go back. Banner shows up again.
* Migrate to coroutines
* Rework location inputs to "ApproximateLocation" so store can cache ressults from nearby locations
* Readme
* Simpler configuration of required keys (move to root? fail if not present?)
* Retire ThreeTenABP
* Add Hyperion
* Move some libs to coroutines
* Android 11: https://developer.android.com/preview/privacy/permissions#one-time
* Android 11: https://developer.android.com/preview/privacy/location#background-location
* Use cases instead of repositories?
* Show banner when no connectivity (check for flight mode?)
* Look into adding screen with open source licenses: https://developers.google.com/android/guides/opensource
* Add prettier detail screens
* Improve feature graphics

## Some day
* Oversee text style usage (also use TextAppearance instead of style in XML)
* Use Store lib?
* Make testing use other settings
* Add more tests
* Add develop settings
* Add instrumentation testing to CI
* Consider refresh after changing debug options
* Separate debug menu instead of settings page
* Firebase performance monitoring
* Add manual clearing of "notification sent" in develop mode
* Add more testing options (individual overrides for example)
* Look into country changes while app running (Locale etc)
* Additional analytics(Failed remote calls, Notifications on/off, Manually started app, Started app from notification, denied location permission, no Google Play Services installed, first start)
* Add performance traces
* Integrate code coverage (Coveralls or Codecov)
* Look into using Spek (problems with junit5 and android)
* Firebase remote config, or move more stuff to backend?

## Never?
* Rename chance to score?
* Slices
* Widget
* Instant app
* Wear OS App, Tablet, Chrome OS, Leanback apps
* Allow reporting of false negatives/positives
