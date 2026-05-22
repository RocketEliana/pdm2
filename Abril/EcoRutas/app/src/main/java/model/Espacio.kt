package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Espacio")
data class Espacio(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val telefono: String,
    val icono:Int,
    val latitud:Double,
    val longitud:Double
)
