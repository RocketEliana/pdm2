package model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Atleta",
            foreignKeys = [
                ForeignKey(
                    entity = Competicion::class,
                    parentColumns = ["id"],
                    childColumns = ["competicionId"],
                    onDelete = ForeignKey.CASCADE
                ),
                ForeignKey(
                    entity = Categoria::class,
                    parentColumns = ["id"],
                    childColumns = ["categoriaId"],
                    onDelete = ForeignKey.CASCADE
                )
    ],

    indices = [
        Index("competicionId"),
        Index("categoriaId")
    ]
)
data class Atleta(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val dorsal:Int,
    val prueba :String,
    val fecha:String,
    val competicionId: Long,
    val categoriaId: Long
)