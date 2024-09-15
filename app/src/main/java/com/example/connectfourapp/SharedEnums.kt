package com.example.connectfourapp

sealed class SharedEnums {

    enum class PlayerColour {
        RED,
        YELLOW,
        GREEN,
        ORANGE,
        PINK,
        AI
    }

    enum class GameMode {
        SINGLE,
        MULTI
    }

    enum class BoardSize {
        SMALL,
        STANDARD,
        LARGE
    }

}