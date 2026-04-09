package com.example.otra

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.otra.databinding.ActivityMainBinding
import model.User
import viewModelApp.AppViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AppViewModel by viewModels()
    private lateinit var preferences: SharedPreferences

    //ojo con el spaguetto!!!LLAMAR A FUNCIONES DECLARADAS ABAJO,MEJOR,MAS LEGIBLE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = getSharedPreferences("preferencias_app", Context.MODE_PRIVATE)
        val iniciado = preferences.getBoolean("iniciado", false)
        val nombre = intent.getStringExtra("nombre")
        val contrasenia = intent.getStringExtra("contrasenia")
        val user: User?
        if (nombre != null && contrasenia != null) {

            user = viewModel.existeUser(nombre, contrasenia)
            if (user != null) {

                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setMessage("Bienvenido!")
                    .setTitle("The bug")
                    .setPositiveButton("seguir") { dialog, which ->


                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                    }
                preferences.edit().putBoolean("iniciado", true).apply()
                irBienvenida()


            } else {
                irRegistro()
            }
        }

        if ((nombre == null || contrasenia == null) && iniciado) {
           irBienvenida()
        } else {
            irRegistro()

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun irRegistro() {
        val intent = Intent(this, Registro::class.java)
        startActivity(intent)
    }

    fun irBienvenida() {
        val intent = Intent(this, Bienvenida::class.java)
        startActivity(intent)
    }

}