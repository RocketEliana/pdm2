package repo

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import data.ActividadDao
import data.PlanDao
import data.SendaDao
import model.Actividad
import model.Plan
import model.Senda

class Repository(private val sendaDao: SendaDao,private val planDao: PlanDao,private val actividadDao: ActividadDao) {

    fun insertarActividad(actividad: Actividad)=actividadDao.insertar(actividad)          // devuelve el ID generado
    fun getAllActividad(): LiveData<List<Actividad>>{return actividadDao.getAll()}
    fun getActividadById(id: Long): Actividad?=actividadDao.getById(id)
    fun insertarSenda(senda: Senda): Long=sendaDao.insertar(senda)          // devuelve el ID generado
    fun getAllSenda(): LiveData<List<Senda>>{return  sendaDao.getAll()}
    fun getSendaById(id: Long): Senda?=sendaDao.getById(id)
    fun insertarPlan(plan: Plan): Long=planDao.insertar(plan)          // devuelve el ID generado
    fun actualizarPlan(plan: Plan){planDao.actualizar(plan)}
    fun eliminarPlan(plan: Plan){planDao.eliminar(plan)}
    fun getAllPlan(): LiveData<List<Plan>>{return planDao.getAll()}
    fun getByIdPlan(id: Long): Plan?=planDao.getById(id)
}