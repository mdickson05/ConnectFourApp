package com.example.connectfourapp

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.viewmodel.compose.viewModel

// Source for orientation: https://stackoverflow.com/a/67612872/21301692
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
){
    var orientation by remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    // If our configuration changes then this will launch a new coroutine scope for it
    LaunchedEffect(configuration) {
        // Save any changes to the orientation value on the configuration object
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeContent(viewModel)
        }
        else -> {
            PortraitContent(viewModel)
        }
    }
}

//---------- Global variables for different setting options/text options
val colours = listOf("Red", "Yellow", "Green", "Orange", "Pink")
val boardOptions = SharedEnums.BoardSize.entries.map {
    when (it) {
        SharedEnums.BoardSize.SMALL -> "Small (6x5)"
        SharedEnums.BoardSize.STANDARD -> "Standard (7x6)"
        SharedEnums.BoardSize.LARGE -> "Large (8x7)"
    }
}
val modeOptions = SharedEnums.GameMode.entries.map {
    when (it) {
        SharedEnums.GameMode.SINGLE -> "Single-Player"
        SharedEnums.GameMode.MULTI -> "Multiplayer"
    }
}
val modeOptionsDesc = listOf("(Player 1 vs. AI)", "(Player 1 vs. Player 2)")

//---------- Settings Screen Layout
//---------- PORTRAIT
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortraitContent(
    viewModel: SettingsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBG)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth().padding(top = 30.dp),
            horizontalArrangement = Arrangement.Start, // Align items to the start of the Row
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Go back to menu button
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(36.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(1.dp),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back to Menu"
                )
            }

            // Spacer that fills the remaining space between the button and title
            Spacer(modifier = Modifier.weight(.8f))

            //Title
            Text(
                text = "Settings",
                fontFamily = CooperBTBold,
                fontSize = 30.sp,

                )

            // Spacer to balance the remaining space on the right
            Spacer(modifier = Modifier.weight(1f))
        }

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
                            // Scroll Left
                            Button(
                                onClick = { viewModel.cyclePlayerOneProfile(false) },
                                modifier = Modifier.size(24.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                contentPadding = PaddingValues(1.5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowLeft,
                                    contentDescription = "Settings",
                                )
                            }

                            Image(
                                modifier = Modifier
                                    .size(48.dp),
                                painter = painterResource(id = viewModel.getPlayerOneProfileImage()),
                                contentDescription = "Player 1 Profile Pic"
                            )

                            // Scroll Right
                            Button(
                                onClick = { viewModel.cyclePlayerOneProfile(true) },
                                modifier = Modifier.size(24.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                contentPadding = PaddingValues(1.5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Settings",
                                )
                            }
                        }

                        // Player 1 Name
                        OutlinedTextField(
                            value = viewModel.playerOneName,
                            onValueChange = { newName -> viewModel.updatePlayerOneName(newName)},
                            label = { Text(text = "Player 1 name...") },
                        )

                        // Player 1 Colour
                        ExposedDropdownMenuBox(
                            expanded = viewModel.playerOneIsExpanded,
                            onExpandedChange = {viewModel.updatePlayerOneIsExpanded()},
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            TextField(
                                modifier = Modifier.menuAnchor(),
                                value = viewModel.playerOneColour.name.uppercase(),
                                label = { Text(text = "Colour") },
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.playerOneIsExpanded) }
                            )
                            ExposedDropdownMenu(
                                expanded = viewModel.playerOneIsExpanded,
                                onDismissRequest = { viewModel.updatePlayerOneIsExpanded() }
                            ) {
                                colours.forEachIndexed { index, text ->
                                    DropdownMenuItem(
                                        text = { Text(text = text) },
                                        onClick = {
                                            viewModel.updatePlayerOneColour(colours[index])
                                            viewModel.updatePlayerOneIsExpanded()
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp)) // Spacer

                //---------- Player 2 Customisation
                Box(modifier = Modifier.weight(1f))
                {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Player 2", fontSize = 20.sp)
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Scroll image right
                            Button(
                                onClick = { viewModel.cyclePlayerTwoProfile(false) },
                                modifier = Modifier.size(24.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                contentPadding = PaddingValues(1.5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowLeft,
                                    contentDescription = "Settings",
                                )
                            }

                            Image(
                                modifier = Modifier
                                    .size(48.dp),
                                painter = painterResource(id = viewModel.getPlayerTwoProfileImage()),
                                contentDescription = "Player 2 Profile Pic"
                            )

                            // Scroll image right
                            Button(
                                onClick = { viewModel.cyclePlayerTwoProfile(true) },
                                modifier = Modifier.size(24.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                contentPadding = PaddingValues(1.5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Settings",
                                )
                            }
                        }
                        // Player 2 Name
                        OutlinedTextField(
                            value = viewModel.playerTwoName,
                            onValueChange = { newName -> viewModel.updatePlayerTwoName(newName) },
                            label = { Text(text = "Player 2 name...") },
                        )

                        // Player 2 Colour
                        ExposedDropdownMenuBox(
                            expanded = viewModel.playerTwoIsExpanded,
                            onExpandedChange = { viewModel.updatePlayerTwoIsExpanded() },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            TextField(
                                modifier = Modifier.menuAnchor(),
                                value = viewModel.playerTwoColour.name.uppercase(),
                                label = { Text(text = "Colour") },
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.playerTwoIsExpanded) }
                            )
                            ExposedDropdownMenu(
                                expanded = viewModel.playerTwoIsExpanded,
                                onDismissRequest = { viewModel.updatePlayerTwoIsExpanded() }
                            ) {
                                colours.forEachIndexed { index, text ->
                                    DropdownMenuItem(
                                        text = { Text(text = text) },
                                        onClick = {
                                            viewModel.updatePlayerTwoColour(colours[index])
                                            viewModel.updatePlayerTwoIsExpanded()

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
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Enums to change bord sizes
                    val boardSizeDrawables = mapOf(
                        SharedEnums.BoardSize.SMALL to R.drawable.small,
                        SharedEnums.BoardSize.STANDARD to R.drawable.standard,
                        SharedEnums.BoardSize.LARGE to R.drawable.large
                    )

                    boardOptions.forEachIndexed { index, text ->
                        val boardSize = SharedEnums.BoardSize.entries[index]
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .selectable(
                                    selected = (boardSize == viewModel.selectedBoardOption),
                                    onClick = { viewModel.updateSelectedBoardOption(boardSize) }
                                )
                        ) {
                            Image(
                                painter = painterResource(id = boardSizeDrawables[boardSize] ?: R.drawable.small),
                                contentDescription = "Board size: $text",
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.DarkGray),
                                contentScale = ContentScale.Crop
                            )
                            RadioButton(
                                selected = (boardSize == viewModel.selectedBoardOption),
                                onClick = { viewModel.updateSelectedBoardOption(boardSize) }
                            )
                            Text(text = text)
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

                val modeImages = listOf(R.drawable.mode_1, R.drawable.mode_2) //List of images
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    modeOptions.forEachIndexed { index, text ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .selectable(
                                    selected = (SharedEnums.GameMode.entries[index] == viewModel.selectedModeOption),
                                    onClick = { viewModel.updateSelectedModeOption(SharedEnums.GameMode.entries[index]) }
                                )
                        ) {
                            Image(
                                painter = painterResource(id = modeImages[index]),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.Transparent),
                                contentScale = ContentScale.Crop
                            )
                            RadioButton(
                                selected = (SharedEnums.GameMode.entries[index] == viewModel.selectedModeOption),
                                onClick = { viewModel.updateSelectedModeOption(SharedEnums.GameMode.entries[index]) }
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
        }
    }
}


//---------- LANDSCAPE
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandscapeContent(
    viewModel: SettingsViewModel
) {
    Log.d("SettingsScreen", "Current Orientation: ${viewModel.playerOneColour}")
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
            // LEFT SIDE
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(GreyBG)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Go back to menu button
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(36.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(1.dp),
                        colors = ButtonDefaults.buttonColors(Color.DarkGray)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Menu"
                        )
                    }

                    // Settings title
                    Text(
                        text = "Settings",
                        fontFamily = CooperBTBold,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //---------- Player 1 Customisation
                    Box(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "Player 1", fontSize = 20.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Scroll Left
                                    Button(
                                        onClick = { viewModel.cyclePlayerOneProfile(false) },
                                        modifier = Modifier.size(24.dp),
                                        shape = CircleShape,
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                        contentPadding = PaddingValues(1.5.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowLeft,
                                            contentDescription = "Settings",
                                        )
                                    }

                                    Image(
                                        modifier = Modifier
                                            .size(48.dp),
                                        painter = painterResource(id = viewModel.getPlayerOneProfileImage()),
                                        contentDescription = "Player 1 Profile Pic"
                                    )

                                    // Scroll Right
                                    Button(
                                        onClick = { viewModel.cyclePlayerOneProfile(true) },
                                        modifier = Modifier.size(24.dp),
                                        shape = CircleShape,
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                        contentPadding = PaddingValues(1.5.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = "Settings",
                                        )
                                    }
                                }
                            }

                            Column {
                                // Player 1 Name
                                OutlinedTextField(
                                    value = viewModel.playerOneName,
                                    onValueChange = { newName -> viewModel.updatePlayerOneName(newName) },
                                    label = { Text(text = "Player 1 name...") },
                                )

                                // Player 1 Colour
                                ExposedDropdownMenuBox(
                                    expanded = viewModel.playerOneIsExpanded,
                                    onExpandedChange = { viewModel.updatePlayerOneIsExpanded() },
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    TextField(
                                        modifier = Modifier.menuAnchor(),
                                        value = viewModel.playerOneColour.name.uppercase(),
                                        label = { Text(text = "Colour") },
                                        onValueChange = {},
                                        readOnly = true,
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.playerOneIsExpanded) }
                                    )
                                    ExposedDropdownMenu(
                                        expanded = viewModel.playerOneIsExpanded,
                                        onDismissRequest = { viewModel.updatePlayerOneIsExpanded() }
                                    ) {
                                        colours.forEachIndexed { index, text ->
                                            DropdownMenuItem(
                                                text = { Text(text = text) },
                                                onClick = {
                                                    viewModel.updatePlayerOneColour(colours[index])
                                                    viewModel.updatePlayerOneIsExpanded()
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

                    //---------- Player 2 Customisation
                    Box(modifier = Modifier.weight(1f)) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = "Player 2", fontSize = 20.sp)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        // Scroll Left
                                        Button(
                                            onClick = { viewModel.cyclePlayerTwoProfile(false) },
                                            modifier = Modifier.size(24.dp),
                                            shape = CircleShape,
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                            contentPadding = PaddingValues(1.5.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.KeyboardArrowLeft,
                                                contentDescription = "Settings",
                                            )
                                        }

                                        Image(
                                            modifier = Modifier
                                                .size(64.dp),
                                            painter = painterResource(id = viewModel.getPlayerTwoProfileImage()),
                                            contentDescription = "Player 2 Profile Pic"
                                        )

                                        // Scroll Right
                                        Button(
                                            onClick = { viewModel.cyclePlayerTwoProfile(true) },
                                            modifier = Modifier.size(24.dp),
                                            shape = CircleShape,
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                            contentPadding = PaddingValues(1.5.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.KeyboardArrowRight,
                                                contentDescription = "Settings",
                                            )
                                        }
                                    }
                                }
                                Column {
                                    // Player 2 Name
                                    OutlinedTextField(
                                        value = viewModel.playerTwoName,
                                        onValueChange = { newName -> viewModel.updatePlayerTwoName(newName) },
                                        label = { Text(text = "Player 2 name...") },
                                    )

                                    // Player 2 Colour
                                    ExposedDropdownMenuBox(
                                        expanded = viewModel.playerTwoIsExpanded,
                                        onExpandedChange = { viewModel.updatePlayerTwoIsExpanded() },
                                        modifier = Modifier.padding(top = 8.dp)
                                    ) {
                                        TextField(
                                            modifier = Modifier.menuAnchor(),
                                            label = { Text(text = "Colour") },
                                            value = viewModel.playerTwoColour.name.uppercase(),
                                            onValueChange = {},
                                            readOnly = true,
                                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.playerTwoIsExpanded) }
                                        )
                                        ExposedDropdownMenu(
                                            expanded = viewModel.playerTwoIsExpanded,
                                            onDismissRequest = { viewModel.updatePlayerTwoIsExpanded() }
                                        ) {
                                            colours.forEachIndexed { index, text ->
                                                DropdownMenuItem(
                                                    text = { Text(text = text) },
                                                    onClick = {
                                                        viewModel.updatePlayerTwoColour(colours[index])
                                                        viewModel.updatePlayerTwoIsExpanded()
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

            // RIGHT SIDE
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
                            .weight(1f)
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
                            val boardSizeDrawables = mapOf(
                                SharedEnums.BoardSize.SMALL to R.drawable.small,
                                SharedEnums.BoardSize.STANDARD to R.drawable.standard,
                                SharedEnums.BoardSize.LARGE to R.drawable.large
                            )

                            val boardOptions = listOf("Small (6x5)", "Standard (7x6)", "Large (8x7)")

                            boardOptions.forEachIndexed { index, text ->
                                val boardSize = SharedEnums.BoardSize.entries[index]
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .selectable(
                                            selected = (boardSize == viewModel.selectedBoardOption),
                                            onClick = { viewModel.updateSelectedBoardOption(boardSize) }
                                        )
                                ) {
                                    Image(
                                        painter = painterResource(id = boardSizeDrawables[boardSize] ?: R.drawable.small),
                                        contentDescription = "Board size: $text",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .background(Color.DarkGray),
                                        contentScale = ContentScale.Crop
                                    )
                                    RadioButton(
                                        selected = (boardSize == viewModel.selectedBoardOption),
                                        onClick = { viewModel.updateSelectedBoardOption(boardSize) }
                                    )
                                    Text(text = text)
                                }
                            }
                        }
                    }

                    //---------- Mode Customisation
                    Column(
                        modifier = Modifier
                            .weight(1f)
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
                                            selected = (SharedEnums.GameMode.entries[index] == viewModel.selectedModeOption),
                                            onClick = { viewModel.updateSelectedModeOption(SharedEnums.GameMode.entries[index]) }
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
                                        selected = (SharedEnums.GameMode.entries[index] == viewModel.selectedModeOption),
                                        onClick = { viewModel.updateSelectedModeOption(SharedEnums.GameMode.entries[index]) }
                                    )
                                    Text(text = text)
                                    Text(
                                        text = modeOptionsDesc[index],
                                        fontSize = 12.sp
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

//---------- Preview
@Preview
@Composable
fun SettingsPrev(){
    val viewModel = viewModel<SettingsViewModel>()
    SettingsScreen(viewModel = viewModel)
}