package com.example.rickandmortyapp.data.room

import android.util.Log
import android.content.Context
import androidx.room.Room
import com.example.rickandmortyapp.data.services.Character
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.SQLException

// Repository objektet kommuniserer med Db for å utføre CRUD
object CharacterRepository {

    private lateinit var appDatabase: AppDatabase
    private val _characterDao by lazy { appDatabase.characterDao() }

    // Setter opp Room Database og initialiserer med context FØR CRUD
    fun initializeDatabase(context: Context) {
        appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "character-database"
        ).build()
    }

    // Her fetches karakterene fra WebAPIet:
    // Http kall - opprettelse av klient, retrofit, bruke bibliotek og opprette serviceobjekt
    private val _okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        ).build()

    private val _retrofit = Retrofit.Builder()
        .client(_okHttpClient)
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(
            GsonConverterFactory.create()
        ).build()

    private val _characterService = _retrofit.create(CharacterService::class.java)

    // Hente alle karakterer fra WebAPIet og returnerer "resultat" arrayet
    suspend fun getAllCharacters(): List<Character> {
        try {
            val response = _characterService.getAllCharacters()

            if (response.isSuccessful) {
                return response.body()?.results ?: emptyList()
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            Log.e("Retrofit service error, can not get characters.", e.toString())
            return emptyList()
        }
    }

    // Setter karakterer inn på Room Database, med exception
    suspend fun insertCharacter(character: Character): Long {
        try {
            return _characterDao.insertCharacter(character)
        } catch (e: SQLException) {
            Log.e("SQLException", "Could not insert character: ${character.name}. ${e.message}")
            return -1L
        }
    }

    // Henter karakterene fra database
    suspend fun getCreatedCharacters(): List<Character> {
        return try {
            _characterDao.getCharacters() // Fetcher fra Room
        } catch (e: SQLException) {
            Log.e("Databasefeil", "Could not fetch from database. ${e.message}")
            emptyList()
        }

    }

    // Sletter karakterene fra database
    suspend fun deleteCharacter(character: Character): Int {
        try {
            return _characterDao.deleteCharacter(character)
        } catch (e: SQLException) {
            Log.e("SQLException", "Can not delete character: ${character.name}. ${e.message}")
            return 0 // Indikerer failure
        }
    }
}

