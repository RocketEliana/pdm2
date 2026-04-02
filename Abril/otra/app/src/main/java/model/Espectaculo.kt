package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "espectaculo")
data class Espectaculo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val foto: String,
    val icono:Int,
    val fecha:String
)