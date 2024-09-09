package com.example.connectfourapp

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectfourapp.ui.theme.BoardBlue
import com.example.connectfourapp.ui.theme.GreyBG
import com.example.connectfourapp.ui.theme.Righteous
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun GameScreen(
    viewModel: GameViewModel
){
    // GOT THE CODE FOR CHANGING CODE BASED ON ORIENTATION FROM STACK OVERFLOW:
    // https://stackoverflow.com/a/67612872/21301692
    var orientation by remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    // If our configuration changes then this will launch a new coroutine scope for it
    LaunchedEffect(configuration) {
        // Save any changes to the orientation value on the configuration object
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeContent(viewModel)
        }
        else -> {
            PortraitContent(viewModel)
        }
    }
}

@Composable
fun PortraitContent(
    viewModel: GameViewModel
){
    val state = viewModel.state

    LaunchedEffect(state.currentTurn)
    {
        if (state.currentTurn == PlayerType.AI) {
            // Delay to simulate AI thinking time
            delay(1000)
            viewModel.onAction(GameUserAction.AIMove)
        }
    }

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
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            // Player 1 + stats
            Column (
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                Image(
                    modifier = Modifier
                        .size(64.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Player 1 Profile Pic"
                )

                Column (
                    horizontalAlignment = Alignment.Start
                ) {
                    val winRate: Double = (state.playerOneWinCount.toDouble() / state.gamesPlayed.toDouble()) * 100
                    val formattedWinRate = String.format(Locale.ENGLISH, "%.1f", winRate)  // Rounds to 1 decimal place
                    Text(text = "Player 1", fontSize = 20.sp)
                    Text(text = "Wins: " + state.playerOneWinCount)
                    Text(text = "Win Rate: $formattedWinRate%")
                }
            }
            // Vs.
            Column {
                Text(text = "vs.", fontFamily = Righteous, fontSize = 40.sp)
            }
            // Player 2/AI + stats
            Column (
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                Image(
                    modifier = Modifier
                        .size(64.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Player 2/AI Profile Pic"
                )

                Column (
                    horizontalAlignment = Alignment.End
                ) {
                    if(state.gameMode == GameMode.MULTI)
                    {
                        val winRate: Double = (state.playerTwoWinCount.toDouble() / state.gamesPlayed.toDouble()) * 100
                        val formattedWinRate = String.format(Locale.ENGLISH, "%.1f", winRate)  // Rounds to 1 decimal place
                        Text(text = "Player 2", fontSize = 20.sp)
                        Text(text = "Wins: " + state.playerTwoWinCount)
                        Text(text = "Win Rate: $formattedWinRate%")
                    }
                    else
                    {
                        val winRate: Double = (state.aiWinCount.toDouble() / state.gamesPlayed.toDouble()) * 100
                        val formattedWinRate = String.format(Locale.ENGLISH, "%.1f", winRate)  // Rounds to 1 decimal place
                        Text(text = "AI", fontSize = 20.sp)
                        Text(text = "Wins: " + state.aiWinCount)
                        Text(text = "Win Rate: $formattedWinRate%")
                    }
                }
            }
        }

        // Current Turn Text
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = state.turnText)
        }

        // Board!
        Box (
            modifier = Modifier
                // .fillMaxWidth()
                // .aspectRatio(1f)
                .background(BoardBlue),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    // aspect ratio HAS to be cols / rows!
                    .aspectRatio(state.cols.toFloat() / viewModel.state.rows.toFloat()),
                columns = GridCells.Fixed(state.cols)

            ) {
                viewModel.boardItems.forEach { (cellNum, playerType) ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    if (state.currentTurn == PlayerType.ONE || state.currentTurn == PlayerType.TWO) {
                                        viewModel.onAction(GameUserAction.BoardTapped(cellNum))
                                    }
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(
                                visible = playerType != PlayerType.NONE,
                                enter = scaleIn(tween(1000))
                            ) {
                                when (playerType) {
                                    PlayerType.ONE -> Disc(playerColour = PlayerColour.RED)
                                    PlayerType.TWO -> Disc(playerColour = PlayerColour.YELLOW)
                                    PlayerType.AI -> Disc(playerColour = PlayerColour.ORANGE)
                                    PlayerType.NONE -> {}
                                }
                            }
                            Disc()

                        }
                    }
                }
            }
        }
        // Moves remaining/played
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            val movesRemaining: Int = (state.rows * state.cols) - state.movesMade
            Text(text = "Moves played: " + state.movesMade) // moves played
            Text(text = "Moves remaining: $movesRemaining") // moves remaining
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
                onClick = {
                    viewModel.onAction(GameUserAction.ResetButtonClicked)

                },
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

