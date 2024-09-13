package com.example.connectfourapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.connectfourapp.ui.theme.GreyBG
import com.example.connectfourapp.ui.theme.OrangeDisc
import com.example.connectfourapp.ui.theme.PinkDisc

@Composable
fun Disc(playerColour: SharedEnums.PlayerColour? = null) {
    val color = when (playerColour) {
        SharedEnums.PlayerColour.RED -> Color.Red
        SharedEnums.PlayerColour.YELLOW -> Color.Yellow
        SharedEnums.PlayerColour.PINK -> PinkDisc
        SharedEnums.PlayerColour.GREEN -> Color.Green
        SharedEnums.PlayerColour.ORANGE -> OrangeDisc
        null -> GreyBG
    }

    Box(
        modifier = Modifier
            .size(42.dp)
            .padding(4.dp)
            .background(color, CircleShape)
    )
}