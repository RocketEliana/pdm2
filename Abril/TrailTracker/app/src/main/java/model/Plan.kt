package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Plan")
data class Plan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombreO: String,
    val tipo: String,
    val fecha:String,
    val duracion:Double,
    val foto:String
)