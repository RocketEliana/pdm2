package data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.Actividad
import model.Plan
import model.Senda

@Database(entities = [Senda::class, Actividad::class, Plan::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun sendaDao(): SendaDao
    abstract fun planDao(): PlanDao
    abstract fun actividadDao(): ActividadDao

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
