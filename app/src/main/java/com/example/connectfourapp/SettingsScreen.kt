package com.example.connectfourapp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectfourapp.ui.theme.CooperBTBold
import com.example.connectfourapp.ui.theme.GreyBG
import androidx.compose.runtime.State
import androidx.compose.ui.AbsoluteAlignment

// Source for orientation: https://stackoverflow.com/a/67612872/21301692
@Composable
fun SettingsScreen(){
    val settingsState = rememberSettingsState()
    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    // If our configuration changes then this will launch a new coroutine scope for it
    LaunchedEffect(configuration) {
        // Save any changes to the orientation value on the configuration object
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeContent(settingsState)
        }
        else -> {
            PortraitContent(settingsState)
        }
    }
}

//---------- Single line function to remember the player settings
@Composable
fun rememberSettingsState() = remember{ mutableStateOf(SettingsState())} // will remember a changeable state that lies within the data class

//---------- Data class holding key-value pairs of player settings
data class SettingsState(
    var playerOneName: String = "Player 1",
    var playerTwoName: String = "Player 2",
    var playerOneColour: String = "Red",
    var playerTwoColour: String = "Yellow",
    var playerOneIsExpanded: Boolean = false,
    var playerTwoIsExpanded: Boolean = false,
    var selectedBoardOption: String = "Standard (7x6)",
    var selectedModeOption: String = "Single-Player"
)

//---------- Global variables for different setting options/text options
val colours = listOf("Red", "Yellow", "Green", "Orange", "Pink")
val boardOptions = listOf("Small (6x5)", "Standard (7x6)", "Large (8x7)")
val modeOptions = listOf("Single-Player", "Multiplayer")
val modeOptionsDesc = listOf("(Player 1 vs. AI)", "(Player 1 vs. Player 2)")

