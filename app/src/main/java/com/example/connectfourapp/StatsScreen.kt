package com.example.connectfourapp

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectfourapp.ui.theme.CooperBTBold
import java.util.Locale


@Composable
fun StatsScreen(
    statsViewModel: StatsViewModel,
    onBackClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    var orientation by remember { mutableStateOf(configuration.orientation) }

    // Observe toast message from statsViewModel
    val toastMessage = statsViewModel.toastMessage

    // Show toast if there's a message
    toastMessage?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
        // Reset the toast message to avoid repeated toasts
        statsViewModel.clearToastMessage()
    }

    // No need to observe playerStats, it's already observable
    val playerStats = statsViewModel.playerStats

    // Update orientation state when the configuration changes
    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> LandscapeStatsContent(
            onBackClick,
            playerStats = playerStats
        )
        else -> PortraitStatsContent(
            onBackClick,
            playerStats = playerStats
        )
    }
}

@Composable
fun PortraitStatsContent(
    onBackClick: () -> Unit,
    playerStats: List<PlayerStats> // Pass the playerStats list
) {
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
            PlayerStatsRowPortrait(playerStats)
        }
    }
}

@Composable
fun LandscapeStatsContent(
    onBackClick: () -> Unit,
    playerStats: List<PlayerStats> // Pass the playerStats list
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Header(onBackClick)
        Spacer(modifier = Modifier.height(16.dp))
        PlayerStatsRowLandscape(playerStats)
    }
}

@Composable
fun Header(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
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
fun PlayerStatsRowPortrait(playerStats: List<PlayerStats>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(46.dp)
    ) {
        // Singleplayer stats
        StatsSectionPortrait(
            title = "Singleplayer",
            players = playerStats.filter { it.playerName == "Player 1 (SP)" || it.playerName == "AI"}
        )

        Divider()

        // Multiplayer stats

        StatsSectionPortrait(
            title = "Multiplayer",
            players = playerStats.filter { it.playerName == "Player 1 (MP)" || it.playerName == "Player 2"}
        )
    }
}

@Composable
fun PlayerStatsRowLandscape(playerStats: List<PlayerStats>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatsSectionLandscape(
            title = "Singleplayer",
            players = playerStats.filter { it.playerName == "Player 1" || it.playerName == "AI"},
            modifier = Modifier.weight(1f)
        )

        // Vertical Divider
        Divider(
            modifier = Modifier
                .fillMaxHeight() // Full height of the parent
                .width(1.dp)
                .background(Color.Gray)
        )
        StatsSectionLandscape(
            title = "Multiplayer",
            players = playerStats.filter { it.playerName == "Player 1 " || it.playerName == "Player 2"},
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun StatsSectionPortrait(title: String, players: List<PlayerStats>) {
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
            text = "(${players.joinToString(" vs ") { it.playerName }})",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )

        // Display players in a Column for portrait mode
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            players.forEach { player ->
                PlayerStatsPortrait(
                    playerName = player.playerName,
                    gamesPlayed = player.gamesPlayed,
                    wins = player.wins,
                    draws = player.draws,
                    winRate = calculateWinRate(player.wins, player.gamesPlayed),
                    playerProfilePic = player.profile
                )
            }
        }
    }
}

@Composable
fun StatsSectionLandscape(title: String, players: List<PlayerStats>, modifier: Modifier = Modifier) {
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
            text = "(${players.joinToString(" vs ") { it.playerName }})",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )

        // Display players in a Row for landscape mode
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            players.forEach { player ->
                PlayerStatsLandscape(
                    playerName = player.playerName,
                    gamesPlayed = player.gamesPlayed,
                    wins = player.wins,
                    draws = player.draws,
                    winRate = calculateWinRate(player.wins, player.gamesPlayed),
                    playerProfilePic = player.profile
                )
            }
        }
    }
}


@Composable
fun PlayerStatsPortrait(playerName: String, gamesPlayed: Int, wins: Int, draws: Int, winRate: Float, playerProfilePic: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PlayerProfilePicture(playerName = playerName, playerProfilePic = playerProfilePic)
        PlayerName(playerName = playerName, fontSize = 20.sp)
        PlayerStats(gamesPlayed = gamesPlayed, wins = wins, draws = draws, winRate = winRate, fontSize = 16.sp)
    }
}

@Composable
fun PlayerStatsLandscape(playerName: String, gamesPlayed: Int, wins: Int, draws: Int, winRate: Float, playerProfilePic: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        PlayerProfilePicture(playerName = playerName, playerProfilePic = playerProfilePic)
        PlayerName(playerName = playerName, fontSize = 18.sp)
        PlayerStats(gamesPlayed = gamesPlayed, wins = wins, draws = draws, winRate = winRate, fontSize = 14.sp)
    }
}

@Composable
fun PlayerProfilePicture(playerProfilePic: Int, playerName: String) {
    Image(
        modifier = Modifier.size(48.dp),
        painter = painterResource(id = playerProfilePic),
        contentDescription = "$playerName Profile Pic"
    )
}
@Composable
fun PlayerName(playerName: String, fontSize: TextUnit) {
    Text(text = playerName, fontSize = fontSize, fontWeight = FontWeight.Bold)
}
@Composable
fun PlayerStats(gamesPlayed: Int, wins: Int, draws: Int, winRate: Float, fontSize: TextUnit) {
    val formattedWinRate = String.format(Locale.ENGLISH, "%.1f", winRate)
    Text(text = "Games: $gamesPlayed", fontSize = fontSize)
    Text(text = "Wins: $wins", fontSize = fontSize)
    Text(text = "Draws: $draws", fontSize = fontSize)
    Text(text = "Win Rate: ${formattedWinRate}%", fontSize = fontSize)
}

fun calculateWinRate(wins: Int, gamesPlayed: Int): Float {
    return if (gamesPlayed > 0) (wins.toFloat() / gamesPlayed) * 100 else 0f
}


