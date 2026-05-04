package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Evento")
data class Evento(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val fecha: String,
    val tipo:String,
    val icono:Int,
    val latitud: Double,
    val longitud:Double,
    val valoracion:Float
)