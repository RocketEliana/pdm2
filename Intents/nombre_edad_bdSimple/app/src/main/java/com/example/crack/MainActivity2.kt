package com.example.crack

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.crack.data.AppDataBase
import com.example.crack.data.Persona
import com.example.crack.data.PersonaDao
import com.example.crack.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var appDataBase: AppDataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        appDataBase= AppDataBase.getDatabase(this)
        val nombreRecibido=intent.getStringExtra("NOMBRE")
        val edadRecibida=intent.getStringExtra("EDAD")
        binding.textView2.text="Hola ${nombreRecibido} , tienes ${edadRecibida} años"
        binding.insertar.setOnClickListener {
            val persona= Persona(nombre = nombreRecibido.toString(), edad = edadRecibida.toString().toInt())
            appDataBase.personaDao().insertar(persona)
            Toast.makeText(this,"Insertado", Toast.LENGTH_SHORT).show()

        }
        binding.verLista.setOnClickListener { val intent=Intent(this, MainActivity3::class.java)
        startActivity(intent)}

        onBackPressedDispatcher.addCallback(this) {
            // Aquí pones lo que quieres que pase cuando den atrás
            finish()
        }


    }
}