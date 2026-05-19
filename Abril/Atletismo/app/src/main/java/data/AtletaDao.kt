package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import model.Atleta
import model.AtletaCompleto

@Dao
interface AtletaDao {

    @Insert
    fun insertar(atleta: Atleta): Long


    @Transaction
    @Query("SELECT * FROM Atleta")
    fun getAll(): LiveData<List<AtletaCompleto>>
    @Query("""
    SELECT COUNT(*) 
    FROM Atleta 
    WHERE categoriaId = :categoriaId
""")
    fun contarAtletasPorCategoria(categoriaId: Long): Int


    @Transaction
    @Query("SELECT * FROM Atleta WHERE id = :id")
    fun getById(id: Long): AtletaCompleto?

}