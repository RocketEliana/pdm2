package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Viaje")
data class Viaje(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val origen: String,
    val destino: String,
    val fechaHora: String
)