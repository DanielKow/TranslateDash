package me.daniel.translatedash.data

data class GameState(
    val score: Int = 0,
    val lives: Int = 3,
    val index: Int = 1,
    val answered: Boolean = false,
    val answer: String = "",
    val words: Array<String> = emptyArray(),
    val correctWord: String = "",
    val wordToGuess: String = "",
    val isReady: Boolean = false
)