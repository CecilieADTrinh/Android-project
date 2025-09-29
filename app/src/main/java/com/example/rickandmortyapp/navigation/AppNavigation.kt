package com.example.rickandmortyapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyapp.screens.createcharacter.CreateCharacterScreen
import com.example.rickandmortyapp.screens.createcharacter.CreateCharacterViewModel
import com.example.rickandmortyapp.screens.createdcharacter_list.CreatedListScreen
import com.example.rickandmortyapp.screens.createdcharacter_list.CreatedListViewModel
import com.example.rickandmortyapp.screens.deletecharacter.DeleteCharacterScreen
import com.example.rickandmortyapp.screens.deletecharacter.DeleteCharacterViewModel
import com.example.rickandmortyapp.screens.favouritecharacters.FavouriteCharactersScreen
import com.example.rickandmortyapp.screens.favouritecharacters.FavouriteCharactersViewModel
import com.example.rickandmortyapp.screens.showcharacters.CharacterViewModel
import com.example.rickandmortyapp.screens.showcharacters.ShowAllCharacterScreen

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object CreatedCharacters

@Serializable
object MakeCharacter

@Serializable
object DeleteCharacter

@Serializable
object FavouriteCharacter

@Composable
fun AppNavigation(
    characterViewModel: CharacterViewModel,
    createCharacterViewModel: CreateCharacterViewModel,
    createdListViewModel: CreatedListViewModel,
    deleteCharacterViewModel: DeleteCharacterViewModel,
    favouriteCharactersViewModel: FavouriteCharactersViewModel
) {

    val navController = rememberNavController()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color(126, 149, 51)
            ) {
                NavigationBarItem(
                    selected = selectedItemIndex == 0,
                    onClick = {
                        selectedItemIndex = 0
                        navController.navigate(Home)
                    },
                    icon = {
                        if (selectedItemIndex == 0) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Home,
                                contentDescription = null
                            )
                        }
                    },
                    label = {
                        Text(
                            text = "Characters",
                            color = Color.White
                        )
                    },
                )

                NavigationBarItem(
                    selected = selectedItemIndex == 1,
                    onClick = {
                        selectedItemIndex = 1
                        navController.navigate(CreatedCharacters)
                    },
                    icon = {
                        if (selectedItemIndex == 1) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.List,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.List,
                                contentDescription = null
                            )
                        }
                    },
                    label = {
                        Text(
                            text = "Characters created",
                            color = Color.White
                        )

                    },
                )

                NavigationBarItem(
                    selected = selectedItemIndex == 2,
                    onClick = {
                        selectedItemIndex = 2
                        navController.navigate(MakeCharacter)
                    },
                    icon = {
                        if (selectedItemIndex == 2) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Create,
                                contentDescription = null
                            )
                        }
                    },
                    label = {
                        Text(
                            text = "Create character",
                            color = Color.White
                        )
                    },
                )

                NavigationBarItem(
                    selected = selectedItemIndex == 3,
                    onClick = {
                        selectedItemIndex = 3
                        navController.navigate(DeleteCharacter)
                    },
                    icon = {
                        if (selectedItemIndex == 3) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = null
                            )
                        }
                    },
                    label = {
                        Text(
                            text = "Delete character",
                            color = Color.White
                        )
                    },
                )

                NavigationBarItem(
                    selected = selectedItemIndex == 4,
                    onClick = {
                        selectedItemIndex = 4
                        navController.navigate(FavouriteCharacter)
                    },
                    icon = {
                        if (selectedItemIndex == 4) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = null
                            )
                        }
                    },
                    label = {
                        Text(
                            text = "Favourites",
                            color = Color.White
                        )
                    },
                )
            }
        }

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Home
                ) {
                    composable<Home> {
                        ShowAllCharacterScreen(
                            characterViewModel,
                            favouriteCharactersViewModel
                        )
                    }

                    composable<CreatedCharacters> {
                        CreatedListScreen(createdListViewModel)
                    }

                    composable<MakeCharacter> {
                        CreateCharacterScreen(createCharacterViewModel)
                    }

                    composable<DeleteCharacter> {
                        DeleteCharacterScreen(deleteCharacterViewModel)
                    }

                    composable<FavouriteCharacter> {
                        FavouriteCharactersScreen(favouriteCharactersViewModel)
                    }
                }
            }
        }
    }
}