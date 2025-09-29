package com.example.rickandmortyapp.screens.showcharacters

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.screens.favouritecharacters.FavouriteCharactersViewModel

@Composable
fun ShowAllCharacterScreen(
    characterViewModel: CharacterViewModel,
    favouriteCharactersViewModel: FavouriteCharactersViewModel
) {
    val characters by characterViewModel.filteredCharacters.collectAsState()
    val favoriteCharacters by favouriteCharactersViewModel.favCharacters.collectAsState()
    var selectedGender by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        characterViewModel.setAllCharacters()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.guns),
            contentDescription = "Rick gun",
            modifier = Modifier
                .size(280.dp)
        )

        Text(
            text = "Rick and Morty Characters",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(Color(43, 70, 37), Color(126, 149, 51))
                )
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        GenderDropdown(
            selectedGender = selectedGender,
            onGenderSelected = { gender ->
                selectedGender = gender
                characterViewModel.filterByGender(gender)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (characters.isEmpty()) {
                item {
                    Text(
                        text = "No characters found.",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(characters) { character ->
                    val isFavorite = favoriteCharacters.contains(character)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .shadow(4.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(214, 222, 189)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f) // Padding
                            ) {
                                Text(text = "Id: ${character.id}")
                                Text(text = "Name: ${character.name}")
                                Text(text = "Gender: ${character.gender}")
                                Text(text = "Status: ${character.status}")
                                Text(text = "Species: ${character.species}")
                            }

                            AsyncImage(
                                model = character.image,
                                contentDescription = character.name,
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color(255, 165, 0), CircleShape)
                            )

                            IconButton(
                                onClick = {
                                    if (isFavorite) {
                                        favouriteCharactersViewModel.removeFromFavorites(character)
                                    } else {
                                        favouriteCharactersViewModel.addToFavorites(character)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                                    tint = if (isFavorite) Color.Red else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenderDropdown(selectedGender: String, onGenderSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf("All", "Male", "Female", "unknown")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .width(200.dp)
                .align(Alignment.Center),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(121, 131, 87)
            )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Filter Dropdown Menu",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = selectedGender)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(200.dp)
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = gender,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    }
                )
            }
        }
    }
}
