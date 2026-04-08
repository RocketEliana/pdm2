package com.example.bibliotecaabril.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "traslado")
data class Traslado(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val origen: String,
    val destino: String,
    val fecha: String
)
