package com.example.rickandmortyapp.data.room

// Konfigurering + definering av database som arver fra RoomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmortyapp.data.services.Character

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}