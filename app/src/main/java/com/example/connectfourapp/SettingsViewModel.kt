package com.example.connectfourapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

    // Profile image
    private var playerOneProfileIndex by mutableIntStateOf(0) // Stores the index of image in list
    private var playerTwoProfileIndex by mutableIntStateOf(1)

    // List of profile pictures
private val profileImages = listOf(
        R.drawable.profile_1,
        R.drawable.profile_2,
        R.drawable.profile_3,
        R.drawable.profile_4,
        R.drawable.profile_5,
//        R.drawable.profile_6,
//        R.drawable.profile_7,
//        R.drawable.profile_8,
//        R.drawable.profile_9,
//        R.drawable.profile_10
)

    //---------- Cycle through player profile pics
    fun cyclePlayerOneProfile(forward: Boolean) {
        playerOneProfileIndex = if (forward) {
            (playerOneProfileIndex + 1) % profileImages.size
        } else {
            (playerOneProfileIndex - 1 + profileImages.size) % profileImages.size
        }
    }

    fun cyclePlayerTwoProfile(forward: Boolean) {
        playerTwoProfileIndex = if (forward) {
            (playerTwoProfileIndex + 1) % profileImages.size
        } else {
            (playerTwoProfileIndex - 1 + profileImages.size) % profileImages.size
        }
    }

    // Accessors for profile image
    fun getPlayerOneProfileImage(): Int = profileImages[playerOneProfileIndex]
    fun getPlayerTwoProfileImage(): Int = profileImages[playerTwoProfileIndex]

    fun onBackButtonClicked(updateGameState: () -> Unit) {
        // Call the provided function to update the game state
        updateGameState()
    }

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

    fun updatePlayerOneColour(colour: String) {
        val newColour = SharedEnums.PlayerColour.valueOf(colour.uppercase())
        if (newColour != playerTwoColour) {
            playerOneColour = newColour
        } else {
            //nothing colour already taken by player 2
        }
    }

    fun updatePlayerTwoColour(colour: String) {
        val newColour = SharedEnums.PlayerColour.valueOf(colour.uppercase())
        if (newColour != playerOneColour) {
            playerTwoColour = newColour
        } else {
            // Nothing colour already taken by player 1
        }
    }

    fun updateSelectedBoardOption(option : SharedEnums.BoardSize){
        selectedBoardOption = option
    }

    fun updateSelectedModeOption(option : SharedEnums.GameMode){
        selectedModeOption = option
    }

}