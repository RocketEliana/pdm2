package com.example.tapasparquesol.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bar")

data class Bar(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    var nombre:String,
    var direccion:String,
    var calificacion:Int,
    val longitud: Double,
    val latitud:Double,
    var web:String
)