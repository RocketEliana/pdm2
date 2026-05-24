package repo

import androidx.lifecycle.LiveData
import androidx.room.Query
import data.LugarDao
import model.Lugar

class Repository(private val dao: LugarDao) {
    fun getAllLugar(): LiveData<List<Lugar>>{return dao.getAll()}

    fun insertarL(lugar: Lugar): Long=dao.insertar(lugar)
    fun getByIdLugar(id: Long): Lugar?=dao.getById(id)
    fun getbycategoria(categoria:String): LiveData<List<Lugar>>{return dao.getbycategoria(categoria)}
}