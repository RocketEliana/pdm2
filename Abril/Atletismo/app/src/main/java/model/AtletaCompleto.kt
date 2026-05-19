package model

import androidx.room.Embedded
import androidx.room.Relation

data class AtletaCompleto(

    @Embedded
    val atleta: Atleta,

    @Relation(
        parentColumn = "competicionId",
        entityColumn = "id"
    )
    val competicion: Competicion,

    @Relation(
        parentColumn = "categoriaId",
        entityColumn = "id"
    )
    val categoria: Categoria
)