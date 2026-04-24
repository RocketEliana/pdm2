package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Instituto")
data class Instituto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val telefono:String,
    val icono:Int,
    val longitud:Double,
    val latitud: Double
)
