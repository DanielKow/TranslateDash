package me.daniel.translatedash.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.daniel.translatedash.data.GameState
import me.daniel.translatedash.data.Translation

@Composable
fun Game(
    gameState: GameState,
    onCorrectAnswer: (String) -> Unit = {},
    onWrongAnswer: (String) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        ) {
            DecreasingProgressBar(gameState)
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Score: ${gameState.score}", fontSize = 18.sp, fontStyle = FontStyle.Italic)
        }
        Row(modifier = Modifier.weight(0.2f)) {
            ToTranslate(text = "test")
        }
        Row(modifier = Modifier.weight(0.7f)) {
            TranslationsGroup(
                arrayOf(
                    Translation("test 1", true),
                    Translation("Test 2"),
                    Translation("Test 3"),
                    Translation("Test 4")
                ), gameState,
                onAnswered = { chosenTranslation ->
                    if (!gameState.answered) {
                        if (chosenTranslation.correct) {
                            onCorrectAnswer(chosenTranslation.text)
                        } else {
                            onWrongAnswer(chosenTranslation.text)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun DecreasingProgressBar(gameState: GameState) {
    val progress = remember { Animatable(1f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = progress.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        LaunchedEffect(gameState.answered) {
            if (!gameState.answered) {
                progress.snapTo(1f)
                progress.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 5000,
                        easing = LinearOutSlowInEasing
                    )
                )
            }
            else {
                progress.stop()
            }
        }
    }
}

@Composable
fun ToTranslate(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Translate:", fontSize = 20.sp)
        Text(text = text, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun TranslationsGroup(
    translations: Array<Translation>,
    gameState: GameState,
    onAnswered: (Translation) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(), Arrangement.SpaceEvenly) {
        translations.forEach {
            Row(modifier = Modifier.weight(1f)) {
                TranslationButton(
                    translation = it,
                    gameState = gameState,
                    onClicked = { onAnswered(it) })
            }
        }
    }
}

@Composable
fun TranslationButton(translation: Translation, onClicked: () -> Unit = {}, gameState: GameState) {
    Button(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        shape = RectangleShape,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 50.dp),
        onClick = onClicked,
        colors = ButtonDefaults.buttonColors(
            if (gameState.answered && gameState.answer == translation.text && !translation.correct) Color.Red
            else if (gameState.answered && translation.correct) Color.Green
            else MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = translation.text, fontSize = 25.sp)
    }
}