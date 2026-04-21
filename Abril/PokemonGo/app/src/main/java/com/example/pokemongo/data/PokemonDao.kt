package com.example.pokemongo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokemongo.model.Pokemon
@Dao
interface PokemonDao {

    @Insert
    fun insertar(pokemon: Pokemon): Long          // devuelve el ID generado
    @Query("SELECT * FROM pokemon")
    fun getAll(): LiveData<List<Pokemon>>

    @Query("SELECT * FROM pokemon WHERE id = :id")
    fun getById(id: Long): Pokemon?           // consulta por ID (sin LiveData)

}