package com.example.connectfourapp

import android.media.Image

data class GameState(

    // Determine num of rows and cols based on board size
    val boardSize: SharedEnums.BoardSize = SharedEnums.BoardSize.STANDARD,
    val rows: Int = getRows(boardSize),
    val cols: Int = getCols(boardSize),

    // Stats tracking
    val playerOneWinCount: Int = 0,
    val playerTwoWinCount: Int = 0,
    val aiWinCount: Int = 0,
    val spDrawCount: Int = 0,
    val mpDrawCount: Int = 0,
    val spGamesPlayed: Int = 0,
    val mpGamesPlayed: Int = 0,
    val movesMade: Int = 0,

    // Player Info
    val playerOneName: String = "Player 1",
    val playerTwoName: String = "Player 2",
    val playerOneProfileImage: Int = R.drawable.ic_launcher_foreground,
    val playerTwoProfileImage: Int = R.drawable.ic_launcher_foreground,

    val playerOneColour: SharedEnums.PlayerColour = SharedEnums.PlayerColour.RED,
    val playerTwoColour: SharedEnums.PlayerColour = SharedEnums.PlayerColour.YELLOW,


    // Turn Info
    val turnText: String = "${playerOneName}'s Turn...",
    val currentTurn: PlayerType = PlayerType.ONE,

    // Victory Info
    val hasWon: Boolean = false,

    // Game mode
    val gameMode: SharedEnums.GameMode = SharedEnums.GameMode.SINGLE
)

// Function which converts boardSize enums into respective row integers
private fun getRows(boardSize: SharedEnums.BoardSize) : Int {
    val rows = when(boardSize) {
        SharedEnums.BoardSize.SMALL -> 5
        SharedEnums.BoardSize.STANDARD -> 6
        SharedEnums.BoardSize.LARGE -> 7
    }
    return rows
}

// Function which converts boardSize enums into respective column integers
private fun getCols(boardSize: SharedEnums.BoardSize) : Int {
    val cols = when(boardSize) {
        SharedEnums.BoardSize.SMALL -> 6
        SharedEnums.BoardSize.STANDARD -> 7
        SharedEnums.BoardSize.LARGE -> 8
    }
    return cols
}

// Locally used - no need to be included in SharedEnums
enum class PlayerType {
    ONE,
    TWO,
    AI,
    NONE
}
