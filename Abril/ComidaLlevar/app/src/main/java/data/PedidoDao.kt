package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Pedido

@Dao
interface PedidoDao {

    @Insert
    fun insertar(pedido: Pedido): Long          // devuelve el ID generado


    @Query("SELECT * FROM Pedido")
    fun getAll(): LiveData<List<Pedido>>

}