//---------- Settings Screen Layout
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortraitContent(state: MutableState<SettingsState>){

    val settingsState by state // Access the current state from the state class

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
                            value = settingsState.playerOneName,
                            onValueChange = { newName ->
                                state.value = settingsState.copy(playerOneName = newName)
                            },
                            label = { Text(text = "Player 1 name...") },
                        )

                        // Player 1 Colour
                        ExposedDropdownMenuBox(
                            expanded = settingsState.playerOneIsExpanded,
                            onExpandedChange = {
                                state.value = settingsState.copy(playerOneIsExpanded = !settingsState.playerOneIsExpanded)
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ){
                            TextField(
                                modifier = Modifier.menuAnchor(),
                                value = settingsState.playerOneColour,
                                label = {Text(text = "Colour")},
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = settingsState.playerOneIsExpanded)}
                            )
                            ExposedDropdownMenu(
                                expanded = settingsState.playerOneIsExpanded,
                                onDismissRequest = { state.value = settingsState.copy(playerOneIsExpanded = false) }
                            ) {
                                colours.forEachIndexed { index, text ->
                                    DropdownMenuItem(
                                        text = { Text(text = text) },
                                        onClick = {
                                            state.value = settingsState.copy(
                                                playerOneColour = colours[index],
                                                playerOneIsExpanded = false
                                            )
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
                            value = settingsState.playerTwoName,
                            onValueChange = { newName ->
                                state.value = settingsState.copy(playerTwoName = newName)
                            },
                            label = { Text(text = "Player 2 name...") },
                        )

                        // Player 2 Colour
                        ExposedDropdownMenuBox(
                            expanded = settingsState.playerTwoIsExpanded,
                            onExpandedChange = {
                                state.value = settingsState.copy(playerTwoIsExpanded = !settingsState.playerTwoIsExpanded)
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            TextField(
                                modifier = Modifier.menuAnchor(),
                                value = settingsState.playerTwoColour,
                                label = { Text(text = "Colour") },
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = settingsState.playerTwoIsExpanded) }
                            )
                            ExposedDropdownMenu(
                                expanded = settingsState.playerTwoIsExpanded,
                                onDismissRequest = { state.value = settingsState.copy(playerTwoIsExpanded = false) }
                            ) {
                                colours.forEachIndexed { index, text ->
                                    DropdownMenuItem(
                                        text = { Text(text = text) },
                                        onClick = {
                                            state.value = settingsState.copy(
                                                playerTwoColour = colours[index],
                                                playerTwoIsExpanded = false
                                            )
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            }
                        }

                    }
                }
            }

            // Board Customisation
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
                                    selected = (text == settingsState.selectedBoardOption),
                                    onClick = {
                                        state.value = settingsState.copy(selectedBoardOption = text)
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
                                selected = (text == settingsState.selectedBoardOption),
                                onClick = { state.value = settingsState.copy(selectedBoardOption = text) }
                            )
                            Text(
                                text = text,
                            )
                        }
                    }
                }
            }

            // Mode Customisation
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
                                    selected = (text == settingsState.selectedModeOption),
                                    onClick = {
                                        state.value = settingsState.copy(selectedModeOption = text)
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
                                selected = (text == settingsState.selectedModeOption),
                                onClick = { state.value = settingsState.copy(selectedModeOption = text) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandscapeContent(state: State<SettingsState>){
    val settingsState = state.value // Access the current state from the state class

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBG)
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //LEFT SIDE
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(GreyBG)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    // Go back to menu button
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(36.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(1.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Menu"
                        )
                    }

                    // Settings title
                    Text(text = "Settings", fontFamily = CooperBTBold, fontSize = 30.sp, modifier = Modifier.padding(start = 10.dp))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //---------- Player 1 Customisation
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
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
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Create,
                                                contentDescription = "Settings",
                                            )
                                        }
                                    }
                                }

                                Column() {
                                    // Player 1 Name
                                    OutlinedTextField(
                                        value = settingsState.playerOneName,
                                        onValueChange = {
                                            settingsState.playerOneName = it
                                        },
                                        label = { Text(text = "Player 1 name...") },
                                    )

                                    //Player 1 Colour
                                    ExposedDropdownMenuBox(
                                        expanded = settingsState.playerOneIsExpanded,
                                        onExpandedChange = {
                                            settingsState.playerOneIsExpanded =
                                                !settingsState.playerOneIsExpanded
                                        },
                                        modifier = Modifier.padding(top = 8.dp)
                                    ) {
                                        TextField(
                                            modifier = Modifier.menuAnchor(),
                                            value = settingsState.playerOneColour,
                                            label = { Text(text = "Colour") },
                                            onValueChange = {},
                                            readOnly = true,
                                            trailingIcon = {
                                                ExposedDropdownMenuDefaults.TrailingIcon(
                                                    expanded = settingsState.playerOneIsExpanded
                                                )
                                            }
                                        )
                                        ExposedDropdownMenu(
                                            expanded = settingsState.playerOneIsExpanded,
                                            onDismissRequest = {
                                                settingsState.playerOneIsExpanded = false
                                            }) {
                                            colours.forEachIndexed { index, text ->
                                                DropdownMenuItem(
                                                    text = { Text(text = text) },
                                                    onClick = {
                                                        settingsState.playerOneColour =
                                                            colours[index]
                                                        settingsState.playerOneIsExpanded = false
                                                    },
                                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                                )
                                            }
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
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
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Create,
                                                contentDescription = "Settings",
                                            )
                                        }
                                    }
                                }
                                Column() {
                                    // Player 2 Name
                                    OutlinedTextField(
                                        value = settingsState.playerTwoName,
                                        onValueChange = { settingsState.playerTwoName = it },
                                        label = { Text(text = "Player 2 name...") },
                                    )

                                    ExposedDropdownMenuBox(
                                        expanded = settingsState.playerTwoIsExpanded,
                                        onExpandedChange = {
                                            settingsState.playerTwoIsExpanded =
                                                !settingsState.playerTwoIsExpanded
                                        },
                                        modifier = Modifier.padding(top = 8.dp)
                                    ) {
                                        TextField(
                                            modifier = Modifier.menuAnchor(),
                                            label = { Text(text = "Colour") },
                                            value = settingsState.playerTwoColour,
                                            onValueChange = {},
                                            readOnly = true,
                                            trailingIcon = {
                                                ExposedDropdownMenuDefaults.TrailingIcon(
                                                    expanded = settingsState.playerTwoIsExpanded
                                                )
                                            }
                                        )
                                        ExposedDropdownMenu(
                                            expanded = settingsState.playerTwoIsExpanded,
                                            onDismissRequest = {
                                                settingsState.playerTwoIsExpanded = false
                                            }) {
                                            colours.forEachIndexed { index, text ->
                                                DropdownMenuItem(
                                                    text = { Text(text = text) },
                                                    onClick = {
                                                        settingsState.playerTwoColour =
                                                            colours[index]
                                                        settingsState.playerTwoIsExpanded = false
                                                    },
                                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //RIGHT SIDE
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(GreyBG)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //---------- Board Customisation
                    Column(
                        modifier = Modifier
                            .weight(1f) // Ensures the column takes equal space
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Board Size",
                            fontFamily = CooperBTBold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            boardOptions.forEach { text ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .selectable(
                                            selected = (text == settingsState.selectedBoardOption),
                                            onClick = {
                                                settingsState.selectedBoardOption = text
                                            }
                                        )
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .background(Color.DarkGray),
                                        contentScale = ContentScale.Crop
                                    )
                                    RadioButton(
                                        selected = (text == settingsState.selectedBoardOption),
                                        onClick = { settingsState.selectedBoardOption = text }
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
                            .weight(1f) // Ensures the column takes equal space
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Mode",
                            fontFamily = CooperBTBold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Row(

                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            modeOptions.forEachIndexed { index, text ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .selectable(
                                            selected = (text == settingsState.selectedModeOption),
                                            onClick = {
                                                settingsState.selectedModeOption = text
                                            }
                                        )
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .background(Color.DarkGray),
                                        contentScale = ContentScale.Crop
                                    )
                                    RadioButton(
                                        selected = (text == settingsState.selectedModeOption),
                                        onClick = { settingsState.selectedModeOption = text }
                                    )

                                    Text(
                                        text = text,
                                    )
//                                    Text(
//                                        text = modeOptionsDesc[index],
//                                        fontSize = 12.sp
//                                    )
                                }
                            }
                        }
                    }

                    // Save Button
                    Button(
                        onClick = { /*TODO*/},
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .size(width = 0.dp, height = 24.dp)
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
}

//---------- Preview
@Preview
@Composable
fun SettingsPrev(){
    SettingsScreen()
}

//@Preview(name = "Portrait")
//@Composable
//fun SettingsPreviewPortrait() {
//    SettingsScreen()
//}

//@Preview(name = "Landscape", device = "spec:shape=Normal,width=840,height=400,unit=dp,dpi=440")
//@Composable
//fun SettingsPreviewLandscape() {
//    val configuration = Configuration().apply {
//        orientation = Configuration.ORIENTATION_LANDSCAPE
//    }
//    SettingsScreen()
//}