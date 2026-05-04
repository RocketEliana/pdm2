package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.Sala


@Dao
interface SalaDao {
    @Insert
    fun insertar(sala: Sala)
    @Delete
    fun borrar(sala: Sala)
    @Update
    fun actualizar(sala: Sala)
    @Query("Select *  from sala")
    fun listadoSalas(): LiveData<List<Sala>>
    @Query("Select * from sala where id = :id")
    fun salaId(id:Int): Sala?

}