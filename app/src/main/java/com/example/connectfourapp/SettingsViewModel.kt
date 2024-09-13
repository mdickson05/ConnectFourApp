package com.example.connectfourapp

import android.text.TextUtils.substring
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    //---------- Settings variables
    // these are to be accessed by the game screen...
    var playerOneName by mutableStateOf("Player 1")
    var playerTwoName by mutableStateOf("Player 2")
    var playerOneIsExpanded by mutableStateOf(false)
    var playerTwoIsExpanded by mutableStateOf(false)

    // To be converted into using enums
    var playerOneColour by mutableStateOf(SharedEnums.PlayerColour.RED)
    var playerTwoColour by mutableStateOf(SharedEnums.PlayerColour.YELLOW)
    var selectedBoardOption by mutableStateOf(SharedEnums.BoardSize.STANDARD)
    var selectedModeOption by mutableStateOf(SharedEnums.GameMode.MULTI)

    //---------- Mutators
    fun updatePlayerOneIsExpanded(){ // Toggles if Player one drop down menu is down or not
        playerOneIsExpanded = !playerOneIsExpanded // Opposite of what it is
    }

    fun updatePlayerTwoIsExpanded(){
        playerTwoIsExpanded = !playerTwoIsExpanded
    }

    fun updatePlayerOneName(name : String){
        playerOneName = name
    }

    fun updatePlayerTwoName(name : String){
        playerTwoName = name
    }

    fun updatePlayerOneColour(colour : String){
        playerOneColour = SharedEnums.PlayerColour.valueOf(colour.uppercase())
    }

    fun updatePlayerTwoColour(colour : String){
        playerOneColour = SharedEnums.PlayerColour.valueOf(colour.uppercase())
    }

    fun updateSelectedBoardOption(option : SharedEnums.BoardSize){
        selectedBoardOption = option
    }

    fun updateSelectedModeOption(option : SharedEnums.GameMode){
        selectedModeOption = option
    }

}