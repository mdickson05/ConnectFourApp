package com.example.connectfourapp

data class PlayerStats(
    val playerName: String = "",
    val gamesPlayed: Int = 0,
    val wins: Int = 0,
    val draws: Int = 0,
    val winRate: Float = 0f,
    val profile: Int = 0
)