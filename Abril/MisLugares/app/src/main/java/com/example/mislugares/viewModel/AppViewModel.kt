package com.example.mislugares.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mislugares.data.AppDataBase
import com.example.mislugares.model.Lugar
import com.example.mislugares.repo.Repository

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val lista: LiveData<List<Lugar>>

    val lmenorMayor: LiveData<List<Lugar>>

    val lmayorMenor: LiveData<List<Lugar>>

    init {
        val dao = AppDataBase.getDatabase(application).lugarDao()
        repository = Repository(dao)
        lista = repository.getAllLista()
        lmenorMayor=repository.menorMayor()
        lmayorMenor=repository.mayorMenor()
    }

    fun insertarL(lugar: Lugar): Long {
        return repository.insertarLugar(lugar)
    }

    fun actualizarL(lugar: Lugar) {
        repository.actualizarLugar(lugar)
    }

    fun eliminarL(lugar: Lugar ) {
        repository.eliminar(lugar)
    }

    fun getPorId(id: Long):Lugar? {
        return  repository.getPorId(id)
    }
}
