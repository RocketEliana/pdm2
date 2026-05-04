package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.RutaConciertos
import model.Sala
import repositorio.RepoModel

class ViewModelGira(application: Application) : AndroidViewModel(application) {
    val listaSala: LiveData<List<Sala>>
    val listaRuta: LiveData<List<RutaConciertos>>
    private val repository: RepoModel

    init {
        val database = AppDataBase.getDatabase(application)
        repository = RepoModel(database.rutaDao(), database.salaDao())
        listaSala = repository.listadoSala()
        listaRuta = repository.listadoRutasCompleto()
    }

    // Métodos para Salas
    fun insertarSala(sala: Sala) = repository.insertarSala(sala)
    fun borrarSala(sala: Sala) = repository.borrarSala(sala)
    fun obtenerSalaPorId(id: Int) = repository.salaDadaId(id)


    fun insertarRuta(ruta: RutaConciertos) = repository.insertarConcierto(ruta)
    fun borrarRuta(ruta: RutaConciertos) = repository.borrarConcierto(ruta)
    fun actualizarRuta(ruta: RutaConciertos) = repository.actualizarConcierto(ruta)
}