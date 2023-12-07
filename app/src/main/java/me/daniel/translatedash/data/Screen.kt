package me.daniel.translatedash.data

import androidx.annotation.StringRes
import me.daniel.translatedash.R

enum class Screen(@StringRes val title: Int) {
    ChooseMode(title = R.string.choose_mode),
    EndlessGame(title = R.string.play),
    TenWordsGame(title = R.string.play),
    EndlessResult(title = R.string.results),
    TenWordsResult(title = R.string.results)
}