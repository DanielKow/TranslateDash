package me.daniel.translatedash.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.daniel.translatedash.R
import me.daniel.translatedash.data.DataSource
import me.daniel.translatedash.ui.theme.TranslateDashTheme

@Composable
fun ChooseModeScreen(){
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Column (
                verticalArrangement = Arrangement.spacedBy(25.dp)){
                DataSource.modes.forEach {
                        item -> ModeButton(item)
                }
            }

        }
    }
}

@Composable
fun ModeButton(
    @StringRes labelResourceId: Int
) {
    Button(modifier = Modifier.fillMaxWidth(0.75f),
        onClick = { /*TODO*/ }) {
        Text(stringResource(labelResourceId))
    }
}

@Preview(showBackground = true)
@Composable
fun ChooseModeScreenPreview() {
    TranslateDashTheme {
        ChooseModeScreen()
    }
}