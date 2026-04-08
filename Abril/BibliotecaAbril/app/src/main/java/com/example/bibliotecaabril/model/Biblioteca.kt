package com.example.bibliotecaabril.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "biblioteca")
data class Biblioteca(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val correo: String,
    val imagen: Int,
    val latitud:Double,
    val longitud: Double
)
