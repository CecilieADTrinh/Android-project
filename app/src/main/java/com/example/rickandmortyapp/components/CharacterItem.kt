package com.example.rickandmortyapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.example.rickandmortyapp.data.services.Character

// Her skrives ut karakterene og informasjon om de
@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
    isFavourite: Boolean = false, // Hvis karakter er favoritt
    clickFavourite: ((Character) -> Unit)? = null,
    onDelete: (() -> Unit)? = null, // Gjør handling, men returnerer ingenting (lambda)
) { // Sender informasjon fra Character Data Class

    Column {
        Text(text = "Id: ${character.id.toString()}")
        Text(text = "Name: ${character.name}")
        Text(text = "Gender: ${character.gender}")
        Text(text = "Status: ${character.status}")
        Text(text = "Species: ${character.species}")
        AsyncImage(
            model = character.image.toString(),
            contentDescription = character.name
        )
    }

    // Delete icon
    if (onDelete != null) {
        IconButton(onClick = { onDelete() }) {
            Row {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete icon"
                )
            }
        }
    }

    // Favourite icon
    Row {
        if (clickFavourite != null) {
            IconButton(onClick =
            { clickFavourite(character) }) {
                Icon(
                    imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavourite) "Removed favourite icon" else "Favourited icon"
                )
            }
        }
    }
}
