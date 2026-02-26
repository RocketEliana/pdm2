package com.example.loginpersonalizado

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginpersonalizado.databinding.ActivityMain2Binding
import com.example.loginpersonalizado.viewModel.UserViewModel

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    // Se inicializa así, fuera de los métodos
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.alta.setOnClickListener {
            val fragmentAlta= AltaFragment()
            val transaction=supportFragmentManager.beginTransaction()

            transaction.replace(R.id.contenedor_principal,fragmentAlta).commit()

        }
        binding.registro.setOnClickListener {
            val fragmentRegistro= RegistroFragment()
            val transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.contenedor_principal,fragmentRegistro).commit()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}