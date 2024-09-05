package com.example.connectfourapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.connectfourapp.ui.theme.GreyBG
import com.example.connectfourapp.ui.theme.OrangeDisc
import com.example.connectfourapp.ui.theme.PinkDisc

@Composable
fun Disc(playerColour: PlayerColour) {
    val color = when (playerColour) {
        PlayerColour.RED -> Color.Red
        PlayerColour.YELLOW -> Color.Yellow
        PlayerColour.PINK -> PinkDisc
        PlayerColour.GREEN -> Color.Green
        PlayerColour.ORANGE -> OrangeDisc
    }

    Box(
        modifier = Modifier
            .size(42.dp)
            .padding(4.dp)
            .background(color, CircleShape)
    )
}