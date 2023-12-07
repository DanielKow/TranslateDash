package me.daniel.translatedash.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.daniel.translatedash.api.RandomWordsApi
import me.daniel.translatedash.data.GameState
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
    private val randomWordsApi = Retrofit.Builder()
        .baseUrl("https://random-word-api.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RandomWordsApi::class.java)

    fun fetchWords() {
        viewModelScope.launch {
            val words = randomWordsApi.getWords(4).toTypedArray()
            val random = Random.Default
            val randomIndex = random.nextInt(0, words.size)

            val correctWord = words[randomIndex]

            _gameState.update { currentState ->
                currentState.copy(
                    words = words,
                    correctWord = correctWord,
                    wordToGuess = correctWord
                )
            }
        }
    }

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