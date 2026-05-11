package model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Restaurante")
data class Restaurante(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    @Embedded(prefix = "pla1_") val plato1: Plato,
    @Embedded(prefix = "pla2_") val plato2: Plato,
    @Embedded(prefix = "pla3_") val plato3: Plato,
    val telefono:String,
    val foto:Int,
    val latitud:Double,
    val longitud:Double
)