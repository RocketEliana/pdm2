package view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Lugar
import repo.Repository

class AppViewModel(application: Application) : AndroidViewModel(application) {
//uso viewmodel para dar solución al problema de los ciclos de vida y rotaciones, independientemente de si usas corrutinas o hilos tradicionales

    private val repository: Repository
    val listaLugar: LiveData<List<Lugar>>

    init {
        val dao = AppDataBase.getDatabase(application).lugarDao()
        repository = Repository(dao)
        listaLugar = repository.getAllLugar()
    }

    fun insertarLug(lugar: Lugar): Long=repository.insertarL(lugar)
    fun getPorIdLugar(id: Long): Lugar?=repository.getByIdLugar(id)
    fun getporcategoria(categoria:String): LiveData<List<Lugar>>{return repository.getbycategoria(categoria)}
}
