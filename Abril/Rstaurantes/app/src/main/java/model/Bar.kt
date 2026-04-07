package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bar")
data class Bar(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val direccion: String,
    val valoracion:Float,
    val latitud: Double,
    val longitud:Double,
    val web:String
)