package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Ruta
import repository.RutaRepositorio

class RutaViewModel(application: Application): AndroidViewModel(application){
    val listado: LiveData<List<Ruta>>
    val repositorio: RutaRepositorio
    init {
        val dao = AppDataBase.getDatabase(application).rutaDao()
        repositorio = RutaRepositorio(dao)
        listado = repositorio.listaRuta()
    }
    fun insertarRuta(ruta:Ruta){repositorio.inserta(ruta)}
    fun borrarRuta(ruta:Ruta){repositorio.borrar(ruta)}
    fun actualiozar(ruta:Ruta){repositorio.actualiza(ruta)}
    fun rutaId(id:Int):Ruta?{return  repositorio.rutaPorId(id)}
}