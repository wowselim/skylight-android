package se.gustavkarlsson.skylight.android.feature.addplace

import com.ioki.textref.TextRef
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import se.gustavkarlsson.skylight.android.core.utils.mapNotNull
import se.gustavkarlsson.skylight.android.lib.geocoder.PlaceSuggestion
import se.gustavkarlsson.skylight.android.lib.location.Location
import se.gustavkarlsson.skylight.android.lib.places.PlacesRepository
import se.gustavkarlsson.skylight.android.lib.scopedservice.ScopedService
import javax.inject.Inject

internal class AddPlaceViewModel @Inject constructor(
    private val placesRepository: PlacesRepository,
    private val knot: AddPlaceKnot,
    val errorMessages: Observable<TextRef>
) : ScopedService {

    override fun onCleared() {
        knot.dispose()
    }

    private val navigateAwayRelay = PublishRelay.create<Unit>()
    val navigateAway: Observable<Unit> = navigateAwayRelay

    private val openSaveDialogRelay = PublishRelay.create<PlaceSuggestion>()
    val openSaveDialog: Observable<PlaceSuggestion> = openSaveDialogRelay

    fun onSearchTextChanged(newText: String) = knot.change.accept(Change.Query(newText))

    val placeSuggestions: Observable<List<SuggestionItem>> = knot.state
        .map(State::suggestions)
        .distinctUntilChanged()
        .filter { it.isNotEmpty() }
        .map { suggestions ->
            suggestions.map {
                SuggestionItem(createTitle(it), createSubtitle(it), it)
            }
        }

    fun onSuggestionClicked(suggestion: PlaceSuggestion) = openSaveDialogRelay.accept(suggestion)

    fun onSavePlaceClicked(name: String, location: Location) {
        placesRepository.add(name, location)
        navigateAwayRelay.accept(Unit)
    }

    private val resultState: Observable<ResultState> = knot.state
        .mapNotNull { state ->
            when {
                state.query.isBlank() -> ResultState.EMPTY
                state.isSearching && state.suggestions.isEmpty() -> ResultState.SEARCHING
                state.suggestions.isEmpty() -> ResultState.NO_SUGGESTIONS
                else -> ResultState.SUGGESTIONS
            }
        }
        .distinctUntilChanged()

    val isEmptyVisible: Observable<Boolean> =
        resultState.map { it == ResultState.EMPTY }

    val isSearchingVisible: Observable<Boolean> =
        resultState.map { it == ResultState.SEARCHING }

    val isNoSuggestionsVisible: Observable<Boolean> =
        resultState.map { it == ResultState.NO_SUGGESTIONS }

    val isSuggestionsVisible: Observable<Boolean> =
        resultState.map { it == ResultState.SUGGESTIONS }
}

private fun createTitle(suggestion: PlaceSuggestion) = suggestion.simpleName

private fun createSubtitle(suggestion: PlaceSuggestion) =
    suggestion.fullName
        .removePrefix(suggestion.simpleName)
        .dropWhile { !it.isLetterOrDigit() }

internal data class SuggestionItem(
    val textLine1: String,
    val textLine2: String,
    val suggestion: PlaceSuggestion
)

internal enum class ResultState {
    EMPTY, SEARCHING, NO_SUGGESTIONS, SUGGESTIONS
}
