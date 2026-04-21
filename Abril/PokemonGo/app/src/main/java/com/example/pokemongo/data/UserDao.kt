package com.example.pokemongo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokemongo.model.User

@Dao
interface UserDao {

    @Insert
    fun insertar(user: User): Long          // devuelve el ID generado
    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: Long): User?           // consulta por ID (sin LiveData)
    @Query("SELECT id FROM user WHERE nombre = :nombreC and password = :passwordC")
    fun getId(nombreC: String,passwordC:String): Long?           // consulta por ID (sin LiveData)


}