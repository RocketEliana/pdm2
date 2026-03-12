package com.example.drones.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viaje")
data class Viaje (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val origen:String,
    val destino:String,
    val fecha:String
)

