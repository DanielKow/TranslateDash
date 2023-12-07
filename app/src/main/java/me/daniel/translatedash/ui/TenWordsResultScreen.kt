package me.daniel.translatedash.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TenWordsResultScreen(score: Int = 0, onTryAgain: () -> Unit = {}, onTryOtherMode: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly){
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Your correct answers:")
                    Text(text = "$score of 10", fontSize = 25.sp)
                }
                Column (verticalArrangement = Arrangement.spacedBy(25.dp)){
                    if (score == 10) {
                        Text(text = "You're awesome!!!", color = Color.Green, fontSize = 30.sp)
                    }
                    else if (score >= 8) {
                        Text(text = "Very good job!", color = Color.Green, fontSize = 25.sp)
                    }
                    else if (score >= 5) {
                        Text(text = "Nice job", fontSize = 25.sp)
                    }
                    else if (score >= 2) {
                        Text(text = "Good try :)", fontSize = 20.sp)
                    }
                    else {
                        Text(text = "Next time try better", color = Color.Red, fontSize = 20.sp)
                    }
                    Button(onClick = onTryAgain) {
                        Text(text = "Try again")
                    }
                    Button(onClick = onTryOtherMode) {
                        Text(text = "Try other mode")
                    }
                }
            }
        }
    }
}