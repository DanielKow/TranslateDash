package me.daniel.translatedash.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import me.daniel.translatedash.data.DataSource
import me.daniel.translatedash.data.GameResult

@Composable
fun TenWordsGameScreen(
    onGameFinished: (GameResult) -> Unit = {},
    gameViewModel: GameViewModel = viewModel()
) {
    val gameState by gameViewModel.gameState.collectAsState()

    if (gameState.isReady) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${gameState.index} of 10",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Game(gameState, onCorrectAnswer = { answer ->
                gameViewModel.answer(answer)
                gameViewModel.gainPoint()

            }, onWrongAnswer = { answer ->
                gameViewModel.answer(answer)
            })


        }
        LaunchedEffect(gameState.answered) {
            if (gameState.answered) {
                delay(1_000);
                if (gameState.index == 10) {
                    withContext(Dispatchers.Main) {
                        onGameFinished(GameResult(gameState.score, 10))
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        gameViewModel.nextWord()
                    }
                }
            } else {
                delay(DataSource.secondsToAnswer * 1000L)
                withContext(Dispatchers.Main) {
                    gameViewModel.answer("")
                }
            }
        }
    } else {
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

