package com.example.bibliotecaabril.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bibliotecaabril.model.Traslado

@Dao
interface TrasladoDao {

    @Insert
    fun insertar(traslado: Traslado): Long          // devuelve el ID generado
    @Query("SELECT * FROM traslado")
    fun getAll(): LiveData<List<Traslado>>

    @Query("SELECT * FROM traslado WHERE id = :id")
    fun getById(id: Long): Traslado?

}