package com.example.bibliotecaabril.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.bibliotecaabril.data.AppDataBase
import com.example.bibliotecaabril.model.Biblioteca
import com.example.bibliotecaabril.model.Traslado
import com.example.bibliotecaabril.repo.Repository

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val listaBiblioteca: LiveData<List<Biblioteca>>
    val listaTraslado: LiveData<List<Traslado>>

    init {
        val daoBiblioteca = AppDataBase.getDatabase(application).bibliotecaDao()
        val daoTraslado = AppDataBase.getDatabase(application).trasladoDao()
        repository = Repository(daoBiblioteca, daoTraslado)
        listaBiblioteca = repository.getBibliotecaAll()
        listaTraslado = repository.getAllTraslado()
    }

    fun bibliotecaById(id: Long): Biblioteca? = repository.getBibliotecaById(id)
    fun trasladoById(id: Long): Traslado? = repository.getTrasladoById(id)
    fun insertaBiblioteca(biblioteca: Biblioteca): Long = repository.insertarBiblioteca(biblioteca)
    fun insertaTraslado(traslado: Traslado): Long = repository.insertarTraslado(traslado)
}
