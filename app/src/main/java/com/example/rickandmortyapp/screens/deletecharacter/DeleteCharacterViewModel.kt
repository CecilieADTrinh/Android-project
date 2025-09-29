package com.example.rickandmortyapp.screens.deletecharacter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.room.CharacterRepository
import com.example.rickandmortyapp.data.services.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeleteCharacterViewModel : ViewModel() { // Arver fra ViewModel

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters = _characters.asStateFlow()

    fun setCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _characters.value = CharacterRepository.getCreatedCharacters() // Fra Db
            } catch (e: Exception) {
                Log.e(
                    "CreateCharacterViewModel",
                    "Error, failed to fetch characters from database.", e
                )
            }
        }
    }

    // ID må tas tak i for å slette hele karakteren
    fun deleteCharacter(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val characterDeleted =
                    CharacterRepository.deleteCharacter(character) // Sletter fra Db
                if (characterDeleted == 1) { // Hvis slettingen var suksessfull
                    val currentList = _characters.value // Lagrer midlertidig listen
                    val afterDeleteList = currentList.filter { it != character }
                    _characters.value =
                        afterDeleteList // Oppdaterer ny liste filtrert uten den slettede
                    // Setter fra state og grensesnitt oppdateres
                    Log.d("DeleteCharacterViewModel", "Successfully deleted: ${character.name}.")
                } else {
                    Log.e(
                        "DeleteCharacterViewModel",
                        "Failed to delete: ${character.name} from database."
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "DeleteCharacterViewModel",
                    "Error. Failed to delete: ${character.name} ${e.message}.",
                    e
                )
            }
        }
    }
}