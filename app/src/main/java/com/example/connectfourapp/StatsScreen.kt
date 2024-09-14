package com.example.connectfourapp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectfourapp.ui.theme.CooperBTBold

@Composable
fun StatsScreen(onBackClick: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    var orientation by remember { mutableStateOf(configuration.orientation) }

    // Update orientation state when the configuration changes
    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> LandscapeStatsContent(onBackClick)
        else -> PortraitStatsContent(onBackClick)
    }
}

@Composable
fun PortraitStatsContent(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Header(onBackClick)
        Spacer(modifier = Modifier.height(16.dp))

        // Wrap the PlayerStatsRow in a Column to make space evenly
        Column(
            modifier = Modifier.fillMaxHeight(), // Take full height of the remaining space
            verticalArrangement = Arrangement.SpaceEvenly // Evenly space the content by a fixed amount
        ) {
            PlayerStatsRowPortrait()
        }
    }
}

@Composable
fun LandscapeStatsContent(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Header(onBackClick)
        Spacer(modifier = Modifier.height(16.dp))
        PlayerStatsRowLandscape()
    }
}

@Composable
fun Header(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton(onBackClick)
        Text(
            text = "Statistics",
            fontFamily = CooperBTBold,
            fontSize = 32.sp,
        )
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
//    IconButton(
//        onClick = onClick,
//        modifier = Modifier.size(48.dp)
//    ) {
//        Icon(
//            imageVector = Icons.Default.ArrowBack,
//            contentDescription = "Back to Menu"
//        )
//    }
    Button(
        onClick = { onClick()},
        modifier = Modifier.size(36.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(1.dp),
        colors = ButtonDefaults.buttonColors(Color.DarkGray)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back to Menu"
        )
    }
}

@Composable
fun PlayerStatsRowPortrait() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(46.dp)
    ) {
        StatsSectionPortrait("Singleplayer", listOf("Player 1", "AI"))
        Divider()
        StatsSectionPortrait("Multiplayer", listOf("Player 1", "Player 2"))
    }
}

@Composable
fun PlayerStatsRowLandscape() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatsSectionLandscape("Singleplayer", listOf("Player 1", "AI"), Modifier.weight(1f))

        // Vertical Divider
        Divider(
            modifier = Modifier
                .fillMaxHeight() // Full height of the parent
                .width(1.dp)
                .background(Color.Gray)
        )
        StatsSectionLandscape("Multiplayer", listOf("Player 1", "Player 2"), Modifier.weight(1f))
    }
}


@Composable
fun StatsSectionPortrait(title: String, players: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "(${players.joinToString(" vs ")})",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )

        // Display players in a Column for portrait mode
        Row(
            modifier = Modifier.fillMaxWidth(),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            players.forEach { PlayerStatsPortrait(it) }
        }
    }
}

@Composable
fun StatsSectionLandscape(title: String, players: List<String>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 8.dp), // Use the dynamic modifier
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "(${players.joinToString(" vs ")})",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )

        // Display players in a Row for landscape mode
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            players.forEach { PlayerStatsLandscape(it) }
        }
        //Divider()
    }
}


@Composable
fun PlayerStatsPortrait(playerName: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "$playerName Profile Pic"
        )
        Text(text = playerName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "Games: 0")
        Text(text = "Wins: 0")
        Text(text = "Win Rate: 0%")
    }
}

@Composable
fun PlayerStatsLandscape(playerName: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Image(
            modifier = Modifier.size(48.dp), // Smaller size for landscape
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "$playerName Profile Pic"
        )
        Text(text = playerName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = "Games: 0", fontSize = 14.sp)
        Text(text = "Wins: 0", fontSize = 14.sp)
        Text(text = "Win Rate: 0%", fontSize = 14.sp)
    }
}

@Preview(name = "Portrait", showBackground = true)
@Composable
fun PortraitStatsPreview() {
    StatsScreen(onBackClick = {})
}

@Preview(name = "Landscape", showBackground = true, widthDp = 720, heightDp = 360)
@Composable
fun LandscapeStatsPreview() {
    StatsScreen(onBackClick = {})
}

