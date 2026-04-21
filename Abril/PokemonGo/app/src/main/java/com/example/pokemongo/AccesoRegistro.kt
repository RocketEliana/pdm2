package com.example.pokemongo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemongo.databinding.ActivityAccesoRegistroBinding
import com.example.pokemongo.viewModel.AppViewModel

class AccesoRegistro : AppCompatActivity() {
    private lateinit var binding: ActivityAccesoRegistroBinding
    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAccesoRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.acceso.setOnClickListener {
            val fragmentAcceso = AccesoFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.contenedorAcceder, fragmentAcceso).commit()
        }
        binding.registro.setOnClickListener {
            val fragmentRegistro = RegistroFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.contenedorAcceder, fragmentRegistro).commit()
        }


    }
}