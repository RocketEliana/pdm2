package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.Pokemon

@Dao
interface PokemonDao {

    @Insert
    fun insertar(pokemon: Pokemon): Long

    @Update
    fun actualizar(pokemon: Pokemon)

    @Delete
    fun eliminar(pokemon: Pokemon)

    @Query("SELECT * FROM Pokemon")
    fun getAll(): LiveData<List<Pokemon>>

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    fun getById(id: Long): Pokemon?

    // Pokemon de un usuario
    @Query("""
        SELECT * FROM Pokemon
        WHERE usuarioId = :usuarioId
    """)
    fun getPokemonUsuario(
        usuarioId: Long
    ): LiveData<List<Pokemon>>
}