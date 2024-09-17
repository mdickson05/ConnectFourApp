package com.example.connectfourapp

data class StatsHolder(
    val id : String = "",
    val playerOneSP : PlayerStats = PlayerStats(),
    val playerOneMP : PlayerStats = PlayerStats(),
    val playerTwo : PlayerStats = PlayerStats(),
    val ai : PlayerStats = PlayerStats()
)
