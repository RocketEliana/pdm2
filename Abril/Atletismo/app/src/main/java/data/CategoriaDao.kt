package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Categoria

@Dao
interface CategoriaDao {

    @Insert
    fun insertar(categoria: Categoria): Long          // devuelve el ID generado

    @Query("SELECT * FROM Categoria")
    fun getAll(): LiveData<List<Categoria>>

    @Query("SELECT * FROM Categoria WHERE id = :id")
    fun getById(id: Long): Categoria?          // consulta por ID (sin LiveData)


}