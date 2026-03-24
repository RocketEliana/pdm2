package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ruta")

data class Ruta(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val imagen:String,
    val nombre:String,
    val provincia:String,
    val dificultad:Int,
    val latitud:Double,
    val longitud:Double,
    val web:String
)
