package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.Bar

@Dao
interface BarDao {

    @Insert
    fun insertar(bar: Bar): Long          // devuelve el ID generado

    @Update
    fun actualizar(bar: Bar)

    @Delete
    fun eliminar(bar: Bar)

    @Query("SELECT * FROM bar")
    fun getAll(): LiveData<List<Bar>>

    @Query("SELECT * FROM bar WHERE id = :id")
    fun getById(id: Long): Bar?           // consulta por ID (sin LiveData)
}
