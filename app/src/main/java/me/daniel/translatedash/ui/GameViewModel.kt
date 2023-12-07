package me.daniel.translatedash.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.daniel.translatedash.data.GameState

class GameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    fun looseLive() {
        _gameState.update { currentState ->
            currentState.copy(lives = currentState.lives - 1)
        }
    }

    fun answer(answer: String) {
        _gameState.update { currentState -> currentState.copy(answered = true, answer = answer) }
    }

    fun nextWord() {
        _gameState.update { currentState ->
            currentState.copy(index = currentState.index + 1, answered = false, answer = "")
        }
    }

    fun gainPoint() {
        _gameState.update { currentState -> currentState.copy(score = currentState.score + 1) }
    }
}