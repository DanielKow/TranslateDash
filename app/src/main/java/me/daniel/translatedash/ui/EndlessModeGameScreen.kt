package me.daniel.translatedash.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import me.daniel.translatedash.R
import me.daniel.translatedash.data.DataSource
import me.daniel.translatedash.data.GameResult

@Composable
fun EndlessModeGameScreen(
    onGameFinished: (GameResult) -> Unit = {},
    gameViewModel: GameViewModel = viewModel()
) {
    val gameState by gameViewModel.gameState.collectAsState()

    if (gameState.isReady) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                for (i in 1..3) {
                    Icon(
                        if (gameState.lives < i) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                        stringResource(R.string.live)
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Word #${gameState.index}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Game(gameState, onCorrectAnswer = { answer ->
                gameViewModel.answer(answer)
                gameViewModel.gainPoint()

            }, onWrongAnswer = { answer ->
                gameViewModel.answer(answer)
                gameViewModel.looseLive()
            })
        }

        LaunchedEffect(gameState.lives) {
            if (gameState.lives == 0) {
                withContext(Dispatchers.Main) {
                    delay(1_000)
                    onGameFinished(GameResult(gameState.score, gameState.index))
                }
            }
        }

        LaunchedEffect(gameState.answered) {
            if (gameState.answered) {
                delay(1_000)
                if (gameState.lives > 0) {
                    withContext(Dispatchers.Main) {
                        gameViewModel.nextWord()
                    }
                }
            } else {
                delay(DataSource.secondsToAnswer * 1000L)
                withContext(Dispatchers.Main) {
                    gameViewModel.answer("")
                    gameViewModel.looseLive()
                }
            }
        }
    }
    else {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }

    LaunchedEffect(gameState.index) {
        gameViewModel.fetchWords()
    }
}