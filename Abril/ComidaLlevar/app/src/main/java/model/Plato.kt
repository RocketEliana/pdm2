package model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Plato( // no es necesario que sea entidad,solo quiero una lista
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String
)