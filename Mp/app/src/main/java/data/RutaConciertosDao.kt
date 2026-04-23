package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.RutaConciertos

@Dao
interface RutaConciertosDao {
    @Insert
    fun insertar(rutaConciertos: RutaConciertos)
    @Delete
    fun borrar(rutaConciertos: RutaConciertos)
    @Update
    fun actualizar(rutaConciertos: RutaConciertos)
    @Query("Select *  from ruta")
    fun listadoRutas(): LiveData<List<RutaConciertos>>
    @Query("Select * from ruta where id = :id")
    fun rutaId(id:Int): RutaConciertos?

}