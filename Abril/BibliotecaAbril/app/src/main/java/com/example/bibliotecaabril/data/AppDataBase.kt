package com.example.bibliotecaabril.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bibliotecaabril.model.Biblioteca
import com.example.bibliotecaabril.model.Traslado

@Database(entities = [Biblioteca::class, Traslado::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun bibliotecaDao(): BibliotecaDao
    abstract fun trasladoDao(): TrasladoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_db"
                )
                    .allowMainThreadQueries()   // Solo para prácticas
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}