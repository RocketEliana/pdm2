package com.example.tapasparquesol.viewModelTapas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tapasparquesol.data.AppDataBase
import com.example.tapasparquesol.model.Bar
import com.example.tapasparquesol.repository.BarRepository

class ViewModelBar(application: Application): AndroidViewModel(application) {
    val repository: BarRepository
    val listado: LiveData<List<Bar>>
    init{
        val dao= AppDataBase.getDatabase(application).barDao()
        repository= BarRepository(dao)
        listado=repository.listadoBares()
    }
    fun inserta(bar: Bar){repository.inserta(bar)}
    fun actualiza(bar: Bar){repository.actualiza(bar)}
    fun borrar(bar: Bar){repository.borrar(bar)}
    fun barId(id:Int):Bar?{return repository.barId(id)}
}