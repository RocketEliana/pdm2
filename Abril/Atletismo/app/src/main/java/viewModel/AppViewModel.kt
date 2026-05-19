package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Atleta
import model.AtletaCompleto
import model.Categoria
import model.Competicion
import repo.Repository

class AppViewModel(application: Application) : AndroidViewModel(application) {
//uso viewmodel para dar solución al problema de los ciclos de vida y rotaciones, independientemente de si usas corrutinas o hilos tradicionales

    private val repository: Repository
    val getAllAtleta:LiveData<List<AtletaCompleto>>
    val getAllCategoria:LiveData<List<Categoria>>
    val getAllCompeticion: LiveData<List<Competicion>>

    init {
        val daoAtleta = AppDataBase.getDatabase(application).atletaDao()
        val daoCompeticion = AppDataBase.getDatabase(application).competicionDao()
        val daoCategoria = AppDataBase.getDatabase(application).categoriaDao()
        repository = Repository(daoAtleta, daoCompeticion, daoCategoria)
        getAllAtleta = repository.getAllA()
        getAllCategoria = repository.getAllC()
        getAllCompeticion = repository.getAllCO()
    }
    fun insertarCompeticion(competicion: Competicion): Long=repository.insertarC(competicion)          // devuelve el ID generado
    fun getCompeticionPorId(id: Long): Competicion?=repository.getCompeticionId(id)       // consulta por ID (sin LiveData)
    fun insertarCategoria(categoria: Categoria): Long=repository.insertarCate(categoria)     // devuelve el ID generado
    fun getCategoriaId(id: Long): Categoria?=repository.getCateId(id)          // consulta por ID (sin LiveData) @Insert
    fun insertarAtleta(atleta: Atleta): Long=repository.insertarAt(atleta)          // devuelve el ID generad
    fun getAtletaId(id: Long): AtletaCompleto?=repository.getAtId(id)
    fun atletasCategoria(categoriaId: Long): Int =repository.contarAtletasCategoria(categoriaId)

}