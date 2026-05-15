package com.example.pokemonextension

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pokemonextension.databinding.ActivityMain2Binding
import view.AppViewModel

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    private val viewModel: AppViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
     binding.acceso.setOnClickListener {

         supportFragmentManager.beginTransaction().replace(R.id.contenedorMain2, AccesoFragment()).commit()
     }
        binding.registro.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.contenedorMain2,
                RegistroFragment()).commit()

        }


    }
}