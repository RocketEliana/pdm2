package model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Pokemon",

    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [Index("usuarioId")]
)
data class Pokemon(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val nombre: String,
    val tipo: String,
    val nivel: Int,

    // Foreign Key
    val usuarioId: Long
)