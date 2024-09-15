package com.example.connectfourapp

data class PlayerStats(
    val playerName: String,
    val gamesPlayed: Int,
    val wins: Int,
    val draws: Int,
    val winRate: Float,
    val profile: Int
)