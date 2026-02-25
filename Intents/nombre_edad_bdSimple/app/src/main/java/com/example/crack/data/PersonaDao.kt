package com.example.crack.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonaDao {
    @Insert
    fun insertar(persona: Persona)
    @Query("Select * FROM Persona where id= :id")
    fun persona(id:Int): Persona?
    @Query("Select * FROM Persona")
    fun listaPersonas():List<Persona>
}