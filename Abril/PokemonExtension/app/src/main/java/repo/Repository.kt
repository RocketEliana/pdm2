package repo

import androidx.lifecycle.LiveData
import data.PokemonDao
import data.UserDao
import model.Pokemon
import model.User
import model.UserPokemon

class Repository(

    private val usuarioDao: UserDao,
    private val pokemonDao: PokemonDao

) {

    // USUARIO

    fun insertarUsuario(
        usuario: User
    ): Long {

        return usuarioDao.insertar(usuario)
    }

    fun listaUsuarios():
            LiveData<List<User>> {

        return usuarioDao.getAll()
    }

    fun getUsuarioById(
        id: Long
    ): User? {

        return usuarioDao.getById(id)
    }

    fun getUsuarioConPokemon(
        id: Long
    ): LiveData<UserPokemon> {

        return usuarioDao
            .getUsuarioConPokemon(id)
    }

    // POKEMON

    fun insertarPokemon(
        pokemon: Pokemon
    ): Long {

        return pokemonDao.insertar(pokemon)
    }

    fun listaPokemonUsuario(
        usuarioId: Long
    ): LiveData<List<Pokemon>> {

        return pokemonDao
            .getPokemonUsuario(usuarioId)
    }
}