package com.example.crack.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "Persona")

data class Persona (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val nombre:String,
    val edad:Int
)

