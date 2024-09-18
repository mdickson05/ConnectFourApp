package com.example.connectfourapp

data class PlayerStats(
    var playerName: String = "",
    var gamesPlayed: Int = 0,
    var wins: Int = 0,
    var draws: Int = 0,
    var winRate: Float = 0f,
    var profile: Int = 0
)