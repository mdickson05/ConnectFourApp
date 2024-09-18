package com.example.connectfourapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GameViewModel(
    private val settingsViewModel: SettingsViewModel,
) : ViewModel() {

    private val playerOneSPRef = FirebaseDatabase.getInstance().getReference("Player 1 (SP)")
    private val playerOneMPRef = FirebaseDatabase.getInstance().getReference("Player 1 (MP)")
    private val playerTwoRef = FirebaseDatabase.getInstance().getReference("Player 2")
    private val aiRef = FirebaseDatabase.getInstance().getReference("AI")

    var state by mutableStateOf(generateState())
    var boardItems by mutableStateOf(generateBoardItems())

    private var _toastMessage by mutableStateOf<String?>(null)
    val toastMessage: String? get() = _toastMessage

    private fun generateBoardItems(): Map<Int, PlayerType> {
        return (1..(state.rows * state.cols)).associateWith { PlayerType.NONE }
    }

    private fun generateState() : GameState
    {
        // Set state to GameState defaults
        var state by mutableStateOf(GameState())

        // Update GameState based on settingsViewModel
        state = state.copy(

            boardSize = settingsViewModel.selectedBoardOption,
            rows = getRows(settingsViewModel.selectedBoardOption),
            cols = getCols(settingsViewModel.selectedBoardOption),

            playerOneName = settingsViewModel.playerOneName,
            playerTwoName = settingsViewModel.playerTwoName,
            playerOneProfileImage = settingsViewModel.getPlayerOneProfileImage(),
            playerTwoProfileImage = settingsViewModel.getPlayerTwoProfileImage(),

            playerOneColour = settingsViewModel.playerOneColour,
            playerTwoColour = settingsViewModel.playerTwoColour,
            gameMode = settingsViewModel.selectedModeOption
        )
        return state
    }

    fun updateGameState() {
        // update database values OR generate new database
        updateDatabase {
            // Generate a new game state
            state = generateState()

            // Regenerate board if board size has changed
            if (state.rows * state.cols != boardItems.size) {
                boardItems = generateBoardItems()
            }

            // Reset the game state
            gameReset()
        }
    }

    fun updateDatabase(onComplete: (Boolean) -> Unit) {
        databaseExists(playerOneSPRef, playerOneMPRef, playerTwoRef, aiRef) { allExist ->
            if (allExist) {
                // If all references exist, update entries
                if (state.gameMode == SharedEnums.GameMode.SINGLE) {
                    updateSPEntries(onComplete)
                } else {
                    updateMPEntries(onComplete)
                }
            } else {
                // If any of the references don't exist, create a new database
                createDatabase(onComplete)
            }
        }
    }

    private fun databaseExists(vararg refs : DatabaseReference, onComplete: (Boolean) -> Unit)
    {
        var allExist = true
        var completedChecks = 0

        for (ref in refs) {
            ref.get().addOnSuccessListener { dataSnapshot ->
                if (!dataSnapshot.exists()) {
                    allExist = false
                }
                completedChecks++
                if (completedChecks == refs.size) {
                    // Once all checks are done, invoke the callback
                    onComplete(allExist)
                }
            }.addOnFailureListener {
                // Handle errors in reading data, possibly a network issue
                _toastMessage = "Error with checking if database exists!"
                allExist = false
                completedChecks++
                if (completedChecks == refs.size) {
                    onComplete(allExist)
                }
            }
        }
    }

    private fun updateSPEntries(onComplete: (Boolean) -> Unit)
    {
        // get new stats
        val playerOneWinRate = calculateWinRate(state.playerOneSPWinCount, state.spGamesPlayed)
        val aiWinRate = calculateWinRate(state.aiWinCount, state.spGamesPlayed)
        val newPlayerOneStats = PlayerStats("Player 1 (SP)", state.spGamesPlayed, state.playerOneSPWinCount, state.spDrawCount, playerOneWinRate, state.playerOneProfileImage)
        val newAiStats = PlayerStats("AI", state.spGamesPlayed, state.aiWinCount, state.spDrawCount, aiWinRate, R.drawable.profile_ai)

        playerOneSPRef.get().addOnSuccessListener { playerOneSnapshot ->
            aiRef.get().addOnSuccessListener { aiSnapshot ->

                // Retrieve existing stats
                val currentPlayerOneStats = playerOneSnapshot.getValue(PlayerStats::class.java)
                val currentAiStats = aiSnapshot.getValue(PlayerStats::class.java)

                // Combine existing stats with new stats
                val updatedPlayerOneStats = generateNewStat(currentPlayerOneStats, newPlayerOneStats)
                val updatedAiStats = generateNewStat(currentAiStats, newAiStats)

                // Update Firebase with the new combined stats
                playerOneSPRef.setValue(updatedPlayerOneStats)
                aiRef.setValue(updatedAiStats)

                _toastMessage = "Single-player entries updated"

                onComplete(true)
            }
        }
    }

    private fun updateMPEntries(onComplete: (Boolean) -> Unit)
    {
        val playerOneWinRate = calculateWinRate(state.playerOneMPWinCount, state.mpGamesPlayed)
        val playerTwoWinRate = calculateWinRate(state.playerTwoWinCount, state.mpGamesPlayed)
        val newPlayerOneStats = PlayerStats("Player 1 (MP)", state.mpGamesPlayed, state.playerOneMPWinCount, state.mpDrawCount, playerOneWinRate, state.playerOneProfileImage)
        val newPlayerTwoStats = PlayerStats("Player 2", state.mpGamesPlayed, state.playerTwoWinCount, state.mpDrawCount, playerTwoWinRate, state.playerTwoProfileImage)


        playerOneMPRef.get().addOnSuccessListener { playerOneSnapshot ->
            playerTwoRef.get().addOnSuccessListener { playerTwoSnapshot ->

                // Retrieve existing stats
                val currentPlayerOneStats = playerOneSnapshot.getValue(PlayerStats::class.java)
                val currentPlayerTwoStats = playerTwoSnapshot.getValue(PlayerStats::class.java)

                // Combine existing stats with new stats
                val updatedPlayerOneStats = generateNewStat(currentPlayerOneStats, newPlayerOneStats)
                val updatedPlayerTwoStats = generateNewStat(currentPlayerTwoStats, newPlayerTwoStats)

                // Update Firebase with the new combined stats
                playerOneMPRef.setValue(updatedPlayerOneStats)
                playerTwoRef.setValue(updatedPlayerTwoStats)

                onComplete(true)

                _toastMessage = "Multi-player entries updated"
            }
        }
    }

    fun createDatabase(onComplete: (Boolean) -> Unit) {
        val playerOneSPWinRate = calculateWinRate(state.playerOneSPWinCount, state.spGamesPlayed)
        val playerOneMPWinRate = calculateWinRate(state.playerOneMPWinCount, state.mpGamesPlayed)
        val playerTwoWinRate = calculateWinRate(state.playerTwoWinCount, state.mpGamesPlayed)
        val aiWinRate = calculateWinRate(state.aiWinCount, state.spGamesPlayed)

        // default stats - will just grab from current session
        val playerOneSPStats = PlayerStats(
            "Player 1 (SP)",
            state.spGamesPlayed,
            state.playerOneSPWinCount,
            state.spDrawCount,
            playerOneSPWinRate,
            state.playerOneProfileImage
        )
        val playerOneMPStats = PlayerStats(
            "Player 1 (MP)",
            state.mpGamesPlayed,
            state.playerOneMPWinCount,
            state.mpDrawCount,
            playerOneMPWinRate,
            state.playerOneProfileImage
        )
        val playerTwoStats = PlayerStats(
            "Player 2",
            state.mpGamesPlayed,
            state.playerTwoWinCount,
            state.mpDrawCount,
            playerTwoWinRate,
            state.playerTwoProfileImage
        )
        val aiStats = PlayerStats(
            "AI",
            state.spGamesPlayed,
            state.aiWinCount,
            state.spDrawCount,
            aiWinRate,
            R.drawable.profile_ai
        )

        var completeCount = 0
        playerOneSPRef.setValue(playerOneSPStats)
            .addOnCompleteListener {
                completeCount++
            }
            .addOnFailureListener {
                _toastMessage = "Failed to store Player 1 (SP) data"
                onComplete(false)
            }
        playerOneMPRef.setValue(playerOneMPStats)
            .addOnCompleteListener {
                completeCount++
            }
            .addOnFailureListener {
                _toastMessage = "Failed to store Player 1 (MP) data"
                onComplete(false)
            }
        playerTwoRef.setValue(playerTwoStats)
            .addOnCompleteListener {
                completeCount++
            }
            .addOnFailureListener {
                _toastMessage = "Failed to store Player 2 data"
                onComplete(false)
            }
        aiRef.setValue(aiStats)
            .addOnCompleteListener {
                completeCount++
            }
            .addOnFailureListener {
                _toastMessage = "Failed to store AI data"
            }
        if(completeCount == 4)
        {
            _toastMessage = "Database created successfully"
            onComplete(true)
        }
    }

    private fun generateNewStat(
        old : PlayerStats?,
        new : PlayerStats
    ) : PlayerStats
    {
        return if (old != null) {
            PlayerStats(
                playerName = new.playerName,
                gamesPlayed = old.gamesPlayed + new.gamesPlayed,
                wins = old.wins + new.wins,
                draws = old.draws + new.draws,
                winRate = calculateWinRate(
                    old.wins + new.wins,
                    old.gamesPlayed + new.gamesPlayed
                ),
                profile = new.profile
            )
        } else {
            new // If no existing stats, just use the new stats
                // shouldn't ever be called?
        }
    }

    fun clearToastMessage() {
        _toastMessage = null
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

                boardItems = boardItems.toMutableMap().apply { this[move] = PlayerType.AI }
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
                        spGamesPlayed = state.spGamesPlayed + 1
                    )
                }

                // STEP 3: Check if game is now drawn
                else if(hasBoardFull()) {
                    state = state.copy (
                        turnText = "Game Draw",
                        spDrawCount =  state.spDrawCount + 1,
                        spGamesPlayed = state.spGamesPlayed + 1
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
            GameUserAction.PauseButtonClicked -> {
                pauseGame()
            }

        }
    }

    private fun pauseGame() {
        state = state.copy (
            isPaused = !state.isPaused
        )
    }

    private fun gameReset() {
        // Game should not be resettable if it is paused
        if(!state.isPaused)
        {
            boardItems.forEach{ (i, _) ->
                boardItems = boardItems.toMutableMap().apply { this[i] = PlayerType.NONE }
            }
            state = state.copy(
                movesMade = 0,
                turnText = "${state.playerOneName}'s turn...",
                currentTurn = PlayerType.ONE,
                invalidMessage = ""
            )
        }
    }

    private fun addValueToBoard(cellNum: Int) {
        // IF square is already taken...
        if(boardItems[cellNum] != PlayerType.NONE) {
            state = state.copy(
                invalidMessage = "Invalid move: square already taken"
            )
            return // do nothing
        }
        // If the row above the chosen square is empty
        else if(boardItems[cellNum + state.cols] == PlayerType.NONE) {
            state = state.copy(
                invalidMessage = "Invalid move: No square below"
            )
            return // do nothing
        }
        // If the move is valid...
        else
        {
            if(state.invalidMessage.isNotEmpty())
            {
                state = state.copy(
                    invalidMessage = ""
                )
            }
            // IF SINGLE PLAYER
            if(isSinglePlayer())
            {
                // STEP 1: Player makes the turn
                boardItems = boardItems.toMutableMap().apply { this[cellNum] = PlayerType.ONE }
                state = state.copy(
                    movesMade = state.movesMade + 1
                )

                // STEP 2: Check if P1 has won the game
                if(checkForVictory(PlayerType.ONE))
                {
                    state = state.copy(
                        turnText = "${state.playerOneName} wins",
                        playerOneSPWinCount = state.playerOneSPWinCount + 1,
                        currentTurn = PlayerType.NONE,
                        spGamesPlayed = state.spGamesPlayed + 1
                    )
                }

                // STEP 3: Check if game is now drawn
                else if(hasBoardFull()) {
                    state = state.copy (
                        turnText = "Game Draw",
                        spDrawCount =  state.spDrawCount + 1,
                        spGamesPlayed = state.spGamesPlayed + 1
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
                    boardItems = boardItems.toMutableMap().apply { this[cellNum] = PlayerType.ONE }
                    state = state.copy(
                        movesMade = state.movesMade + 1
                    )

                    // STEP 2: Check if P1 has won the game
                    if(checkForVictory(PlayerType.ONE))
                    {
                        state = state.copy(
                            turnText = "${state.playerOneName} wins",
                            playerOneMPWinCount = state.playerOneMPWinCount + 1,
                            currentTurn = PlayerType.NONE,
                            mpGamesPlayed = state.mpGamesPlayed + 1
                        )
                    }

                    // STEP 3: Check if game is now drawn
                    else if(hasBoardFull()) {
                        state = state.copy (
                            turnText = "Game Draw",
                            mpDrawCount =  state.mpDrawCount + 1,
                            mpGamesPlayed = state.mpGamesPlayed + 1
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
                    boardItems = boardItems.toMutableMap().apply { this[cellNum] = PlayerType.TWO }
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
                            mpGamesPlayed = state.mpGamesPlayed + 1
                        )
                    }

                    // STEP 3: Check if game is now drawn (i.e. board is full)
                    else if(hasBoardFull()) {
                        state = state.copy (
                            turnText = "Game Draw",
                            mpDrawCount =  state.mpDrawCount + 1,
                            mpGamesPlayed = state.mpGamesPlayed + 1
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
                    return true
                }
            }
        }

        // Check vertical
        for (row in 0 until state.rows - 3) {
            for (col in 0 until state.cols) {
                if (checkLine(player, row, col, 1, 0)) {
                    return true
                }
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (row in 0 until state.rows - 3) {
            for (col in 0 until state.cols - 3) {
                if (checkLine(player, row, col, 1, 1)) {
                    return true
                }
            }
        }

        // Check diagonal (top-right to bottom-left)
        for (row in 0 until state.rows - 3) {
            for (col in 3 until state.cols) {
                if (checkLine(player, row, col, 1, -1)) {
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
        return state.gameMode == SharedEnums.GameMode.SINGLE
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

    private fun calculateWinRate(wins: Int, gamesPlayed: Int): Float {
        return if (gamesPlayed > 0) (wins.toFloat() / gamesPlayed) * 100 else 0f
    }
}