package com.example.rickandmortyapp.data.room

import retrofit2.Response
import retrofit2.http.GET

// Henter endepunkt for Web URL rickandmortyapi.com/api/character
interface CharacterService {
    @GET("character")
    suspend fun getAllCharacters(): Response<CharacterList>
}