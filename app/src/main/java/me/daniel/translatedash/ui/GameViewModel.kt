package me.daniel.translatedash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.daniel.translatedash.api.DictionaryApi
import me.daniel.translatedash.api.RandomWordsApi
import me.daniel.translatedash.data.GameState
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
    private val randomWordsApi = Retrofit.Builder()
        .baseUrl("https://random-word-api.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RandomWordsApi::class.java)

    private val dictionaryApi = Retrofit.Builder()
        .baseUrl("https://api.pons.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DictionaryApi::class.java)

    fun fetchWords() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                val words = randomWordsApi.getWords(4).toTypedArray()
                val indexes = arrayListOf(0, 1, 2, 3);
                indexes.shuffle()

                indexes.forEach { index ->
                    val word = words[index]
                    try {
                        val translations = dictionaryApi.getTranslation(word, "enpl", "en")

                        val correctWord =
                            translations[0].hits[0].roms[0].arabs[0].translations[0].target.split("<span")[0].trim()

                        _gameState.update { currentState ->
                            currentState.copy(
                                words = words,
                                correctWord = word,
                                wordToGuess = correctWord,
                                isReady = true
                            )
                        }
                        return@launch
                    } catch (_: Exception) {
                    }
                }
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
            currentState.copy(
                index = currentState.index + 1,
                answered = false,
                answer = "",
                isReady = false
            )
        }
    }

    fun gainPoint() {
        _gameState.update { currentState -> currentState.copy(score = currentState.score + 1) }
    }
}