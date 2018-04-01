package se.gustavkarlsson.skylight.android.caching

import android.content.Context
import se.gustavkarlsson.skylight.android.entities.AuroraReport

class AuroraReportDualCache(context: Context, cacheId: String, defaultValue: AuroraReport) :
	SingletonCache<AuroraReport> by DualSingletonCache(
		context,
		cacheId,
		defaultValue,
		AuroraReportCacheSerializer()
	)
