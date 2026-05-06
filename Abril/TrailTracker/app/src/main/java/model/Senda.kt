package model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Senda")
data class Senda(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val tefono: String,
    val foto: Int,
    val latitud:Double,
    val longitud:Double,
    @Embedded(prefix = "senderismo_") val actSenderismo: Actividad,
    @Embedded(prefix = "escalada_")   val actEscalada: Actividad,
    @Embedded(prefix = "montana_")    val actMontana: Actividad
)