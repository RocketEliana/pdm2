package com.example.pokemongo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tipo: String,
    val nivel: Int,
    val foto:String,


)