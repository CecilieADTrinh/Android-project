package com.example.rickandmortyapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.rickandmortyapp.data.room.CharacterRepository
import com.example.rickandmortyapp.navigation.AppNavigation
import com.example.rickandmortyapp.screens.createcharacter.CreateCharacterViewModel
import com.example.rickandmortyapp.screens.createdcharacter_list.CreatedListViewModel
import com.example.rickandmortyapp.screens.deletecharacter.DeleteCharacterViewModel
import com.example.rickandmortyapp.screens.favouritecharacters.FavouriteCharactersViewModel
import com.example.rickandmortyapp.screens.showcharacters.CharacterViewModel
import com.example.rickandmortyapp.ui.theme.RickAndMortyAppTheme

// ViewModels som tilhører hver screen

class MainActivity : ComponentActivity() {
    // Viser alle karakterer skjerm 1
    private val _characterViewModel: CharacterViewModel by viewModels()

    // Lage karakterer skjerm 3
    private val _createCharacterViewModel: CreateCharacterViewModel by viewModels()

    // Vise karakterer lagd skjerm 2
    private val _createdListViewModel: CreatedListViewModel by viewModels()

    // Slette karakterer skjerm 4
    private val _deleteCharacterViewModel: DeleteCharacterViewModel by viewModels()

    // Favorittkarakterer skjerm 5
    private val _favouriteCharactersViewModel: FavouriteCharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Repository må initiere Db herfra, så den er tilgjengelig før CRUD-operasjoner
        CharacterRepository.initializeDatabase(applicationContext)

        enableEdgeToEdge()
        setContent {
            RickAndMortyAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        // Sender ViewModel til Screen
                        AppNavigation(
                            _characterViewModel,
                            _createCharacterViewModel,
                            _createdListViewModel,
                            _deleteCharacterViewModel,
                            _favouriteCharactersViewModel
                        )

                    }
                }
            }
        }
    }
}
