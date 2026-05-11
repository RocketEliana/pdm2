package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pedido")
data class Pedido(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val restaurante: String,
    val plato: String,
    val entrega:String,
    val fechaHora:String

)