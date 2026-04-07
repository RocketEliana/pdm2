package data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.Bar

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
                    "bar_db"
                )
                    .allowMainThreadQueries()   // Solo para prácticas
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}