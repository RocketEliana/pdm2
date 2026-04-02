package repo

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import data.EspectaculoDao
import data.UserDao
import model.Espectaculo
import model.User

class Repository(private val daoUser: UserDao,private val daoEspectaculo: EspectaculoDao) {


    fun insertarEspectaculo(espectaculo: Espectaculo): Long{return daoEspectaculo.insertar(espectaculo)}          // devuelve el ID generado
    fun actualizarEspectaculo(espectaculo: Espectaculo){daoEspectaculo.actualizar(espectaculo)}
    fun eliminarEspectaculo(espectaculo: Espectaculo){daoEspectaculo.eliminar(espectaculo)}
    fun getAllEspectaculos(): LiveData<List<Espectaculo>>{return  daoEspectaculo.getAll()}
    fun getEspectaculoById(id: Long):Espectaculo?{return daoEspectaculo.getById(id)}           // consulta por ID (sin LiveData)   @Insert
    fun insertarUser(user: User): Long{return daoUser.insertar(user)}
    fun existeUser(nombre:String,contrasenia:String): User?{return daoUser.userExiste(nombre,contrasenia)}           // consulta por ID (sin LiveData)
}
