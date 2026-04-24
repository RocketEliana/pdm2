package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Instituto

@Dao
interface InstitutoDao {

    @Insert
    fun insertar(instituto: Instituto): Long          // devuelve el ID generado

    @Query("SELECT * FROM Instituto")
    fun getAll(): LiveData<List<Instituto>>

    @Query("SELECT * FROM Instituto WHERE id = :id")
    fun getById(id: Long): Instituto?          // consulta por ID (sin LiveData)
}