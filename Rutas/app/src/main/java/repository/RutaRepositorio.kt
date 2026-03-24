package repository

import androidx.lifecycle.LiveData
import data.RutaDao
import model.Ruta

class RutaRepositorio(private val daoRuta: RutaDao){
    fun inserta(ruta:Ruta){daoRuta.insertar(ruta)}
    fun borrar(ruta:Ruta){daoRuta.borrar(ruta)}
    fun actualiza(ruta: Ruta){daoRuta.actualizar(ruta)}
    fun listaRuta(): LiveData<List<Ruta>>{return  daoRuta.listaRutas()}
    fun rutaPorId(id:Int):Ruta?=daoRuta.rutaPorId(id)

}