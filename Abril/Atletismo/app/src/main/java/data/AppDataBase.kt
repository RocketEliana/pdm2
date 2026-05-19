package data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.Atleta
import model.Categoria
import model.Competicion

@Database(entities = [Atleta::class, Competicion::class, Categoria::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun atletaDao(): AtletaDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun competicionDao(): CompeticionDao

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