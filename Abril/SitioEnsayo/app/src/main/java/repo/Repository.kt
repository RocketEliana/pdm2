package repo

import androidx.lifecycle.LiveData
import data.SitioDao
import model.Sitio

class Repository(private val dao: SitioDao) {

    fun insertarS(sitio: Sitio): Long {
        return dao.insertar(sitio)
    }

    fun actualizarS(sitio: Sitio) {
        dao.actualizar(sitio)
    }

    fun eliminarS(sitio: Sitio) {
        dao.eliminar(sitio)
    }

    fun listaTodoS(): LiveData<List<Sitio>> = dao.getAll()

    fun getById(id: Long): Sitio? = dao.getById(id)
}