package data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.RutaConciertos
import model.Sala

@Database(entities = [Sala::class, RutaConciertos::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun salaDao(): SalaDao
    abstract fun rutaDao(): RutaConciertosDao
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "indie_db"
                )
                    .allowMainThreadQueries() // Solo para práctica
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}