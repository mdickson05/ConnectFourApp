package com.example.connectfourapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel(private val settingsViewModel: SettingsViewModel) : ViewModel() {

    var state by mutableStateOf(generateState())

    val boardItems: MutableMap<Int, PlayerType> = generateBoardItems()

    private fun generateBoardItems(): MutableMap<Int, PlayerType> {
        return (1..(state.rows * state.cols)).associateWith { PlayerType.NONE }.toMutableMap()
    }

    private fun generateState() : GameState
    {
        // Set state to GameState defaults
        var state by mutableStateOf(GameState())

        // Update GameState based on settingsViewModel

        state = state.copy(
            playerOneName = settingsViewModel.playerOneName,
            playerTwoName = settingsViewModel.playerTwoName

            // playerOneColour = settingsViewModel.playerOneColour,
            // playerTwoColour = settingsViewModel.playerTwoColour,
            // gameMode = settingsViewModel.gameMode,
            // boardSize = settingsViewModel.boardSize

        )


        return state
    }

    fun onAction(action: GameUserAction)
    {
        when(action) {
            is GameUserAction.BoardTapped -> {
                addValueToBoard(action.cellNum)
            }
            GameUserAction.ResetButtonClicked -> {
                gameReset()
            }
            GameUserAction.AIMove -> {
                // STEP 1: Generate AI move
                val move: Int = generateAIMove()

                boardItems[move] = PlayerType.AI
                state = state.copy(
                    movesMade = state.movesMade + 1
                )

                // STEP 2: Check if AI has won the game
                if(checkForVictory(PlayerType.AI))
                {
                    state = state.copy(
                        turnText = "AI wins",
                        aiWinCount = state.aiWinCount + 1,
                        currentTurn = PlayerType.NONE,
                        hasWon = true,
                        gamesPlayed = state.gamesPlayed + 1
                    )
                }

                // STEP 3: Check if game is now drawn
                else if(hasBoardFull()) {
                    state = state.copy (
                        turnText = "Game Draw",
                        drawCount =  state.drawCount + 1,
                        gamesPlayed = state.gamesPlayed + 1
                    )
                }

                // STEP 4: Change to Player 1
                else {
                    state = state.copy (
                        turnText = "${state.playerOneName}'s Turn...",
                        currentTurn = PlayerType.ONE
                    )
                }
            }

        }
    }

    private fun gameReset() {
        boardItems.forEach{ (i, _) ->
            boardItems[i] = PlayerType.NONE
        }
        state = state.copy(
            movesMade = 0,
            turnText = "${state.playerOneName}'s turn...",
            currentTurn = PlayerType.ONE,
            victoryType = VictoryType.NONE,
            hasWon = false
        )
    }


    private fun addValueToBoard(cellNum: Int) {
        // IF square is already taken...
        if(boardItems[cellNum] != PlayerType.NONE) {
            return // do nothing
        }
        // If the row above the chosen square is empty
        else if(boardItems[cellNum + state.cols] == PlayerType.NONE) {
            return // do nothing
        }
        // If the move is valid...
        else
        {
            // IF SINGLE PLAYER
            if(isSinglePlayer())
            {
                // STEP 1: Player makes the turn
                boardItems[cellNum] = PlayerType.ONE
                state = state.copy(
                    movesMade = state.movesMade + 1
                )

                // STEP 2: Check if P1 has won the game
                if(checkForVictory(PlayerType.ONE))
                {
                    state = state.copy(
                        turnText = "${state.playerOneName} wins",
                        playerOneWinCount = state.playerOneWinCount + 1,
                        currentTurn = PlayerType.NONE,
                        hasWon = true,
                        gamesPlayed = state.gamesPlayed + 1
                    )
                }

                // STEP 3: Check if game is now drawn
                else if(hasBoardFull()) {
                    state = state.copy (
                        turnText = "Game Draw",
                        drawCount =  state.drawCount + 1,
                        gamesPlayed = state.gamesPlayed + 1
                    )
                }

                // STEP 4: Generate AI move
                else {
                    state = state.copy (
                        turnText = "AI's Turn...",
                        currentTurn = PlayerType.AI
                    )
                }

                // do nothing, functionality to be added
                // make our turn THEN do the AI turn

            }
            // IF Multiplayer
            else {
                if (state.currentTurn == PlayerType.ONE) {

                    // STEP 1: Player 1 makes the turn
                    boardItems[cellNum] = PlayerType.ONE
                    state = state.copy(
                        movesMade = state.movesMade + 1
                    )

                    // STEP 2: Check if P1 has won the game
                    if(checkForVictory(PlayerType.ONE))
                    {
                        state = state.copy(
                            turnText = "${state.playerOneName} wins",
                            playerOneWinCount = state.playerOneWinCount + 1,
                            currentTurn = PlayerType.NONE,
                            hasWon = true,
                            gamesPlayed = state.gamesPlayed + 1
                        )
                    }

                    // STEP 3: Check if game is now drawn
                    else if(hasBoardFull()) {
                        state = state.copy (
                            turnText = "Game Draw",
                            drawCount =  state.drawCount + 1,
                            gamesPlayed = state.gamesPlayed + 1
                        )
                    }

                    // STEP 4: Move to next player
                    else {
                        state = state.copy (
                            turnText = "${state.playerTwoName}'s Turn...",
                            currentTurn = PlayerType.TWO
                        )
                    }
                }

                // IF it is player 2's turn...
                else if (state.currentTurn == PlayerType.TWO) {

                    // STEP 1: P2 makes the turn
                    boardItems[cellNum] = PlayerType.TWO
                    state = state.copy(
                        movesMade = state.movesMade + 1
                    )

                    // STEP 2: Check if P2's move has won the game
                    if(checkForVictory(PlayerType.TWO))
                    {
                        state = state.copy(
                            turnText = "${state.playerTwoName} wins",
                            playerTwoWinCount = state.playerTwoWinCount + 1,
                            currentTurn = PlayerType.NONE,
                            hasWon = true,
                            gamesPlayed = state.gamesPlayed + 1
                        )
                    }

                    // STEP 3: Check if game is now drawn (i.e. board is full)
                    else if(hasBoardFull()) {
                        state = state.copy (
                            turnText = "Game Draw",
                            drawCount =  state.drawCount + 1,
                            gamesPlayed = state.gamesPlayed + 1
                        )
                    }

                    // STEP 4: Move to next player
                    else
                    {
                        state = state.copy (
                            turnText = "${state.playerOneName}'s Turn...",
                            currentTurn = PlayerType.ONE
                        )
                    }

                }
            }
        }
    }

    // IF there is no empty values in the board, it follows that the board is full
    private fun hasBoardFull(): Boolean {
        return !boardItems.containsValue(PlayerType.NONE)
    }

    private fun checkForVictory(player: PlayerType): Boolean {
        // Check horizontal
        for (row in 0 until state.rows) {
            for (col in 0 until state.cols - 3) {
                if (checkLine(player, row, col, 0, 1)) {
                    state = state.copy(victoryType = VictoryType.HORIZONTAL, victoryPos = getIndex(row, col))
                    return true
                }
            }
        }

        // Check vertical
        for (row in 0 until state.rows - 3) {
            for (col in 0 until state.cols) {
                if (checkLine(player, row, col, 1, 0)) {
                    state = state.copy(victoryType = VictoryType.VERTICAL, victoryPos = getIndex(row, col))
                    return true
                }
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (row in 0 until state.rows - 3) {
            for (col in 0 until state.cols - 3) {
                if (checkLine(player, row, col, 1, 1)) {
                    state = state.copy(victoryType = VictoryType.DIAGONAL, victoryPos = getIndex(row, col))
                    return true
                }
            }
        }

        // Check diagonal (top-right to bottom-left)
        for (row in 0 until state.rows - 3) {
            for (col in 3 until state.cols) {
                if (checkLine(player, row, col, 1, -1)) {
                    state = state.copy(victoryType = VictoryType.DIAGONAL, victoryPos = getIndex(row, col))
                    return true
                }
            }
        }

        return false
    }

    private fun checkLine(player: PlayerType, startRow: Int, startCol: Int, rowDelta: Int, colDelta: Int): Boolean {
        for (i in 0 until 4) {
            val row = startRow + i * rowDelta
            val col = startCol + i * colDelta
            if (boardItems[getIndex(row, col)] != player) {
                return false
            }
        }
        return true
    }

    private fun getIndex(row: Int, col: Int): Int {
        return row * state.cols + col + 1
    }

    private fun isSinglePlayer(): Boolean {
        return state.gameMode == GameMode.SINGLE
    }

    private fun generateAIMove(): Int {
        val limit: Int = state.rows * state.cols
        var move: Int = (1..limit).random()
        var checked: Boolean = checkMove(move)

        // while boardItems == NONE OR (move is not in bottom row AND boardItems in the row below == NONE)
        while(!checked)
        {
            move = (1..limit).random()
            checked = checkMove(move)
        }

        return move
    }

    private fun checkMove(move: Int): Boolean {
        val limit: Int = state.rows * state.cols

        // Check if the current move position is empty
        if (boardItems[move] != PlayerType.NONE) {
            return false // Position is already taken
        }

        // If move is in the bottom row, it's valid as long as it's empty
        if (move > limit - state.cols) {
            return true
        }

        // If move is not in the bottom row, check if the position below is filled
        return boardItems[move + state.cols] != PlayerType.NONE
    }


}