package repo

import androidx.lifecycle.LiveData
import data.EventoDao
import data.UserDao
import model.Evento
import model.User

class Repository(private val userDao: UserDao, private val eventoDao: EventoDao) {


    fun insertarEvento(evento: Evento): Long =
        eventoDao.insertar(evento)          // devuelve el ID generado

    fun actualizarEvento(evento: Evento) {
        eventoDao.actualizar(evento)
    }

    fun eliminarEvento(evento: Evento) {
        eventoDao.eliminar(evento)
    }

    fun getAllEvento(): LiveData<List<Evento>> {
        return eventoDao.getAll()
    }

    fun getEventoById(id: Long): Evento? = eventoDao.getById(id)

    fun insertarUser(user: User): Long = userDao.insertar(user)          // devuelve el ID generado


    fun getUserById(id: Long): User? = userDao.getById(id)
}