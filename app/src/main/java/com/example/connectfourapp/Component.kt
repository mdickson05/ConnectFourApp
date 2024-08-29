package com.example.connectfourapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.connectfourapp.ui.theme.GreyBG

@Composable
fun GameBoard(
    rows: Int = 6,
    cols: Int = 7
) {
    Column {
        for(row in 0 until rows)
        {
            Row {
                for(col in 0 until cols) {
                    Column {
                        Tile();
                    }
                }
            }
        }
    }
}

@Composable
fun Tile()
{
    Button(
        onClick = { /*TODO*/ },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = GreyBG
        ),
        modifier = Modifier
            .size(42.dp)
            .padding(4.dp)
    ) {

    }
}