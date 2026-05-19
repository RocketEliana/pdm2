package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categoria")
data class Categoria(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val latitud:Double,
    val longitud:Double
)