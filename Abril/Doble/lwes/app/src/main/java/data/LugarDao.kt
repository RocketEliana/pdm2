package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Lugar

@Dao
interface LugarDao {

    @Insert
    fun insertar(lugar: Lugar): Long          // devuelve el ID generado

    @Query("SELECT * FROM Lugar")
    fun getAll(): LiveData<List<Lugar>>

    @Query("SELECT * FROM Lugar WHERE id = :id")
    fun getById(id: Long): Lugar?          // consulta por ID (sin LiveData)
    @Query("SELECT * FROM Lugar WHERE categoria= :categoria")
    fun getbycategoria(categoria:String): LiveData<List<Lugar>>
}