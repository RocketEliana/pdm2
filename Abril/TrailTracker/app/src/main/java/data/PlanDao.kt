package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.Plan

@Dao
interface PlanDao{

    @Insert
    fun insertar(plan: Plan): Long          // devuelve el ID generado

    @Update
    fun actualizar(plan: Plan)

    @Delete
    fun eliminar(plan: Plan)

    @Query("SELECT * FROM Plan")
    fun getAll(): LiveData<List<Plan>>

    @Query("SELECT * FROM Plan WHERE id = :id")
    fun getById(id: Long): Plan?          // consulta por ID (sin LiveData)
}
