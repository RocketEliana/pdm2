package viewModelApp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Espectaculo
import model.User
import repo.Repository

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val listaEspectaculo: LiveData<List<Espectaculo>>

    init {
        val daoUser = AppDataBase.getDatabase(application).userDao()
        val daoEspectaculo = AppDataBase.getDatabase(application).espectaculoDao()
        repository = Repository(daoUser,daoEspectaculo)
        listaEspectaculo = repository.getAllEspectaculos()
    }

    fun insertarEspectaculo(espectaculo: Espectaculo): Long=repository.insertarEspectaculo(espectaculo)        // devuelve el ID generado
    fun actualizarEspectaculo(espectaculo: Espectaculo){repository.actualizarEspectaculo(espectaculo)}
    fun eliminarEspectaculo(espectaculo: Espectaculo){repository.eliminarEspectaculo(espectaculo)}
    fun getEspectaculoById(id: Long):Espectaculo?=repository.getEspectaculoById(id)           // consulta por ID (sin LiveData)   @Insert
    fun insertarUser(user: User): Long?=repository.insertarUser(user)
    fun existeUser(nombre:String,contrasenia:String): User?=repository.existeUser(nombre,contrasenia)
}
