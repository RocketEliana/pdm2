package com.example.loginpersonalizado.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.loginpersonalizado.data.AppDataBase
import com.example.loginpersonalizado.data.PokemonDao
import com.example.loginpersonalizado.model.Pokemon
import com.example.loginpersonalizado.model.User
import com.example.loginpersonalizado.repository.bdRepository

class bdViewModel(application: Application): AndroidViewModel(application) {
    private val repository: bdRepository
    val listadoUser: LiveData<List<User>>
    val listadoPokemon:LiveData<List<Pokemon>>
    init{
        val daoUser= AppDataBase.getDatabase(application).userDao()
        val daoPokemon= AppDataBase.getDatabase(application).pokemonDao()
        repository= bdRepository(daoUser,daoPokemon)
        listadoUser=repository.listadoUser()
        listadoPokemon=repository.listaPokemon()
    }

fun IdUser(nombre:String,contrasenia:String):Int?=repository.IdconsultaUser(nombre,contrasenia)
    fun insertarUser(user : User){repository.insertarUser(user)}
    fun actualizarUser(user: User){repository.actualizarUser(user)}
    fun UserId(id:Int):User?=repository.obtenerUserPorId(id)
    fun borrarUser(user: User){repository.borrarUser(user)}
    fun insertarPokemon(pokemon: Pokemon){repository.insertarPokemon(pokemon)}
    fun pokemonPorId(id:Int):Pokemon?= repository.obtenerPokemonPorId(id)


}

    //ejemplo // En el ViewModel
    //fun obtenerPorCategoria(cat: String): LiveData<List<User>> {
    //    return repository.listadoPorCategoria(cat)
    //}si lleva parametro,en el init no!aunque sea un liveData,por que el init es lo que se carga inicialmente


