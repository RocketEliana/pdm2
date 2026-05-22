package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Espacio

@Dao
interface EspacioDao {

    @Insert
    fun insertar(espacio: Espacio): Long          // devuelve el ID generado
    @Query("SELECT * FROM Espacio")
    fun getAll(): LiveData<List<Espacio>>

    @Query("SELECT * FROM Espacio WHERE id = :id")
    fun getById(id: Long): Espacio?          // consulta por ID (sin LiveData)
}
