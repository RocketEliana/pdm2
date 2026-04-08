package com.example.bibliotecaabril.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bibliotecaabril.model.Biblioteca

@Dao
interface BibliotecaDao {

    @Insert
    fun insertar(biblioteca: Biblioteca): Long          // devuelve el ID generado
    @Query("SELECT * FROM biblioteca")
    fun getAll(): LiveData<List<Biblioteca>>

    @Query("SELECT * FROM biblioteca WHERE id = :id")
    fun getById(id: Long): Biblioteca?           // consulta por ID (sin LiveData)
}