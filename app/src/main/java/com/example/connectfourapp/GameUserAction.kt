package com.example.connectfourapp

sealed class GameUserAction {
    data object ResetButtonClicked : GameUserAction()
    data class BoardTapped(val cellNum: Int): GameUserAction()
    data object AIMove : GameUserAction()
    data object PauseButtonClicked : GameUserAction()
}