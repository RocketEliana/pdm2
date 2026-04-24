package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Instituto
import model.Viaje
import repo.Repository

class AppViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val listaInsti: LiveData<List<Instituto>>
    val listaViaje: LiveData<List<Viaje>>

    init {
        val daoInstituto = AppDataBase.getDatabase(application).institutoDao()
        val daoViaje = AppDataBase.getDatabase(application).viajeDao()
        repository = Repository(daoInstituto,daoViaje)
        listaInsti = repository.listaInstis()
        listaViaje=repository.listaViaje()
    }
    fun insertViaje(viaje: Viaje): Long=repository.insertarViaje(viaje)          // devuelve el ID generado

    fun actualizaViaje(viaje: Viaje){repository.actualizarViaje(viaje)}

    fun eliminaViaje(viaje: Viaje){repository.eliminarViaje(viaje)}

    fun insertaInsti(instituto: Instituto): Long=repository.insertarInsti(instituto)          // devuelve el ID generado

    fun instiPorId(id: Long): Instituto?=repository.instiById(id)
}

