package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Competicion

@Dao
interface CompeticionDao {

    @Insert
    fun insertar(competicion: Competicion): Long          // devuelve el ID generado

    @Query("SELECT * FROM Competicion")
    fun getAll(): LiveData<List<Competicion>>

    @Query("SELECT * FROM Competicion WHERE id = :id")
    fun getById(id: Long): Competicion?          // consulta por ID (sin LiveData)
}