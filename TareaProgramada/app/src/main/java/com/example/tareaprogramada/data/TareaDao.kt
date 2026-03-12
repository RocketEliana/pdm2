package com.example.tareaprogramada.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tareaprogramada.model.Tarea

@Dao
interface TareaDao{
    @Insert
    fun insertar(tarea: Tarea)
    @Update
    fun actualizar(tarea: Tarea)
    @Delete
    fun borrar(tarea: Tarea)
    @Query("Select * from tarea")
    fun listadoTareas(): LiveData<List<Tarea>>
    @Query("Select * from tarea where id = :id")
    fun tareaPorId(id:Int):Tarea?
}