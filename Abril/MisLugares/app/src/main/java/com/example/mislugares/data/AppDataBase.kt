package com.example.mislugares.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mislugares.model.Lugar

@Database(entities = [Lugar::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun lugarDao(): LugarDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "lugar_db"
                )
                    .allowMainThreadQueries()   // Solo para prácticas
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
