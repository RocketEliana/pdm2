package com.example.tareaprogramada.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tareaprogramada.data.AppDataBase
import com.example.tareaprogramada.data.TareaDao
import com.example.tareaprogramada.model.Tarea
import com.example.tareaprogramada.repository.TareaRepository

class TareaViewModel(application: Application): AndroidViewModel(application) {
    val listaTareas: LiveData<List<Tarea>>
        val repositorio: TareaRepository
        init{
            val dao= AppDataBase.getDatabase(application).tareaDao()
            repositorio= TareaRepository(dao)
            listaTareas=repositorio.listar()

        }
    fun insertar(tarea: Tarea){repositorio.insertar(tarea)}
    fun actualiza(tarea: Tarea){repositorio.actualizaTarea(tarea)}
    fun borrar(tarea: Tarea){repositorio.borrarTarea(tarea)}
    fun tarId(id:Int):Tarea?=repositorio.tarPorId(id)
}