package com.example.drones.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.drones.data.InstitutoDao
import com.example.drones.data.ViajeDao
import com.example.drones.model.Instituto
import com.example.drones.model.Viaje

class RepositorioComun(private val daoInstituto: InstitutoDao,private val daoViaje: ViajeDao) {
    fun insertarViaje(viaje: Viaje){daoViaje.insertar(viaje)}
    fun borrarViaje(viaje: Viaje){daoViaje.borrar(viaje)}
    fun actualizarViaje(viaje: Viaje){daoViaje.actualizar(viaje)}
    fun listaViaje(): LiveData<List<Viaje>>{return daoViaje.listaViaje()}
    fun viajePorId(id:Int): Viaje?=daoViaje.viajePorId(id)
    fun insertarInstituto(instituto: Instituto){daoInstituto.insertar(instituto)}
    fun listaInstituto(): LiveData<List<Instituto>>{return daoInstituto.listaInstituto()}
    fun institutoPorId(id:Int): Instituto?=daoInstituto.institutoPorId(id)
}