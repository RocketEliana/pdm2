package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Actividad

@Dao
interface ActividadDao {

    @Insert
    fun insertar(actividad: Actividad): Long          // devuelve el ID generado

    @Query("SELECT * FROM Actividad")
    fun getAll(): LiveData<List<Actividad>>

    @Query("SELECT * FROM Actividad WHERE id = :id")
    fun getById(id: Long): Actividad?          // consulta por ID (sin LiveData)
}
