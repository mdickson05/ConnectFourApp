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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectFourAppTheme {
                // MenuScreen()
//                SettingsScreen(
//                    viewModel = settingsViewModel
//                )
                // val settingsViewModel: SettingsViewModel = viewModel()
                // val gameViewModel: GameViewModel = viewModel { GameViewModel(settingsViewModel) }
                // GameScreen(viewModel = gameViewModel)
                StatsScreen()
            }
        }
    }
}

@Preview
@Composable
fun MenuScreen() {
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
                    onClick = { /*TODO*/ },
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
                    onClick = { /*TODO*/ },
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
                    onClick = { /*TODO*/ },
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

