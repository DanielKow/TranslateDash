package me.daniel.translatedash.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(score: Int, totalWords: Int, onTryAgain: () -> Unit, onTryOtherMode: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Your correct answers:")
                    Text(text = "$score of $totalWords", fontSize = 25.sp)
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val percentage = score / totalWords.toFloat()
                    if (percentage >= 0.8) {
                        Text(text = "You're awesome!!!", color = Color.Green, fontSize = 30.sp)
                    } else if (percentage >= 0.2) {
                        Text(text = "Good job", fontSize = 25.sp)
                    } else {
                        Text(text = "Nice try :)", fontSize = 20.sp)
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = onTryAgain, modifier = Modifier.fillMaxWidth(0.5f)) {
                        Text(text = "Try again")
                    }
                    Button(onClick = onTryOtherMode, modifier = Modifier.fillMaxWidth(0.5f)) {
                        Text(text = "Try other mode")
                    }
                }
            }
        }
    }
}