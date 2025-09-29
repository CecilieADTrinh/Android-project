package com.example.rickandmortyapp.screens.createcharacter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.services.Character

@Composable
fun CreateCharacterScreen(createCharacterViewModel: CreateCharacterViewModel) {

    val characters = createCharacterViewModel.characters.collectAsState()

    var newCharacterName by remember {
        mutableStateOf("")
    }
    var newCharacterStatus by remember {
        mutableStateOf("")
    }
    var newCharacterSpecies by remember {
        mutableStateOf("")
    }
    var newCharacterGender by remember {
        mutableStateOf("")
    }

    // Variabler som benyttes for at bruker MÅ fylle inn alle tekstfeltene
    var nameEmpty by remember {
        mutableStateOf(false)
    }
    var statusEmpty by remember {
        mutableStateOf(false)
    }
    var speciesEmpty by remember {
        mutableStateOf(false)
    }
    var genderEmpty by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        createCharacterViewModel.setCharacters() // Fetcher karakterene fra Db
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.rickandmorty),
            contentDescription = "Rick and Morty",
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = "Create new Rick and Morty-character",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(20.dp))

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextField(
                value = newCharacterName,
                onValueChange = {
                    newCharacterName = it
                    nameEmpty = false
                },
                label = {
                    Text(text = "Character")
                }
            )
            if (nameEmpty) {
                Text(
                    text =
                    "Name is required.",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            TextField(
                value = newCharacterStatus,
                onValueChange = {
                    newCharacterStatus = it
                    statusEmpty = false
                },
                label = {
                    Text(text = "Status")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text // Displayer keyboard
                )
            )

            if (statusEmpty) {
                Text(
                    text =
                    "Status is required.",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            TextField(
                value = newCharacterSpecies,
                onValueChange = {
                    newCharacterSpecies = it
                    speciesEmpty = false
                },
                label = {
                    Text(text = "Species")
                }
            )
            if (speciesEmpty) {
                Text(
                    text =
                    "Species is required.",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            TextField(
                value = newCharacterGender,
                onValueChange = {
                    newCharacterGender = it
                    genderEmpty = false
                },
                label = {
                    Text(text = "Gender")
                }
            )

            if (genderEmpty) {
                Text(
                    text =
                    "Gender is required",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }

        val defaultImageUrl = "https://rickyandmortyapi.com/api/character/avatar/1.jpeg" // Bruker skal ikke fylle ut bilde

        Button(
            onClick = { // Ny karakter, hvis alle feltene er fylt.
                nameEmpty = newCharacterName.isBlank()
                statusEmpty = newCharacterStatus.isBlank()
                speciesEmpty = newCharacterSpecies.isBlank()
                genderEmpty = newCharacterGender.isBlank()

                if (!nameEmpty && !statusEmpty && !speciesEmpty && !genderEmpty) {
                    createCharacterViewModel.insertCharacter(
                        Character(
                            name = newCharacterName,
                            status = newCharacterStatus,
                            species = newCharacterSpecies,
                            gender = newCharacterGender,
                            image = defaultImageUrl
                        )
                    )
                    newCharacterName = "" // Tømmer tekstfeltene
                    newCharacterStatus = ""
                    newCharacterSpecies = ""
                    newCharacterGender = ""
                }
            },
            modifier = Modifier
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(121, 131, 87),
                contentColor = Color.White
            )
        ) {

            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = "Save to database"
            )
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add icon"
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        if (characters.value.isEmpty()) {
            Text(
                text = "You have not created a character yet.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "Your new characters: ",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            LazyColumn(
                reverseLayout = true, // Denne gjør at nye elementer vises øverst
            ) {
                items(characters.value) { character ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(50.dp)
                            .border(
                                1.dp,
                                Color(129, 164, 112),
                                RoundedCornerShape(25.dp)
                            )
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color(193, 218, 180)),
                    ) {
                        Text(
                            text = "Name: ${character.name}",
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

