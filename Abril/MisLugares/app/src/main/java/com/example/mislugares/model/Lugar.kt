package com.example.mislugares.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lugar")
data class Lugar(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val tipoIncon: Int,
    val direccion: String,
    val telefono: String,
    val web: String,
    val descripcion: String,
    val fecha:String,
    val calificacion:Float,
    val foto:String,
    val latitud: Double,
    val longitud: Double
)