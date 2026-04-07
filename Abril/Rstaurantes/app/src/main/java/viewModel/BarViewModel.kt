package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Bar
import repo.Repository

class BarViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val listaBar: LiveData<List<Bar>>

    init {
        val dao = AppDataBase.getDatabase(application).barDao()
        repository = Repository(dao)
        listaBar = repository.listaTodo()
    }

    fun insertar(bar:Bar): Long {
        return repository.insertar(bar)
    }

    fun actualizar(bar: Bar) {
        repository.actualizar(bar)
    }

    fun eliminar(bar: Bar) {
        repository.eliminar(bar)
    }

    fun getById(id: Long): Bar? {
        return repository.getById(id)
    }
}