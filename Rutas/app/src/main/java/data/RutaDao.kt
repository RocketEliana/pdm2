package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.Ruta

@Dao

interface RutaDao {
    @Insert
    fun insertar(ruta: Ruta)
    @Update
    fun actualizar(ruta:Ruta)
    @Delete
    fun borrar(ruta:Ruta)
    @Query("Select * from ruta")
    fun listaRutas(): LiveData<List<Ruta>>
    @Query("Select * from ruta where id = :id")
    fun rutaPorId(id:Int):Ruta?
}