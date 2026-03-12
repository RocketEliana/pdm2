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

interface InstitutoDao {
    @Insert
    fun insertar(instituto: Instituto)
    @Query("Select * from instituto")
    fun listaInstituto(): LiveData<List<Instituto>>
    @Query("Select * from instituto where id = :id")
    fun institutoPorId(id:Int): Instituto?

}
