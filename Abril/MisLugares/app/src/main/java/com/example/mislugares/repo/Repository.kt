package com.example.mislugares.repo

import androidx.lifecycle.LiveData
import com.example.mislugares.data.LugarDAO
import com.example.mislugares.model.Lugar

class Repository(private val dao: LugarDAO) {

    fun getAllLista(): LiveData<List<Lugar>> {
        return dao.getAll()
    }

    fun menorMayor(): LiveData<List<Lugar>> {
        return dao.getmenorMayor()
    }

    fun mayorMenor(): LiveData<List<Lugar>> {
        return dao.getmayorMenor()
    }

    fun insertarLugar(lugar: Lugar) = dao.insertar(lugar)          // devuelve el ID generado
    fun actualizarLugar(lugar: Lugar) {
        dao.actualizar(lugar)
    }

    fun eliminar(lugar: Lugar) {
        dao.eliminar(lugar)
    }

    fun getPorId(id: Long): Lugar? = dao.getById(id)           // consulta por ID (sin LiveData)
}