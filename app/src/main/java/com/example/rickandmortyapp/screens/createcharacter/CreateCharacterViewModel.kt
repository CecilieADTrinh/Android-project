package com.example.rickandmortyapp.screens.createcharacter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.room.CharacterRepository
import com.example.rickandmortyapp.data.services.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateCharacterViewModel : ViewModel() {

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

    fun insertCharacter(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newCharacterId = CharacterRepository.insertCharacter(character)
                if (newCharacterId != -1L) {
                    val newCharacter = character.copy(id = newCharacterId.toInt())
                    Log.d(
                        "CreateCharacterViewModel",
                        "Successfully inserted: ${newCharacter.name}"
                    )
                    _characters.value += newCharacter
                } else {
                    Log.e( // Error message
                        "CreateCharacterViewModel",
                        "Could not insert character: ${character.name} in the database."
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "CreateCharacterViewModel",
                    "Error. Failed to insert character to database ${e.message} ",
                    e
                )
            }
        }
    }
}