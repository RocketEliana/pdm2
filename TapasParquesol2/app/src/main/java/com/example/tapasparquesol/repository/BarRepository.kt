package com.example.tapasparquesol.repository

import androidx.lifecycle.LiveData
import com.example.tapasparquesol.data.BarDao
import com.example.tapasparquesol.model.Bar

class BarRepository(private val barDao: BarDao) {
    fun inserta(bar: Bar){barDao.inertar(bar)}
    fun actualiza(bar: Bar){barDao.actualizar(bar)}
    fun borrar(bar:Bar){barDao.borrar(bar)}
    fun listadoBares(): LiveData<List<Bar>>{return barDao.listadoBares()}
    fun barId(id:Int):Bar?{return  barDao.barPorId(id)}
}