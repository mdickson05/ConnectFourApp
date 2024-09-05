package com.example.connectfourapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var state by mutableStateOf(GameState())

    val boardItems: MutableMap<Int, PlayerType> = generateBoardItems()

    private fun generateBoardItems(): MutableMap<Int, PlayerType> {
        return (1..(state.rows * state.cols)).associateWith { PlayerType.NONE }.toMutableMap()
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
        }
    }

    private fun gameReset() {
        boardItems.forEach{ (i, _) ->
            boardItems[i] = PlayerType.NONE
        }
        state = state.copy(
            turnText = "Player 1's turn...",
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
        if(boardItems[cellNum + state.cols] == PlayerType.NONE) {
            return
        }

        else if (state.currentTurn == PlayerType.ONE) {

            // STEP 1: Player 1 makes the turn
            boardItems[cellNum] = PlayerType.ONE

            // STEP 2: Check if P1 has won the game
            if(checkForVictory(PlayerType.ONE))
            {
                state = state.copy(
                    turnText = "Player 1 wins",
                    playerOneWinCount = state.playerOneWinCount + 1,
                    currentTurn = PlayerType.NONE,
                    hasWon = true
                )
            }

            // STEP 3: Check if game is now drawn
            else if(hasBoardFull()) {
                state = state.copy (
                    turnText = "Game Draw",
                    drawCount =  state.drawCount + 1
                )
            }

            // STEP 4: Move to next player
            else {
                state = state.copy (
                    turnText = "Player 2's Turn...",
                    currentTurn = PlayerType.TWO
                )
            }

        }

        // IF it is player 2's turn...
        else if (state.currentTurn == PlayerType.TWO) {

            // STEP 1: P2 makes the turn
            boardItems[cellNum] = PlayerType.TWO

            // STEP 2: Check if P2's move has won the game
            if(checkForVictory(PlayerType.TWO))
            {
                state = state.copy(
                    turnText = "Player 2 wins",
                    playerOneWinCount = state.playerOneWinCount + 1,
                    currentTurn = PlayerType.NONE,
                    hasWon = true
                )
            }

            // STEP 3: Check if game is now drawn (i.e. board is full)
            else if(hasBoardFull()) {
                state = state.copy (
                    turnText = "Game Draw",
                    drawCount =  state.drawCount + 1
                )
            }

            // STEP 4: Move to next player
            else {
                state = state.copy (
                    turnText = "Player 1's Turn...",
                    currentTurn = PlayerType.ONE
                )
            }

        }
    }

    // IF there is no empty values in the board, it follows that the board is full
    private fun hasBoardFull(): Boolean {
        return !boardItems.containsValue(PlayerType.NONE)
    }

    private fun checkForVictory(boardValue: PlayerType): Boolean {
        return false // does nothing, for now
    }

}