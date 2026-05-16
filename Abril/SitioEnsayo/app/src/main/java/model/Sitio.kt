package model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Sitio")
data class Sitio(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val direccion: String,
    val icono:Int,
    val calificacion:Float,
    val telefono:String,
    val fecha:String,
    val web:String,
    val descripcion:String,
    val foto:String,
    val latitud:Double,
    val longitud:Double

)