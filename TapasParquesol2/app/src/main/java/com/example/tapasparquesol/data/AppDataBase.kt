package com.example.tapasparquesol.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tapasparquesol.model.Bar

@Database(entities = [Bar::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun barDao(): BarDao
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "producto_db"
                )
                    .allowMainThreadQueries() // Solo para práctica
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}