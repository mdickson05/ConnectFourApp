package com.example.connectfourapp

data class GameState(
    // num rows and columns
    // TODO: Make a map between SettingState BoardSize enums and rows/cols
    val rows: Int = 6,
    val cols: Int = 7,

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
    val playerOneColour: PlayerColour = PlayerColour.RED,
    val playerTwoColour: PlayerColour = PlayerColour.YELLOW,


    // Turn Info
    val turnText: String = "${playerOneName}'s Turn...",
    val currentTurn: PlayerType = PlayerType.ONE,


    // Victory Info
    val victoryType: VictoryType = VictoryType.NONE,
    val victoryPos: Int = 0,
    val hasWon: Boolean = false,

    // Game mode
    val gameMode: GameMode = GameMode.SINGLE
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

enum class GameMode {
    SINGLE,
    MULTI
}
