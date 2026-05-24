package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Lugar")
data class Lugar(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val fecha: String,
    val foto:String,
    val valoracion: Float,
    val latitud: Double,
    val longitud:Double,
    val categoria:String
)