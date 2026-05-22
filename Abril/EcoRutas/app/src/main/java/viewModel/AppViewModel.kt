package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Espacio
import model.Viaje
import repo.Repository

class AppViewModel(application: Application) : AndroidViewModel(application) {
//uso viewmodel para dar solución al problema de los ciclos de vida y rotaciones, independientemente de si usas corrutinas o hilos tradicionales

    private val repository: Repository
    val listaViaje: LiveData<List<Viaje>>

    val listaEspacio: LiveData<List<Espacio>>

    init {
        val daoViaje = AppDataBase.getDatabase(application).viajeDao()
        val daoEspacio = AppDataBase.getDatabase(application).espacioDao()
        repository = Repository(daoEspacio,daoViaje)
        listaViaje=repository.getAllViaje()
        listaEspacio=repository.getAllEspacio()
    }

    fun insertaViaje(viaje: Viaje): Long =
        repository.insertarViaje(viaje)          // devuelve el ID generado

    fun actualizaViaje(viaje: Viaje) {
        repository.actualizarViaje(viaje)
    }

    fun eliminaViaje(viaje: Viaje) {
        repository.eliminarViaje(viaje)
    }


    fun getPorIdViaje(id: Long): Viaje? =
        repository.getByIdViaje(id)          // consulta por ID (sin Li @Insert

    fun insertaEspacio(espacio: Espacio): Long =
        repository.insertarEspacio(espacio)          // devuelve el ID generado


    fun getPorIdEspacio(id: Long): Espacio? = repository.getByIdEspacio(id)
}
