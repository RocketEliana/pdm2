package com.example.drones.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.drones.model.Instituto
import com.example.drones.model.Viaje

@Dao

interface ViajeDao {
    @Insert
    fun insertar(viaje: Viaje)
    @Delete
    fun borrar(viaje: Viaje)
    @Update
    fun actualizar(viaje: Viaje)
    @Query("Select * from viaje")
    fun listaViaje(): LiveData<List<Viaje>>
    @Query("Select * from viaje where id = :id")
    fun viajePorId(id:Int): Viaje?

}