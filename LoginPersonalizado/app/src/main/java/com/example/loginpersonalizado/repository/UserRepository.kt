package com.example.loginpersonalizado.repository

import androidx.lifecycle.LiveData
import com.example.loginpersonalizado.data.UserDao
import com.example.loginpersonalizado.model.User

class UserRepository(private val daoUser: UserDao) {
    fun insertar(user: User){daoUser.insertar(user)}
    fun actualizar(user: User){daoUser.update(user)}
    fun borrar(user: User){daoUser.borrar(user)}
    fun obtenerPorId(id:Int):User?=daoUser.devolverPorId(id)
    fun listado(): LiveData<List<User>> {
        return daoUser.listado()
    }
    fun Idconsulta(nombre:String,contrasenia:String):Int?=daoUser.consultarId(nombre,contrasenia)
}