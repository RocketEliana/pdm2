package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Restaurante

@Dao
interface RestauranteDao {

    @Insert
    fun insertar(restaurante: Restaurante): Long          // devuelve el ID generado

    @Query("SELECT * FROM Restaurante")
    fun getAll(): LiveData<List<Restaurante>>

    @Query("SELECT * FROM Restaurante WHERE id = :id")
    fun getById(id: Long): Restaurante?          // consulta por ID (sin LiveData)
}