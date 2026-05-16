package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.Sitio

@Dao
interface SitioDao {

    @Insert
    fun insertar(sitio: Sitio): Long          // devuelve el ID generado

    @Update
    fun actualizar(sitio: Sitio)

    @Delete
    fun eliminar(sitio: Sitio)

    @Query("SELECT * FROM Sitio")
    fun getAll(): LiveData<List<Sitio>>

    @Query("SELECT * FROM Sitio WHERE id = :id")
    fun getById(id: Long):  Sitio?          // consulta por ID (sin LiveData)
}