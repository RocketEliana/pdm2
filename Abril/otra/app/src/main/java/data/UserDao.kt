package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.User


@Dao
interface UserDao {

    @Insert
    fun insertar(user: User): Long          // devuelve el ID generado
    @Query("SELECT * FROM user WHERE nombre = :nombre and contrasenia =:contrasenia" )
    fun userExiste(nombre:String,contrasenia:String): User?           // consulta por ID (sin LiveData)
}
