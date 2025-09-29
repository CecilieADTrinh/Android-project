package com.example.rickandmortyapp.screens.deletecharacter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickandmortyapp.components.CharacterItem
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.example.rickandmortyapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DeleteCharacterScreen(deleteCharacterViewModel: DeleteCharacterViewModel) {

    val characters by deleteCharacterViewModel.characters.collectAsState() // Karakterer oppdateres automatisk når state endres
    var deleteMessage by remember { mutableStateOf("") } // Holder meldingen
    val coroutineScope = rememberCoroutineScope() // Timeout beskjed til bruker

    LaunchedEffect(Unit) {
        deleteCharacterViewModel.setCharacters() // Oppdateres 1x
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.pistol),
                contentDescription = "Rick with pistol",
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterEnd)
            )
            Text(
                text = "Delete characters you created!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    brush = Brush.linearGradient( // Gradering av farge på overskrift
                        colors = listOf(Color(43, 70, 37), Color(126, 149, 51))
                    )
                ),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = 100.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (deleteMessage.isNotEmpty()) {
            Text(
                text = deleteMessage,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(vertical = 6.dp)
            )
        }

        if (characters.isEmpty()) {
            Text("No characters found in the database.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(characters) { character ->
                    var isExpanded by remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isExpanded = !isExpanded },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = character.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Icon(
                                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                                )
                            }
                            IconButton(onClick = {
                                deleteCharacterViewModel.deleteCharacter(character)
                                deleteMessage = "${character.name} has been deleted!"
                                coroutineScope.launch {
                                    delay(3500)  // 2 sek forsinkelse
                                    deleteMessage = ""         // Tømmer tekst etter 2 sek
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete Character",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(4.dp))
                            CharacterItem(
                                character = character,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}