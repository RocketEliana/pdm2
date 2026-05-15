package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import model.User
import model.UserPokemon

@Dao
interface UserDao {

    @Insert
    fun insertar(usuario: User): Long

    @Update
    fun actualizar(usuario: User)

    @Delete
    fun eliminar(usuario: User)

    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Long): User?

    // RELACIÓN 1:N
    @Transaction
    @Query("SELECT * FROM User WHERE id = :id")
    fun getUsuarioConPokemon(
        id: Long
    ): LiveData<UserPokemon>
}