package com.example.connectfourapp

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatsViewModel : ViewModel() {
    private val _playerStats = MutableLiveData<List<PlayerStats>>()
    val playerStats: LiveData<List<PlayerStats>> = _playerStats

    fun updateStats(gameState: GameState) {
        val playerOneSPWins = gameState.playerOneSPWinCount
        val playerOneMPWins = gameState.playerOneMPWinCount
        val playerTwoWins = gameState.playerTwoWinCount
        val spGamesPlayed = gameState.spGamesPlayed
        val mpGamesPlayed = gameState.mpGamesPlayed
        val spDrawCount = gameState.spDrawCount
        val mpDrawCount = gameState.mpDrawCount
        val aiWins = gameState.aiWinCount

        val playerOneProfileImage = gameState.playerOneProfileImage
        val playerTwoProfileImage = gameState.playerTwoProfileImage
        val aiProfilePic = R.drawable.profile_ai

        _playerStats.value = listOf(
            PlayerStats("Player 1", spGamesPlayed, playerOneSPWins, spDrawCount,calculateWinRate(playerOneSPWins, spGamesPlayed), playerOneProfileImage),
            PlayerStats("AI", spGamesPlayed, aiWins, spDrawCount, calculateWinRate(aiWins, spGamesPlayed), aiProfilePic),
            PlayerStats("Player 1 ", mpGamesPlayed, playerOneMPWins, mpDrawCount, calculateWinRate(playerOneMPWins, mpGamesPlayed), playerOneProfileImage),
            PlayerStats("Player 2", mpGamesPlayed, playerTwoWins, mpDrawCount, calculateWinRate(playerTwoWins, mpGamesPlayed), playerTwoProfileImage)

        )
    }
}