package com.example.connectfourapp

data class StatsHolder(
    val id : String = "",
    var playerOneSP : PlayerStats = PlayerStats(),
    var playerOneMP : PlayerStats = PlayerStats(),
    var playerTwo : PlayerStats = PlayerStats(),
    var ai : PlayerStats = PlayerStats()
)
