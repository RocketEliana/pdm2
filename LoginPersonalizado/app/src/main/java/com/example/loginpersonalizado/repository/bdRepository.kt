package com.example.loginpersonalizado.repository

import androidx.lifecycle.LiveData
import com.example.loginpersonalizado.data.PokemonDao
import com.example.loginpersonalizado.data.UserDao
import com.example.loginpersonalizado.model.Pokemon
import com.example.loginpersonalizado.model.User

class bdRepository(private val daoUser: UserDao,private val daoPokemon: PokemonDao) {
    fun insertarUser(user: User){daoUser.insertar(user)}
    fun insertarPokemon(pokemon: Pokemon){daoPokemon.insertar(pokemon)}
    fun actualizarUser(user: User){daoUser.update(user)}
    fun borrarUser(user: User){daoUser.borrar(user)}
    fun obtenerUserPorId(id:Int):User?=daoUser.devolverPorId(id)
    fun obtenerPokemonPorId(id:Int): Pokemon?=daoPokemon.devolverPorId(id)
    fun listaPokemon():LiveData<List<Pokemon>>{return daoPokemon.listado()}
    fun listadoUser(): LiveData<List<User>> {
        return daoUser.listado()
    }
    fun IdconsultaUser(nombre:String,contrasenia:String):Int?=daoUser.consultarId(nombre,contrasenia)
}
