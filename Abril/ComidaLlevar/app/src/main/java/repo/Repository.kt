package repo

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import data.PedidoDao
import data.RestauranteDao
import model.Pedido
import model.Restaurante

class Repository(private val Rdao: RestauranteDao,private val Pdao: PedidoDao) {

    fun insertarRestaurante(restaurante: Restaurante): Long {
        return Rdao.insertar(restaurante)
    }

    fun listaTodo(): LiveData<List<Restaurante>> = Rdao.getAll()

    fun getById(id: Long): Restaurante? = Rdao.getById(id)

    fun insertarP(pedido: Pedido): Long=Pdao.insertar(pedido)          // devuelve el ID generado


    fun getAllPedido(): LiveData<List<Pedido>>{return Pdao.getAll()}
}
