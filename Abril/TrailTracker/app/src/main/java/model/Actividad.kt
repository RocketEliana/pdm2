package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Actividad")
data class Actividad(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tipo: String,
    val latA:Double,
    val longA: Double
)