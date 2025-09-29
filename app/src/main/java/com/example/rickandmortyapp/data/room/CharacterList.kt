package com.example.rickandmortyapp.data.room

import com.example.rickandmortyapp.data.services.Character


// Variabelnavn må matche med WebAPIET sitt JSON arraynavn. "results": [ {
data class CharacterList(
    val results: List<Character>
)
