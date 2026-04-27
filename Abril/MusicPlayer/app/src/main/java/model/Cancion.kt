package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cancion")
data class Cancion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titulo: String,
    val artista: String,
    val genero: String,
    val valoracion:Float,
    val foto: String
)