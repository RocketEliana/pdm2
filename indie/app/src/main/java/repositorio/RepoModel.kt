package repositorio

import androidx.lifecycle.LiveData
import data.RutaConciertosDao
import data.SalaDao
import model.RutaConciertos
import model.Sala

class RepoModel(private val daoRuta: RutaConciertosDao, private val daoSala: SalaDao) {
    // --- MÉTODOS DE CONCIERTOS (RUTAS) ---
    fun insertarConcierto(ruta: RutaConciertos) = daoRuta.insertar(ruta)
    fun borrarConcierto(ruta: RutaConciertos) = daoRuta.borrar(ruta)
    fun actualizarConcierto(ruta: RutaConciertos) = daoRuta.actualizar(ruta)
    fun listadoRutasCompleto(): LiveData<List<RutaConciertos>> = daoRuta.listadoRutas()
    fun rutaDadaId(id: Int): RutaConciertos? = daoRuta.rutaId(id)

    // --- MÉTODOS DE SALAS ---
    fun insertarSala(sala: Sala) = daoSala.insertar(sala)
    fun borrarSala(sala: Sala) = daoSala.borrar(sala) // Corregido: antes decía insertar
    fun actualizarSala(sala: Sala) = daoSala.actualizar(sala)
    fun listadoSala(): LiveData<List<Sala>> = daoSala.listadoSalas()
    fun salaDadaId(id: Int): Sala? = daoSala.salaId(id)
}