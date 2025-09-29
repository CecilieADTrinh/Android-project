package com.example.rickandmortyapp.data.services

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey(autoGenerate = true) // Database autogenerer Id
    // Har med instansvariabler som matcher JSON webAPIet rickandmortyapi.com/api/character
    val id: Int = 0,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)


