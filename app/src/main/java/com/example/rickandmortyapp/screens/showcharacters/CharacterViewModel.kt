package com.example.rickandmortyapp.screens.showcharacters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.room.CharacterRepository
import com.example.rickandmortyapp.data.services.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Ansvar for kobling mellom Repository og Screen

// Implementerer ViewModel import
class CharacterViewModel : ViewModel() {

    private val _allCharacters = MutableStateFlow<List<Character>>(emptyList())
    val allCharacters = _allCharacters.asStateFlow()

    private val _filteredCharacters = MutableStateFlow<List<Character>>(emptyList())
    val filteredCharacters = _filteredCharacters.asStateFlow()

    fun setAllCharacters() { // Fetcher data direkte fra Repository
        viewModelScope.launch {
            try {
                _allCharacters.value =
                    CharacterRepository.getAllCharacters() // Oppdateres og vises på UI
                _filteredCharacters.value = CharacterRepository.getAllCharacters()
                // Blir "emitet" til composable screen
                Log.d("CharacterViewModel", "Successfully fetched all characters.")
            } catch (e: Exception) {
                Log.e(
                    "CharacterViewModel",
                    "Error. Failed to fetch characters from repository.", e
                )
            }
        }
    }

    fun filterByGender(gender: String) {
        try {
            _filteredCharacters.value = if (gender == "All") {
                _allCharacters.value
            } else {
                _allCharacters.value.filter { it.gender == gender }
            }
            Log.d(
                "CharacterViewModel", "" +
                        "Filtered characters by gender: $gender"
            )


        } catch (e: Exception) {
            Log.e(
                "CharacterViewModel",
                "Error. Failed to filter characters by $gender ${e.message}", e
            )
        }
    }
}
