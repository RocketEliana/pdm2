package data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.Pokemon
import model.User

@Database(
    entities = [
        User::class,
        Pokemon::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {

    abstract fun ususerDao(): UserDao
    abstract fun pokemonDao(): PokemonDao

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(
            context: Context
        ): AppDataBase {

            return INSTANCE ?: synchronized(this) {

                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "pokemon_db"
                    )
                        .allowMainThreadQueries()
                        .build()

                INSTANCE = instance
                instance
            }
        }
    }
}