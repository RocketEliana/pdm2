package com.example.loginpersonalizado.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import com.example.loginpersonalizado.model.User

@Dao
interface UserDao {
    @Insert(onConflict = ABORT)
    fun insertar(user: User)
    @Update
    fun update(user:User)
    @Delete
    fun borrar(user:User)
    @Query("Select * from user where id = :id")
    fun devolverPorId(id:Int):User?
    @Query("Select * from user")
    fun listado(): LiveData<List<User>>
    @Query("Select id from user where nombre= :nombre AND contrasenia= :contrasenia")
    fun consultarId(nombre:String,contrasenia:String):Int?
}