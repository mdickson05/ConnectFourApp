package com.example.connectfourapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectfourapp.ui.theme.ConnectFourAppTheme
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectFourAppTheme {
                val navController = rememberNavController()
                val settingsViewModel: SettingsViewModel = viewModel()
                val gameViewModel: GameViewModel = viewModel { GameViewModel(settingsViewModel) }
                val statsViewModel: StatsViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Menu.route
                ) {
                    composable(Screen.Menu.route) {
                        MenuScreen(navController)
                    }
                    composable(Screen.Stats.route) {
                        StatsScreen(
                            gameViewModel = gameViewModel,
                            statsViewModel = statsViewModel,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                    composable(Screen.Game.route) {
                        GameScreen(viewModel = gameViewModel,  navController = navController)
                    }
                    composable(Screen.Settings.route) {
                        SettingsScreen(viewModel = settingsViewModel, navController = navController)
                    }
                }
            }
        }
    }
}


@Composable
fun MenuScreen(navController: NavHostController) {
    Box( // Box to align text at button and title and buttons in middle
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        //---------- Connect 4 title and Buttons
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.titlelogo),
                contentDescription = null,
                modifier = Modifier
                    .width(350.dp) //Adjust the title size
                    .aspectRatio(461f / 262f) // Image aspect ratio DO NOT CHANGE
                    .background(Color.White), // Changes background colour DO NOT CHANGE
                contentScale = ContentScale.Fit
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center

            ) {
                //---------- Statistics button
                Button(
                    onClick = { navController.navigate(Screen.Stats.route) },
                    modifier = Modifier
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.DarkGray)
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "StatsButton",
                    )
                }

                //---------- Play button
                Button(
                    onClick = { navController.navigate(Screen.Game.route) },
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "PlayButton",
                    )
                }

                //---------- Settings Button
                Button(
                    onClick = { navController.navigate(Screen.Settings.route) },
                    modifier = Modifier
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.DarkGray)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "SettingsButton",
                    )
                }
            }
        }

        //---------- All rights reserved text
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "© 2024 Marcus Dickson, Charles Cope and Jet Le.",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "All Rights Reserved",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

//@Preview
//@Composable
//fun prevMainScreen(){
//    MenuScreen(navController = )
//}

sealed class Screen(val route: String) {
    object Menu : Screen("menu")
    object Stats : Screen("stats")
    object Game : Screen("game")
    object Settings : Screen("settings")
}



