package com.example.connectfourapp

data class GameState(
    // num rows and columns
    // TODO: Make a map between SettingState BoardSize enums and rows/cols

    val boardSize: SharedEnums.BoardSize = SharedEnums.BoardSize.STANDARD,
    val rows: Int = getRows(boardSize),
    val cols: Int = getCols(boardSize),

    // Stats tracking
    val playerOneWinCount: Int = 0,
    val playerTwoWinCount: Int = 0,
    val aiWinCount: Int = 0,
    val drawCount: Int = 0,
    val gamesPlayed: Int = 0,
    val movesMade: Int = 0,

    // Player Info
    val playerOneName: String = "Player 1",
    val playerTwoName: String = "Player 2",
    val playerOneColour: SharedEnums.PlayerColour = SharedEnums.PlayerColour.RED,
    val playerTwoColour: SharedEnums.PlayerColour = SharedEnums.PlayerColour.YELLOW,


    // Turn Info
    val turnText: String = "${playerOneName}'s Turn...",
    val currentTurn: PlayerType = PlayerType.ONE,


    // Victory Info
    val victoryType: VictoryType = VictoryType.NONE,
    val victoryPos: Int = 0,
    val hasWon: Boolean = false,

    // Game mode
    val gameMode: SharedEnums.GameMode = SharedEnums.GameMode.SINGLE
)

private fun getRows(boardSize: SharedEnums.BoardSize) : Int {
    val rows = when(boardSize) {
        SharedEnums.BoardSize.SMALL -> 5
        SharedEnums.BoardSize.STANDARD -> 6
        SharedEnums.BoardSize.LARGE -> 7
    }
    return rows
}

private fun getCols(boardSize: SharedEnums.BoardSize) : Int {
    val cols = when(boardSize) {
        SharedEnums.BoardSize.SMALL -> 6
        SharedEnums.BoardSize.STANDARD -> 7
        SharedEnums.BoardSize.LARGE -> 8
    }
    return cols
}

// Locally used
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
