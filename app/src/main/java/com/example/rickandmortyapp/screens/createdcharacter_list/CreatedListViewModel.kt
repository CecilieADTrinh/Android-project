package com.example.rickandmortyapp.screens.createdcharacter_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.room.CharacterRepository
import com.example.rickandmortyapp.data.services.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Kommuniserer med Repository for å fetche data om karakterene som er opprettet.
// Viser data til UI gjennom StateFlow

class CreatedListViewModel : ViewModel() {

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    private val _filteredCharacters = MutableStateFlow<List<Character>>(emptyList())
    val filteredCharacters: StateFlow<List<Character>> = _filteredCharacters

    fun setCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val characters = CharacterRepository.getCreatedCharacters()
                _characters.value = characters
                _filteredCharacters.value = characters
                Log.d("CreatedListViewModel", "Succesfully fetched characters from database.")
            } catch (e: Exception) {
                Log.e(
                    "CreatedListViewModel",
                    "Error. Can not fetch characters from database: ${e.message}", e
                )
            }
        }
    }

    fun filterByGender(gender: String) {
        try {
            _filteredCharacters.value = if (gender == "All") {
                _characters.value
            } else {
                _characters.value.filter { it.gender.equals(gender, ignoreCase = true) }
            }
        } catch (e: Exception) {
            Log.e(
                "CreatedListViewModel",
                "Error. Can not filter characters by gender: ${e.message}", e
            )
        }
    }
}

