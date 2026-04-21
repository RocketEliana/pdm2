package com.example.pokemongo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.pokemongo.Repository.Repository
import com.example.pokemongo.data.AppDataBase
import com.example.pokemongo.model.Pokemon
import com.example.pokemongo.model.User

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val listaPokemon:LiveData<List<Pokemon>>


    init {
        val daoUser = AppDataBase.getDatabase(application).userDao()
        val daoPokemon= AppDataBase.getDatabase(application).pokemonDao()
        repository = Repository(daoUser,daoPokemon)
        listaPokemon=repository.getAllPokemon()
    }

    fun insertarUser(user: User): Long {
        return repository.insertarUser(user)
    }



    fun getUserId(id: Long):User? {
        return repository.getUserById(id)
    }
    fun getIdUser(nombreC: String,passwordC:String): Long?=repository.getIdUser(nombreC, passwordC)
    fun insertarPoquemon(pokemon: Pokemon): Long=repository.insertarPoquemon(pokemon)
    fun getPokemonId(id: Long): Pokemon?=repository.getPokemonId(id)

}



