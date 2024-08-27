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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.layout.ContentScale
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
    val colours = listOf("Red", "Yellow", "Green", "Orange", "Pink") // List of player colours
    var playerOneColour by remember {mutableStateOf(colours[0])} //Default colour is Red
    var playerTwoColour by remember { mutableStateOf(colours[1]) } //Default colour is Yellow
    var playerOneIsExpanded by remember {
        mutableStateOf(false)
    }
    var playerTwoIsExpanded by remember {
        mutableStateOf(false)
    }

    // Board Radio Options
    val boardOptions = listOf("Small (6x5)", "Standard (7x6) ", "Large (8x7)")
    val (selectedBoardOption, onBoardOptionSelected) = remember { mutableStateOf(boardOptions[1] ) }

    // Game Mode Radio Options
    val modeOptions = listOf("Single-Player", "Multiplayer")
    val modeOptionsDesc = listOf("(Player 1 vs. AI)", "(Player 1 vs. Player 2)")
    val (selectedModeOption, onModeOptionSelected) = remember { mutableStateOf(modeOptions[0] ) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBG)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = "Settings", fontFamily = CooperBTBold, fontSize = 30.sp, modifier = Modifier.padding(top = 10.dp))

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
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(64.dp),
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Player 1 Profile Pic"
                            )

                            // Edit profile image button
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.size(24.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                contentPadding = PaddingValues(1.5.dp)
                            ){
                                Icon(
                                    imageVector = Icons.Default.Create,
                                    contentDescription = "Settings",
                                )
                            }
                        }

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
                                label = {Text(text = "Colour")},
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(64.dp),
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Player 2 Profile Pic"
                            )

                            //Edit profile image button
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.size(24.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                contentPadding = PaddingValues(1.5.dp)
                            ){
                                Icon(
                                    imageVector = Icons.Default.Create,
                                    contentDescription = "Settings",
                                )
                            }
                        }
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
                                label = {Text(text = "Colour")},
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
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Board Size", fontFamily = CooperBTBold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 15.dp))
                Row (
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    boardOptions.forEach { text ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .selectable(
                                    selected = (text == selectedBoardOption),
                                    onClick = {
                                        onBoardOptionSelected(text)
                                    }
                                )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.DarkGray),
                                contentScale = ContentScale.Crop
                            )
                            RadioButton(
                                selected = (text == selectedBoardOption),
                                onClick = { onBoardOptionSelected(text) }
                            )
                            Text(
                                text = text,
                            )
                        }
                    }
                }
            }

            //---------- Mode Customisation
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Mode", fontFamily = CooperBTBold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 15.dp))
                Row (
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    modeOptions.forEachIndexed { index, text ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .selectable(
                                    selected = (text == selectedModeOption),
                                    onClick = {
                                        onModeOptionSelected(text)
                                    }
                                )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.DarkGray),
                                contentScale = ContentScale.Crop
                            )
                            RadioButton(
                                selected = (text == selectedModeOption),
                                onClick = { onModeOptionSelected(text) }
                            )
                            Text(
                                text = text,
                            )
                            Text(
                                text = modeOptionsDesc[index],
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            //---------- Option buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Go back to menu button
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(1.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back to Menu"
                    )
                }

                // Save Button
                Button(
                    onClick = { /*TODO*/},
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .size(width = 0.dp, height = 48.dp)
                    ,

                    contentPadding = PaddingValues(1.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Reset",
                    )
                    Spacer(modifier = Modifier
                        .width(12.dp)
                    )
                    Text(
                        text = "Save"
                    )

                }
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