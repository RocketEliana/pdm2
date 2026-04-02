package com.example.otra

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.otra.databinding.ActivityRegistroBinding
import model.User
import viewModelApp.AppViewModel

class Registro : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registro.setOnClickListener {
            val nombre = binding.nombre.text.toString()
            val contrasenia = binding.contrasenia.text.toString()
            if (!nombre.isEmpty() && !contrasenia.isEmpty()) {
                val user = User(nombre = nombre, contrasenia = contrasenia)
                val id = viewModel.insertarUser(user)
                if (id != -1L) {
                    Toast.makeText(this, "Insertado con exito", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, Bienvenida::class.java)
                    val preferences = getSharedPreferences("preferencias_app", Context.MODE_PRIVATE)
                     preferences.edit().putBoolean("iniciado",true).apply()
                    startActivity(intent)
                }

            } else {
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_LONG).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}