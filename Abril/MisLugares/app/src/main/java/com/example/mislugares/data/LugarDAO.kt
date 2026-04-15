package com.example.mislugares.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mislugares.model.Lugar

@Dao
interface LugarDAO {

    @Insert
    fun insertar(lugar: Lugar): Long          // devuelve el ID generado

    @Update
    fun actualizar(lugar: Lugar)

    @Delete
    fun eliminar(lugar: Lugar)

    @Query("SELECT * FROM lugar")
    fun getAll(): LiveData<List<Lugar>>

    @Query("SELECT * FROM lugar WHERE id = :id")
    fun getById(id: Long): Lugar           // consulta por ID (sin LiveData)
    @Query("SELECT * FROM lugar order by fecha asc")
    fun getmenorMayor(): LiveData<List<Lugar>>
    @Query("SELECT * FROM lugar order by fecha desc")
    fun getmayorMenor(): LiveData<List<Lugar>>
}