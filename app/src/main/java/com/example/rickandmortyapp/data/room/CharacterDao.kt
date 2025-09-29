package com.example.rickandmortyapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.rickandmortyapp.data.services.Character

// Interface definerer metoder og spørringer som skal gjøres mot databasen

// Spørring om CRUD
@Dao
interface CharacterDao {
    @Query("SELECT * FROM Character")
    suspend fun getCharacters(): List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character): Long
// Long pga ny id blir lagret som karakterer som gis tilbake

    @Delete // Sletting returnerer int (0=dårlig, 1=bra)
    suspend fun deleteCharacter(character: Character): Int
}