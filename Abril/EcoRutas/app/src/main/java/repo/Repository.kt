package repo

import androidx.lifecycle.LiveData
import data.EspacioDao
import data.ViajeDao
import model.Espacio
import model.Viaje

class Repository(private val espacioDao: EspacioDao, private val viajeDao: ViajeDao) {
    fun getAllViaje(): LiveData<List<Viaje>> {
        return viajeDao.getAll()
    }

    fun getAllEspacio(): LiveData<List<Espacio>> {
        return espacioDao.getAll()
    }

    fun insertarViaje(viaje: Viaje): Long =
        viajeDao.insertar(viaje)          // devuelve el ID generado

    fun actualizarViaje(viaje: Viaje) {
        viajeDao.actualizar(viaje)
    }

    fun eliminarViaje(viaje: Viaje) {
        viajeDao.eliminar(viaje)
    }


    fun getByIdViaje(id: Long): Viaje? =
        viajeDao.getById(id)          // consulta por ID (sin Li @Insert

    fun insertarEspacio(espacio: Espacio): Long =
        espacioDao.insertar(espacio)          // devuelve el ID generado


    fun getByIdEspacio(id: Long): Espacio? = espacioDao.getById(id)          // consulta por ID
}
