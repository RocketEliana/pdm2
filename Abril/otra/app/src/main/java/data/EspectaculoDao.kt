package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.Espectaculo


@Dao
interface EspectaculoDao {

    @Insert
    fun insertar(espectaculo: Espectaculo): Long          // devuelve el ID generado

    @Update
    fun actualizar(espectaculo: Espectaculo)

    @Delete
    fun eliminar(espectaculo: Espectaculo)

    @Query("SELECT * FROM espectaculo")
    fun getAll(): LiveData<List<Espectaculo>>

    @Query("SELECT * FROM espectaculo WHERE id = :id")
    fun getById(id: Long):Espectaculo?           // consulta por ID (sin LiveData)
}
