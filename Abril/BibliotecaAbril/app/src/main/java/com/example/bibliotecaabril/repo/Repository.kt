package com.example.bibliotecaabril.repo

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import com.example.bibliotecaabril.data.BibliotecaDao
import com.example.bibliotecaabril.data.TrasladoDao
import com.example.bibliotecaabril.model.Biblioteca
import com.example.bibliotecaabril.model.Traslado

class Repository(private val daoBiblioteca: BibliotecaDao,private  val daoTraslado: TrasladoDao) {
    fun insertarTraslado(traslado: Traslado):Long=daoTraslado.insertar(traslado)
    fun getAllTraslado(): LiveData<List<Traslado>>{return daoTraslado.getAll()}
    fun getTrasladoById(id: Long): Traslado?=daoTraslado.getById(id)
    fun insertarBiblioteca(biblioteca: Biblioteca): Long=daoBiblioteca.insertar(biblioteca)          // devuelve el ID generado
    fun getBibliotecaAll(): LiveData<List<Biblioteca>>{return daoBiblioteca.getAll()}
    fun getBibliotecaById(id: Long): Biblioteca?=daoBiblioteca.getById(id)           // consulta por ID (sin LiveData)
}