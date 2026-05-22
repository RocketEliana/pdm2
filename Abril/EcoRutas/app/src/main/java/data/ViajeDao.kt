package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.Viaje

@Dao
interface ViajeDao {

    @Insert
    fun insertar(viaje: Viaje): Long          // devuelve el ID generado

    @Update
    fun actualizar(viaje: Viaje)

    @Delete
    fun eliminar(viaje: Viaje)

    @Query("SELECT * FROM Viaje")
    fun getAll(): LiveData<List<Viaje>>

    @Query("SELECT * FROM Viaje WHERE id = :id")
    fun getById(id: Long): Viaje?          // consulta por ID (sin LiveData)
}
