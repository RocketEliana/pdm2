package com.example.tapasparquesol.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tapasparquesol.model.Bar

@Dao
interface BarDao {
    @Insert
    fun inertar(bar: Bar)
    @Update
    fun actualizar(bar: Bar)
    @Delete
    fun borrar(bar: Bar)
    @Query("Select * from bar")
    fun listadoBares(): LiveData<List<Bar>>
    @Query("Select * from bar where id = :id")
    fun barPorId(id:Int):Bar?
}