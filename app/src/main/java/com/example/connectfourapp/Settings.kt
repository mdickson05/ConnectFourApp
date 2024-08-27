package com.example.connectfourapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectfourapp.ui.theme.BoardBlue
import com.example.connectfourapp.ui.theme.CooperBTBold
import com.example.connectfourapp.ui.theme.GreyBG
import com.example.connectfourapp.ui.theme.Righteous

//---------- Settings Screen Layout
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(){

    // TextView Names Variables
    var playerOneName by remember { mutableStateOf("Player 1") }
    var playerTwoName by remember { mutableStateOf("Player 2") }

    // ExposedDropDown Colour Variables
    val colours = listOf("Red", "Yellow", "Green", "Orange", "Pink")
    var playerOneColour by remember {mutableStateOf(colours[0])} //Default colour is Red
    var playerTwoColour by remember { mutableStateOf(colours[1]) } //Default colour is Yellow
    var playerOneIsExpanded by remember {
        mutableStateOf(false)
    }
    var playerTwoIsExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBG)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = "Settings", fontFamily = CooperBTBold, fontSize = 30.sp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GreyBG)
                .padding(horizontal = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //---------- Player 1 Customisation
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Player 1", fontSize = 20.sp)
                        Image(
                            modifier = Modifier
                                .size(64.dp),
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Player 1 Profile Pic"
                        )

                        // Player 1 Name
                        OutlinedTextField(
                            value = playerOneName,
                            onValueChange = { newPlayerOneName ->
                                playerOneName = newPlayerOneName
                            },
                            label = { Text(text = "Player 1 name...") },
                        )

                        //Player 1 Colour
                        ExposedDropdownMenuBox(
                            expanded = playerOneIsExpanded,
                            onExpandedChange = {playerOneIsExpanded = !playerOneIsExpanded },
                            modifier = Modifier.padding(top = 8.dp)
                        ){
                            TextField(
                                modifier = Modifier.menuAnchor(),
                                value = playerOneColour,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = playerOneIsExpanded)}
                            )
                            ExposedDropdownMenu(expanded = playerOneIsExpanded, onDismissRequest = { playerOneIsExpanded = false }) {
                                colours.forEachIndexed { index, text ->
                                    DropdownMenuItem(
                                        text = { Text(text = text) },
                                        onClick = {
                                            playerOneColour = colours[index]
                                            playerOneIsExpanded = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp)) // Spacer

                //---------- PLayer 2 Customisation
                Box(modifier = Modifier.weight(1f))
                {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Player 2", fontSize = 20.sp)
                        Image(
                            modifier = Modifier
                                .size(64.dp),
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Player 2 Profile Pic"
                        )
                        // Player 2 Name
                        OutlinedTextField(
                            value = playerTwoName,
                            onValueChange = { newPlayerTwoName ->
                                playerTwoName = newPlayerTwoName
                            },
                            label = { Text(text = "Player 2 name...") },
                        )

                        ExposedDropdownMenuBox(
                            expanded = playerTwoIsExpanded,
                            onExpandedChange = {playerTwoIsExpanded = !playerTwoIsExpanded },
                            modifier = Modifier.padding(top = 8.dp)
                        ){
                            TextField(
                                modifier = Modifier.menuAnchor(),
                                value = playerTwoColour,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = playerTwoIsExpanded)}
                            )
                            ExposedDropdownMenu(expanded = playerTwoIsExpanded, onDismissRequest = { playerTwoIsExpanded = false }) {
                                colours.forEachIndexed { index, text ->
                                    DropdownMenuItem(
                                        text = { Text(text = text) },
                                        onClick = {
                                            playerTwoColour = colours[index]
                                            playerTwoIsExpanded = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            }
                        }
                    }
                }
            }

            //---------- Board Customisation
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "sd")
            }

            //---------- Mode Customisation
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "sd")
            }
        }
    }
}

//---------- Preview
@Preview
@Composable
fun SettingsPrev(){
    SettingsScreen()
}