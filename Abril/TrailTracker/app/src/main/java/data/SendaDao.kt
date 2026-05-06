package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Senda

@Dao
interface SendaDao {

    @Insert
    fun insertar(senda: Senda): Long          // devuelve el ID generado
    @Query("SELECT * FROM Senda")
    fun getAll(): LiveData<List<Senda>>

    @Query("SELECT * FROM Senda WHERE id = :id")
    fun getById(id: Long): Senda?          // consulta por ID (sin LiveData)
}