@Composable
fun LandscapeContent(
    viewModel: GameViewModel
){
    val state = viewModel.state

    LaunchedEffect(state.currentTurn)
    {
        if (state.currentTurn == PlayerType.AI) {
            // Delay to simulate AI thinking time
            delay(1000)
            viewModel.onAction(GameUserAction.AIMove)
        }
    }

    // Row to hold everything in
    Row (
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBG)
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Info:
        // - Player Turn
        // - Player 1 v Player 2 stats
        // - Moves played/remaining
        Column (
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            // Current turn text
            Row (
                // modifier = Modifier
                // .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = state.turnText)
            }

            // Player 1 + stats
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    modifier = Modifier
                        .size(64.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Player 1 Profile Pic"
                )

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val winRate: Double = (state.playerOneWinCount.toDouble() / state.gamesPlayed.toDouble()) * 100
                    val formattedWinRate = String.format(Locale.ENGLISH, "%.1f", winRate)  // Rounds to 1 decimal place
                    Text(text = "Player 1", fontSize = 20.sp)
                    Text(text = "Wins: " + state.playerOneWinCount)
                    Text(text = "Win Rate: $formattedWinRate%")
                }
            }
            // Vs.
            Row {
                Text(text = "vs.", fontFamily = Righteous, fontSize = 40.sp)
            }
            // Player 2 + stats
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    modifier = Modifier
                        .size(64.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Player 2 Profile Pic"
                )

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(state.gameMode == GameMode.MULTI)
                    {
                        val winRate: Double = (state.playerTwoWinCount.toDouble() / state.gamesPlayed.toDouble()) * 100
                        val formattedWinRate = String.format(Locale.ENGLISH, "%.1f", winRate)  // Rounds to 1 decimal place
                        Text(text = "Player 2", fontSize = 20.sp)
                        Text(text = "Wins: " + state.playerTwoWinCount)
                        Text(text = "Win Rate: $formattedWinRate%")
                    }
                    else
                    {
                        val winRate: Double = (state.aiWinCount.toDouble() / state.gamesPlayed.toDouble()) * 100
                        val formattedWinRate = String.format(Locale.ENGLISH,"%.1f", winRate)  // Rounds to 1 decimal place
                        Text(text = "AI", fontSize = 20.sp)
                        Text(text = "Wins: " + state.aiWinCount)
                        Text(text = "Win Rate: $formattedWinRate%")
                    }
                }
            }

            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Column {
                    val movesRemaining: Int = (state.rows * state.cols) - state.movesMade
                    Text(text = "Moves played: " + state.movesMade) // moves played
                    Text(text = "Moves remaining: $movesRemaining") // moves remaining
                }
            }


        }

        // Game Board and Buttons
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .width(400.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Board!
            Box (
                modifier = Modifier
                    // .size(300.dp)
                    // .aspectRatio(1f)
                    .background(BoardBlue),
                contentAlignment = Alignment.Center
            ){
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        // aspect ratio HAS to be cols/rows!
                        .aspectRatio(state.cols.toFloat() / viewModel.state.rows.toFloat()),
                    columns = GridCells.Fixed(state.cols)

                ) {
                    viewModel.boardItems.forEach { (cellNum, playerType) ->
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clickable(
                                        interactionSource = MutableInteractionSource(),
                                        indication = null
                                    ) {
                                        if (state.currentTurn == PlayerType.ONE || state.currentTurn == PlayerType.TWO) {
                                            viewModel.onAction(GameUserAction.BoardTapped(cellNum))
                                        }
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                AnimatedVisibility(
                                    visible = playerType != PlayerType.NONE,
                                    enter = scaleIn(tween(1000))
                                ) {
                                    when (playerType) {
                                        PlayerType.ONE -> Disc(playerColour = PlayerColour.RED)
                                        PlayerType.TWO -> Disc(playerColour = PlayerColour.YELLOW)
                                        PlayerType.AI -> Disc(playerColour = PlayerColour.ORANGE)
                                        PlayerType.NONE -> {}
                                    }
                                }
                                Disc()

                            }
                        }
                    }
                }
            }

            // Buttons
            Row (
//                modifier = Modifier
//                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
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
                    onClick = {
                        viewModel.onAction(GameUserAction.ResetButtonClicked)
                    },
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
}


@Preview
@Composable
fun Prev(){
    GameScreen(viewModel = GameViewModel())
}