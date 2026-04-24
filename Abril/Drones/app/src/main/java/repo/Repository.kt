package repo

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import data.InstitutoDao
import data.ViajeDao
import model.Instituto
import model.Viaje

class Repository(private val daoInstituto: InstitutoDao,private val  viajeDao: ViajeDao) {


    fun insertarViaje(viaje: Viaje): Long=viajeDao.insertar(viaje)          // devuelve el ID generado

    fun actualizarViaje(viaje: Viaje){viajeDao.actualizar(viaje)}

    fun eliminarViaje(viaje: Viaje){viajeDao.eliminar(viaje)}

    fun listaViaje(): LiveData<List<Viaje>>{return  viajeDao.getAll()}

    fun insertarInsti(instituto: Instituto): Long=daoInstituto.insertar(instituto)          // devuelve el ID generado

    fun listaInstis(): LiveData<List<Instituto>>{return daoInstituto.getAll()}

    fun instiById(id: Long): Instituto?=daoInstituto.getById(id)          // consulta por
}