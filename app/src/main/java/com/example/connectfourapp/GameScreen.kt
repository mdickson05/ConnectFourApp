package com.example.connectfourapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectfourapp.ui.theme.GreyBG

@Composable
fun GameScreen(){
    // Column to hold everything in
    Column (
      modifier = Modifier
          .fillMaxSize()
          .background(GreyBG)
          .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        // Player 1 vs Player 2 + stats
        Row {
            // Player 1 + stats
            Column {

            }
            // Vs.
            Column {

            }
            // Player 2 + stats
            Column {

            }
        }

        // Player 1's turn
        Text(text = "Player 1's Turn...")

        // Board!
        Box {
            
        }

        // Moves remaining/played
        Row {
            // TODO: Make the moves played/remaining variables
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                Text(text = "Moves played: 0") // moves played
                Text(text = "Moves remaining: 42") // moves remaining
            }
        }

        // Buttons
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            // Back to menu
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

            // Pause game
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(1.dp)
            ) {

                Icon(
                    painter = painterResource(R.drawable.baseline_pause),
                    contentDescription = "Pause"
                )
            }

            // Settings
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(1.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                )
            }

            // Reset game
            Button(
                onClick = { /*TODO*/},
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .size(width = 0.dp, height = 48.dp)
                ,

                contentPadding = PaddingValues(1.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset",
                )
                Spacer(modifier = Modifier
                    .width(12.dp)
                )
                Text(
                    text = "Reset Game"
                )

            }
        }
    }
}

@Preview
@Composable
fun Prev(){
    GameScreen()
}