package repo

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import data.AtletaDao
import data.CategoriaDao
import data.CompeticionDao
import model.Atleta
import model.AtletaCompleto
import model.Categoria
import model.Competicion


class Repository(private val atletaDao: AtletaDao,private val competicionDao: CompeticionDao,
                 private val categoriaDao: CategoriaDao) {

    fun getAllA(): LiveData<List<AtletaCompleto>>{return atletaDao.getAll()}
    fun getAllC(): LiveData<List<Categoria>>{return categoriaDao.getAll()}
    fun getAllCO(): LiveData<List<Competicion>>{return competicionDao.getAll()}
    fun insertarC(competicion: Competicion): Long=competicionDao.insertar(competicion)          // devuelve el ID generado
    fun getCompeticionId(id: Long): Competicion?=competicionDao.getById(id)       // consulta por ID (sin LiveData)
    fun insertarCate(categoria: Categoria): Long=categoriaDao.insertar(categoria)      // devuelve el ID generado
    fun getCateId(id: Long): Categoria?=categoriaDao.getById(id)          // consulta por ID (sin LiveData) @Insert
    fun insertarAt(atleta: Atleta): Long=atletaDao.insertar(atleta)          // devuelve el ID generad
    fun getAtId(id: Long): AtletaCompleto?=atletaDao.getById(id)          // consulta por ID (sin LiveData)
    fun contarAtletasCategoria(categoriaId: Long): Int =atletaDao.contarAtletasPorCategoria(categoriaId)

}