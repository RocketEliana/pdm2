package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Sitio
import repo.Repository

class AppViewModel(application: Application) : AndroidViewModel(application) {
//uso viewmodel para dar solución al problema de los ciclos de vida y rotaciones, independientemente de si usas corrutinas o hilos tradicionales

    private val repository: Repository
    val listaSitio: LiveData<List<Sitio>>

    init {
        val dao = AppDataBase.getDatabase(application).sitioDao()
        repository = Repository(dao)
        listaSitio = repository.listaTodoS()
    }

    fun insertarSitio(sitio: Sitio): Long {
        return repository.insertarS(sitio)
    }

    fun actualizarSitio(sitio: Sitio) {
        repository.actualizarS(sitio)
    }

    fun eliminarSitio(sitio: Sitio) {
        repository.eliminarS(sitio)
    }

    fun getPOrId(id: Long): Sitio? {
        return repository.getById(id)
    }
}
