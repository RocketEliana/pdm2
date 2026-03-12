package com.example.tareaprogramada.repository

import androidx.lifecycle.LiveData
import com.example.tareaprogramada.data.TareaDao
import com.example.tareaprogramada.model.Tarea

class TareaRepository( private val  dao: TareaDao) {
    fun listar(): LiveData<List<Tarea>>{return dao.listadoTareas()}
    fun insertar(tarea:Tarea){dao.insertar(tarea)}
    fun actualizaTarea(tarea:Tarea){dao.actualizar(tarea)}
    fun borrarTarea(tarea: Tarea){dao.borrar(tarea)}
    fun tarPorId(id:Int):Tarea?=dao.tareaPorId(id)
}