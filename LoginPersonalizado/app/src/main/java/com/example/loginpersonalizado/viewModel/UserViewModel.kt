package com.example.loginpersonalizado.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.loginpersonalizado.data.AppDataBase
import com.example.loginpersonalizado.model.User
import com.example.loginpersonalizado.repository.UserRepository

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository
    val listado: LiveData<List<User>>
    init{
        val dao= AppDataBase.getDatabase(application).userDao()
        repository= UserRepository(dao)
        listado=repository.listado()
    }

fun Id(nombre:String,contrasenia:String):Int?=repository.Idconsulta(nombre,contrasenia)
    fun insertar(user : User){repository.insertar(user)}
    fun actualizar(user: User){repository.actualizar(user)}
    fun UserId(id:Int):User?=repository.obtenerPorId(id)
    fun borrar(user: User){repository.borrar(user)}

}

    //ejemplo // En el ViewModel
    //fun obtenerPorCategoria(cat: String): LiveData<List<User>> {
    //    return repository.listadoPorCategoria(cat)
    //}si lleva parametro,en el init no!aunque sea un liveData,por que el init es lo que se carga inicialmente


