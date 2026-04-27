package Repo

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import data.CancionDao
import data.UserDao
import model.Cancion
import model.User

class Repository(private val userDao: UserDao,private val cancionDao: CancionDao) {

    fun insertarUser(user: User): Long=userDao.insertar(user)
    fun getUserById(id: Long): User? =userDao.getById(id)
    fun getNombreContraseniaUser(nombre: String,contrasenia:String): User?=userDao.getNombreContrasenia(nombre,contrasenia)

    fun insertarCancion(cancion: Cancion): Long=cancionDao.insertar(cancion)

    fun getCancionId(id: Long): Cancion?=cancionDao.getById(id) // consulta por ID (sin LiveData)

    fun listaCanciones(): LiveData<List<Cancion>>{return cancionDao.getAll()}
}


