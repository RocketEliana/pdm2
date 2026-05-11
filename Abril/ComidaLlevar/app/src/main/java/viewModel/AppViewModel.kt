package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Pedido
import model.Restaurante
import repo.Repository

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val lista: LiveData<List<Restaurante>>
    val listaPedido: LiveData<List<Pedido>>
    init {
        val Rdao = AppDataBase.getDatabase(application).restauranteDao()
        val Pdao = AppDataBase.getDatabase(application).pedidoDao()
        repository = Repository(Rdao,Pdao)
        lista= repository.listaTodo()
        listaPedido=repository.getAllPedido()

    }

    fun insertarR(restaurante: Restaurante): Long {
        return repository.insertarRestaurante(restaurante)
    }


    fun getById(id: Long): Restaurante? {
        return repository.getById(id)
    }

    fun insertarPedido(pedido: Pedido): Long=repository.insertarP(pedido)          // devuelve el ID generado


}