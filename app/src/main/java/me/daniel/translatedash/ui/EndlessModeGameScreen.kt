package me.daniel.translatedash.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun EndlessModeGameScreen(
    onGameFinished: () -> Unit = {},
    gameViewModel: GameViewModel = viewModel()
) {
    val gameState by gameViewModel.gameState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Word #${gameState.index}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Game(gameState, onCorrectAnswer = { answer ->
            gameViewModel.answer(answer)
            gameViewModel.gainPoint()

        }, onWrongAnswer = { answer ->
            gameViewModel.answer(answer)
            gameViewModel.looseLive()
        })
    }

    LaunchedEffect(gameState.answered) {
        if (gameState.answered) {
            delay(1_000);
            if (gameState.lives == 0) {
                withContext(Dispatchers.Main) {
                    onGameFinished()
                }
            } else {
                withContext(Dispatchers.Main) {
                    gameViewModel.nextWord()
                }
            }
        } else {
            delay(5_000)
            withContext(Dispatchers.Main) {
                gameViewModel.answer("")
                gameViewModel.looseLive()
                if (gameState.lives == 0) {
                    withContext(Dispatchers.Main) {
                        onGameFinished()
                    }
                }
            }
        }
    }
}