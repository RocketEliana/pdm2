package com.example.loginpersonalizado.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Pokemon")
data class Pokemon (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val nombre:String,
    val tipo:String,
    val nivel:Int,
    val foto:String)