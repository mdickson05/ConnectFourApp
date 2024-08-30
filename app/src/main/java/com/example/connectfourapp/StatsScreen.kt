package com.example.connectfourapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun StatsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "STATISTICS",
            fontSize = 40.sp,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),  // Add some padding for spacing
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Singleplayer Stats Section
            Divider()
            Text(
                text = "Singleplayer",
                fontSize = 30.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "(Player 1 vs AI)",
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic
            )


            Row() {
                // Player 1 Stats with Image
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        modifier = Modifier.size(64.dp),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Player 1 Profile Pic"
                    )

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(start = 32.dp)
                    ) {
                        Text(text = "Player 1", fontSize = 20.sp)
                        Text(text = "Games Played: 0")
                        Text(text = "Wins: 0")
                        Text(text = "Win Rate: 0%")
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        modifier = Modifier.size(64.dp),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "AI Profile Pic"
                    )

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(start = 46.dp)
                    ) {
                        Text(text = "AI", fontSize = 20.sp)
                        Text(text = "Games Played: 0")
                        Text(text = "Wins: 0")
                        Text(text = "Win Rate: 0%")
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Divider()

            // Multiplayer Stats Section
            Text(
                text = "Multiplayer",
                fontSize = 30.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "(Player 1 vs Player 2)",
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic
            )


            Row() {
                // Player 1 Stats with Image
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        modifier = Modifier.size(64.dp),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Player 1 Profile Pic"
                    )

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(start = 46.dp)
                    ) {
                        Text(text = "Player 1", fontSize = 20.sp)
                        Text(text = "Games Played: 0")
                        Text(text = "Wins: 0")
                        Text(text = "Win Rate: 0%")
                    }
                }



                // Player 2 Stats with Image
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        modifier = Modifier.size(64.dp),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Player 2 Profile Pic"
                    )

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(start = 32.dp)
                    ) {
                        Text(text = "Player 2", fontSize = 20.sp)
                        Text(text = "Games Played: 0")
                        Text(text = "Wins: 0")
                        Text(text = "Win Rate: 0%")
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Divider()
        }


        // Back to Menu Button
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(1.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back to Menu"
            )
        }
    }
}

