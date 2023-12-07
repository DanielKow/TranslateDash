package me.daniel.translatedash.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.daniel.translatedash.R
import me.daniel.translatedash.data.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: Screen,
    navigateHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = navigateHome) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        })
}

@Composable
fun App(
    viewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.ChooseMode.name
    )
    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                navigateHome = { navController.navigate(Screen.ChooseMode.name) })
        }
    )
    { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = Screen.ChooseMode.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.ChooseMode.name) {
                ChooseModeScreen(
                    onModeSelected = { navController.navigate(it.name) },
                    alreadyTranslatedCorrectly = uiState.totalScore
                )
            }
            composable(route = Screen.TenWordsGame.name) {
                TenWordsGameScreen(onGameFinished = {
                    viewModel.increaseScoreBy(10)
                    viewModel.setLastScore(5)
                    navController.navigate(Screen.TenWordsResult.name)
                })
            }
            composable(route = Screen.EndlessGame.name) {
                EndlessModeGameScreen()
            }
            composable(route = Screen.TenWordsResult.name) {
                TenWordsResultScreen(
                    uiState.lastScore,
                    onTryAgain = { navController.navigate(Screen.TenWordsGame.name) },
                    onTryOtherMode = { navController.navigate(Screen.ChooseMode.name) })
            }
        }
    }

}