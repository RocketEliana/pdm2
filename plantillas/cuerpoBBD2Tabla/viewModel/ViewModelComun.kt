package com.example.drones.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.drones.data.AppDataBase
import com.example.drones.model.Instituto
import com.example.drones.model.Viaje
import com.example.drones.repository.RepositorioComun

class ViewModelComun(application: Application): AndroidViewModel(application) {
    val listaViaje: LiveData<List<Viaje>>
    val listaInstituto: LiveData<List<Instituto>>
    val repositorio: RepositorioComun
    init{
        val daoviaje= AppDataBase.getDatabase(application).viajeDao()
        val daoinstituto= AppDataBase.getDatabase(application).institutoDao()
        repositorio= RepositorioComun(daoinstituto,daoviaje)
        listaViaje=repositorio.listaViaje()
        listaInstituto=repositorio.listaInstituto()

    }
    fun insertarViaje(viaje: Viaje){repositorio.insertarViaje(viaje)}
    fun actualizaViaje(viaje: Viaje){repositorio.actualizarViaje(viaje)}
    fun borrarViaje(viaje: Viaje){repositorio.borrarViaje(viaje)}
    fun viajeId(id:Int):Viaje?=repositorio.viajePorId(id)
    fun insertar(instituto: Instituto){repositorio.insertarInstituto(instituto)}
    fun InstId(id:Int): Instituto?=repositorio.institutoPorId(id)
}