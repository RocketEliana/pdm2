package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Cancion



@Dao
interface CancionDao {

    @Insert
    fun insertar(cancion: Cancion): Long          // devuelve el ID generado

    @Query("SELECT * FROM Cancion WHERE id = :id")
    fun getById(id: Long): Cancion? // consulta por ID (sin LiveData)
    @Query("SELECT * FROM Cancion")
    fun getAll(): LiveData<List<Cancion>>
}