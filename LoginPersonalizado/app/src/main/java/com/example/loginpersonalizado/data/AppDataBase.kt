package com.example.loginpersonalizado.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.loginpersonalizado.model.Pokemon
import com.example.loginpersonalizado.model.User

@Database(entities = [User::class],[Pokemon::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pokemonDao():PokemonDao
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_pokemon_db"
                )
                    .allowMainThreadQueries() // Solo para práctica
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}