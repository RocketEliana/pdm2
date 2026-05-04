package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sala")
data class Sala(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val nombre:String,
    val imagen:Int,
    val latitud: Double,
    val longitud:Double,
    val telefono:String
)