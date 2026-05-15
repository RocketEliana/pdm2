package model

import androidx.room.Embedded
import androidx.room.Relation

data class UserPokemon(

    @Embedded
    val usuario: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "usuarioId"
    )
    val pokemon: List<Pokemon>
)