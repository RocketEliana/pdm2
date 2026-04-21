package com.example.pokemongo.Repository

import androidx.lifecycle.LiveData
import com.example.pokemongo.data.PokemonDao
import com.example.pokemongo.data.UserDao
import com.example.pokemongo.model.Pokemon
import com.example.pokemongo.model.User


class Repository(private val userDao: UserDao,private val pokemonDao: PokemonDao) {

    fun insertarUser(user: User): Long {
        return userDao.insertar(user)
    }


    fun getUserById(id: Long): User? = userDao.getById(id)
    fun getIdUser(nombreC: String,passwordC:String): Long?=userDao.getId(nombreC, passwordC)
    fun insertarPoquemon(pokemon: Pokemon): Long=pokemonDao.insertar(pokemon)         // devuelve el ID generado
    fun getAllPokemon(): LiveData<List<Pokemon>>{return  pokemonDao.getAll()}
    fun getPokemonId(id: Long): Pokemon?=pokemonDao.getById(id)           // consulta por ID (sin LiveData)
}
