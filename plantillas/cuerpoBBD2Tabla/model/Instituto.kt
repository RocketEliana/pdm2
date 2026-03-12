package com.example.drones.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instituto")
data class Instituto (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val nombre:String,
    val telefono:String,
    val foto:Int
)