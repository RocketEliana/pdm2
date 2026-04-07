package repo

import androidx.lifecycle.LiveData
import data.BarDao
import model.Bar

class Repository(private val dao: BarDao) {

    fun insertar(bar: Bar): Long {
        return dao.insertar(bar)
    }

    fun actualizar(bar: Bar) {
        dao.actualizar(bar)
    }

    fun eliminar(bar: Bar) {
        dao.eliminar(bar)
    }

    fun listaTodo(): LiveData<List<Bar>> = dao.getAll()

    fun getById(id: Long): Bar?= dao.getById(id)
}
