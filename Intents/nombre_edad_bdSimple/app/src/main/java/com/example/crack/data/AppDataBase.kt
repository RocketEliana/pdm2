package com.example.crack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Persona::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    // -------------------------------------------------------------------------
    // Esta función abstracta permite a Room generar la implementación del DAO.
    // Room creará un objeto que implementa pERSONADao automáticamente.
    // -------------------------------------------------------------------------
    abstract fun personaDao(): PersonaDao

    // -------------------------------------------------------------------------
    // Companion object: permite crear una única instancia de la base de datos.
    // Esto sigue el patrón Singleton, necesario para evitar múltiples conexiones.
    // -------------------------------------------------------------------------
    companion object {

        // ---------------------------------------------------------------------
        // @Volatile: garantiza que la variable se lea siempre desde memoria
        // principal, y no desde caché de hilos. Muy importante en Singletons.
        // INSTANCE guardará la base de datos ya creada.
        // ---------------------------------------------------------------------
        @Volatile
        private var INSTANCE: AppDataBase? = null

        // ---------------------------------------------------------------------
        // getDatabase(context): devuelve la instancia única de la base de datos.
        // Si no existe, la crea dentro de un bloque synchronized.
        // ---------------------------------------------------------------------
        fun getDatabase(context: Context): AppDataBase {

            // Si INSTANCE tiene ya un valor, lo devolvemos.
            // Si es null, ejecutamos la parte derecha (synchronized {...}).
            return INSTANCE ?: synchronized(this) {

                // -----------------------------------------------------------------
                // Room.databaseBuilder crea la base de datos:
                // - context.applicationContext → evita fugas de memoria
                // - AppDataBase::class.java → esta misma clase como referencia
                // - "discos_db" → nombre del archivo de base de datos
                // -----------------------------------------------------------------
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "discos_db"
                )
                    // -----------------------------------------------------------------
                    // allowMainThreadQueries() permite hacer consultas en el hilo principal.
                    // SOLO DEBE USARSE EN CLASE O PRÁCTICAS.
                    // En producción puede bloquear la app (ANR).
                    // -----------------------------------------------------------------
                    .allowMainThreadQueries()
                    .build()

                // Guardamos la instancia en INSTANCE
                INSTANCE = instance

                // devolvemos la instancia creada
                instance
            }
        }
    }
}