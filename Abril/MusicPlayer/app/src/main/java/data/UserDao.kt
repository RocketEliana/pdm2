package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.User

@Dao
interface UserDao {

    @Insert
    fun insertar(user: User): Long          // devuelve el ID generado

    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Long): User? // consulta por ID (sin LiveData)
    @Query("SELECT * FROM User WHERE nombre =:nombre and contrasenia =:contrasenia")
    fun getNombreContrasenia(nombre: String,contrasenia:String): User?
}