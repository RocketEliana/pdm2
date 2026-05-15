package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val password:String

)