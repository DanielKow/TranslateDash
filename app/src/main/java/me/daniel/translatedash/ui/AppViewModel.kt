package me.daniel.translatedash.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.daniel.translatedash.data.UiState

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun increaseScoreBy(toIncrease: Int) {
        _uiState.update { currentState ->
            currentState.copy(totalScore = currentState.totalScore + toIncrease)
        }
    }

    fun setLastScore(score: Int) {
        _uiState.update { currentState ->
            currentState.copy(lastScore = score)
        }
    }

    fun setLastTotalWords(totalWords: Int) {
        _uiState.update { currentState ->
            currentState.copy(lastTotalWords = totalWords)
        }
    }
}