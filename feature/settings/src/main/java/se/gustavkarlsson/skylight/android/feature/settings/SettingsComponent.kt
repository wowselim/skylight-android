package se.gustavkarlsson.skylight.android.feature.settings

import dagger.Component
import se.gustavkarlsson.skylight.android.core.AppComponent
import se.gustavkarlsson.skylight.android.lib.settings.SettingsComponent as LibSettingsComponent

@Component(
    dependencies = [
        AppComponent::class,
        LibSettingsComponent::class
    ]
)
internal interface SettingsComponent {
    fun viewModel(): SettingsViewModel

    companion object {
        fun build(): SettingsComponent =
            DaggerSettingsComponent.builder()
                .appComponent(AppComponent.instance)
                .settingsComponent(LibSettingsComponent.instance)
                .build()
    }
}
