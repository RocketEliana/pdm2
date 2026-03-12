package com.example.loginpersonalizado.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import com.example.loginpersonalizado.model.Pokemon
import com.example.loginpersonalizado.model.User

@Dao
interface PokemonDao {
    @Insert(onConflict = ABORT)
    fun insertar(pokemon: Pokemon)

    @Query("Select * from Pokemon where id = :id")
    fun devolverPorId(id: Int): Pokemon?

    @Query("Select * from Pokemon")
    fun listado(): LiveData<List<Pokemon>>
}



