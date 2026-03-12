package com.example.tareaprogramada.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarea")
data class Tarea (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val nombre: String,
    val fecha: String
)