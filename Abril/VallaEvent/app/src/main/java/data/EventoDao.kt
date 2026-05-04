package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.Evento

@Dao
interface EventoDao {

    @Insert
    fun insertar(evento: Evento): Long          // devuelve el ID generado

    @Update
    fun actualizar(evento: Evento)

    @Delete
    fun eliminar(evento: Evento)

    @Query("SELECT * FROM Evento")
    fun getAll(): LiveData<List<Evento>>

    @Query("SELECT * FROM Evento WHERE id = :id")
    fun getById(id: Long): Evento?          // consulta por ID (sin LiveData)
}