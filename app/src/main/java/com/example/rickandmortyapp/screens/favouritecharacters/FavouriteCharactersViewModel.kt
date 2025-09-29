package com.example.rickandmortyapp.screens.favouritecharacters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.services.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouriteCharactersViewModel : ViewModel() {
    private val _favCharacters = MutableStateFlow<List<Character>>(emptyList())
    val favCharacters = _favCharacters.asStateFlow()

    fun addToFavorites(character: Character) {
        viewModelScope.launch {
            try {
                val currentFavorites = _favCharacters.value.toMutableList()
                if (!currentFavorites.contains(character)) {
                    currentFavorites.add(character)
                    _favCharacters.value = currentFavorites
                    Log.d(
                        "FavouriteCharactersViewModel",
                        "${character.name} succesfully added to favourites."
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "FavouriteCharactersViewModel",
                    "Error. Failed to add: ${character.name} to favourites.", e
                )
            }
        }
    }

    fun removeFromFavorites(character: Character) {
        viewModelScope.launch {
            try {
                val currentFavorites = _favCharacters.value.toMutableList()
                if (currentFavorites.remove(character)) {
                    _favCharacters.value = currentFavorites
                } else {
                    Log.e(
                        "FavouriteCharactersViewModel",
                        "Error. Failed to remove: ${character.name} from favourites."
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "FavouriteCharactersViewModel",
                    "Could not remove character ${character.name} from favourite ${e.message}.", e
                )
            }
        }
    }
}
