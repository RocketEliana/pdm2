package View

import Repo.Repository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Cancion
import model.User

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val listaCancion:LiveData<List<Cancion>>


    init {
        val daoUser = AppDataBase.getDatabase(application).userDao()
        val daocancion = AppDataBase.getDatabase(application).cancionDao()
        repository = Repository(daoUser,daocancion)
        listaCancion=repository.listaCanciones()

    }

    fun insertaUser(user: User): Long=repository.insertarUser(user)         // devuelve el ID generado
    fun userById(id: Long): User? =repository.getUserById(id)
    fun nombreContraseniaUser(nombre: String,contrasenia:String): User?=repository.getNombreContraseniaUser(nombre,contrasenia)

    fun insertaCancion(cancion: Cancion): Long=repository.insertarCancion(cancion)

    fun cancionId(id: Long): Cancion?=repository.getCancionId(id)


}
