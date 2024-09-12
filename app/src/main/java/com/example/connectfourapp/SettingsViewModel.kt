package com.example.connectfourapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    //---------- Settings variables
    // these are to be accessed by the game screen...
    var playerOneName by mutableStateOf("Player 1")
    var playerTwoName by mutableStateOf("Player 2")
    var playerOneColour by mutableStateOf("Player 1")
    var playerTwoColour by mutableStateOf("Player 2")
    var playerOneIsExpanded by mutableStateOf(false)
    var playerTwoIsExpanded by mutableStateOf(false)
    var selectedBoardOption by mutableStateOf("Standard (7x6)")
    var selectedModeOption by mutableStateOf("Single-Player")

    //---------- Mutators
    fun updatePlayerOneName(name : String){
        playerOneName = name
    }

    fun updatePlayerTwoName(name : String){
        playerTwoName = name
    }

    fun updatePlayerOneColour(colour : String){
        playerOneColour = colour
    }

    fun updatePlayerTwoColour(colour : String){
        playerTwoColour = colour
    }

    fun updatePlayerOneIsExpanded(){ // Toggles if Player one drop down menu is down or not
        playerOneIsExpanded = !playerOneIsExpanded // Opposite of what it is
    }

    fun updatePlayerTwoIsExpanded(){
        playerTwoIsExpanded = !playerTwoIsExpanded
    }

    fun updateSelectedBoardOption(option : String){
        selectedBoardOption = option
    }

    fun updateSelectedModeOption(option : String){
        selectedModeOption = option
    }

}