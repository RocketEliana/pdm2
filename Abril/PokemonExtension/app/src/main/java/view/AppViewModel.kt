package view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Pokemon
import model.User
import model.UserPokemon
import repo.Repository

class AppViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository:
            Repository

    val listaUsuarios:
            LiveData<List<User>>//dinamica,sin parametros

    init {

        val daoUser =
            AppDataBase
                .getDatabase(application).ususerDao()
        val daoPokemon = AppDataBase.getDatabase(application).pokemonDao()

        repository =
            Repository(
                daoUser, daoPokemon
            )

        listaUsuarios =
            repository.listaUsuarios()
    }

    fun insertarUsuario(
        usuario: User
    ): Long {

        return repository
            .insertarUsuario(usuario)
    }

    fun insertarPokemon(
        pokemon: Pokemon
    ): Long {

        return repository
            .insertarPokemon(pokemon)
    }

    fun getUsuarioConPokemon(
        id: Long
    ): LiveData<UserPokemon> {

        return repository
            .getUsuarioConPokemon(id)
    }

    fun listaPokemonUsuario(
        usuarioId: Long
    ): LiveData<List<Pokemon>> {

        return repository
            .listaPokemonUsuario(usuarioId)
    }
}