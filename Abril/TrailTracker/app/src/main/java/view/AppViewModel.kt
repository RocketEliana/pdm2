package view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.AppDataBase
import model.Actividad
import model.Plan
import model.Senda
import repo.Repository

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val listaActividad: LiveData<List<Actividad>>
    val listaSenda: LiveData<List<Senda>>
    val listaPlan: LiveData<List<Plan>>

    init {
        val sendaDao = AppDataBase.getDatabase(application).sendaDao()
        val planDao = AppDataBase.getDatabase(application).planDao()
        val actividadDao = AppDataBase.getDatabase(application).actividadDao()
        repository = Repository(sendaDao, planDao, actividadDao)
        listaActividad = repository.getAllActividad()
        listaPlan = repository.getAllPlan()
        listaSenda = repository.getAllSenda()
    }

    fun insertaActividad(actividad: Actividad) = repository.insertarActividad(actividad)
    fun getActividadId(id: Long): Actividad? = repository.getActividadById(id)
    fun insertaSenda(senda: Senda): Long =
        repository.insertarSenda(senda)  // devuelve el ID generado

    fun getSendaId(id: Long): Senda? = repository.getSendaById(id)
    fun insertaPlan(plan: Plan): Long =
        repository.insertarPlan(plan)         // devuelve el ID generado

    fun actualizaPlan(plan: Plan) {
        repository.actualizarPlan(plan)
    }

    fun eliminarPlan(plan: Plan) {
        repository.eliminarPlan(plan)
    }


    fun getIdPlan(id: Long): Plan? = repository.getByIdPlan(id)


}


