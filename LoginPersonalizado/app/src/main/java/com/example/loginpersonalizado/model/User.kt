package com.example.loginpersonalizado.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val nombre:String,
    val contrasenia:String

)