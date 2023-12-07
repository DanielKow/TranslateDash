package me.daniel.translatedash.data

import androidx.annotation.StringRes
import me.daniel.translatedash.R

class Mode(@StringRes val nameResource: Int, val screen: Screen)

object DataSource {
    val modes = listOf(
        Mode(R.string.ten_words, Screen.TenWordsGame),
        Mode(R.string.endless, Screen.EndlessGame)
    )
    const val secondsToAnswer: Int = 10
}