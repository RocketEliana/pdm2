package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Evento
import model.User
import repo.Repository

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val listaEvento: LiveData<List<Evento>>



    init {
        val userDao = AppDataBase.getDatabase(application).userDao()
        val eventoDao = AppDataBase.getDatabase(application).eventoDao()
        repository = Repository(userDao,eventoDao)
        listaEvento = repository.getAllEvento()
    }
    fun insertaEvento(evento: Evento): Long =
        repository.insertarEvento(evento)         // devuelve el ID generado

    fun actualizaEvento(evento: Evento) {
        repository.actualizarEvento(evento)
    }

    fun eliminaEvento(evento: Evento) {
        repository.eliminarEvento(evento)
    }



    fun getEventoId(id: Long): Evento? = repository.getEventoById(id)

    fun insertaUser(user: User): Long = repository.insertarUser(user)          // devuelve el ID generado


    fun getUserId(id: Long): User?=repository.getUserById(id)
}

