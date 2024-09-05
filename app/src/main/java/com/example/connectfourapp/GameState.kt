package com.example.connectfourapp

import com.example.connectfourapp.ui.theme.GreyBG

data class GameState(
    // num rows and columns
    // TODO: Make a map between SettingState BoardSize enums and rows/cols
    val rows: Int = 6,
    val cols: Int = 7,

    // Stats tracking
    val playerOneWinCount: Int = 0,
    val playerTwoWinCount: Int = 0,
    val drawCount: Int = 0,

    // Player Info
    val PlayerOneColour: PlayerColour = PlayerColour.RED,
    val PlayerTwoColour: PlayerColour = PlayerColour.YELLOW,


    // Turn Info
    val turnText: String = "Player 1's Turn...",
    val currentTurn: PlayerType = PlayerType.ONE,


    // Victory Info
    val victoryType: VictoryType = VictoryType.NONE,
    val victoryPos: Int = 0,
    val hasWon: Boolean = false
)

enum class PlayerType {
    ONE,
    TWO,
    AI,
    NONE
}

enum class VictoryType {
    NONE,
    HORIZONTAL,
    VERTICAL,
    DIAGONAL
}

enum class PlayerColour {
    RED,
    YELLOW,
    GREEN,
    ORANGE,
    PINK
}
