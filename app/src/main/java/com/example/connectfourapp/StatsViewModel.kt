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

        _playerStats.value = listOf(
            PlayerStats("Player 1 (single)", spGamesPlayed, playerOneSPWins, calculateWinRate(playerOneSPWins, spGamesPlayed)),
            PlayerStats("AI", spGamesPlayed, spDrawCount, calculateWinRate(spDrawCount, spGamesPlayed)),
            PlayerStats("Player 1 (multi)", mpGamesPlayed, playerOneMPWins, calculateWinRate(playerOneMPWins, mpGamesPlayed)),
            PlayerStats("Player 2", mpGamesPlayed, playerTwoWins, calculateWinRate(playerTwoWins, mpGamesPlayed))

        )
    }
}