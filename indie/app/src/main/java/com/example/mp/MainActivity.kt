package com.example.mp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mp.databinding.ActivityMainBinding
import model.Sala
import ui.AdapterListaPrincipal
import viewModel.ViewModelGira

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencias: SharedPreferences
    private lateinit var adapter: AdapterListaPrincipal
    private val viewModel: ViewModelGira by viewModels()
    companion object{
        const val PREFERENCES="preferences"
        const val INICIADO="iniciado"
        const val SELECCIONADO="seleccionado"
        const val IDSALA="id"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        preferencias = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val iniciado = preferencias.getBoolean(INICIADO, false)
        val imagenLogo = preferencias.getInt(SELECCIONADO, R.drawable.vc)




        binding.logo.setImageResource(imagenLogo)

        if (!iniciado) {
            val listaInicialSalas = listOf(
                Sala(
                    nombre = "La Rivera",
                    imagen = R.drawable.larivera,
                    latitud = 40.4129999,
                    longitud = -3.7221514,
                    telefono = "623338696"
                ),
                Sala(
                    nombre = "RazzMatazz",
                    imagen = R.drawable.razz,
                    latitud = 41.3977323,
                    longitud = 2.1911108,
                    telefono = "623338696"
                ),
                Sala(
                    nombre = "Copera",
                    imagen = R.drawable.copera,
                    latitud = 37.1303369,
                    longitud = -3.5837524,
                    telefono = "623338696"
                ),
            )
            for (lista in listaInicialSalas) {
                viewModel.insertarSala(lista)
            }
            preferencias.edit().putBoolean(INICIADO, true).apply()
        }
        viewModel.listaSala.observe(this) { it ->
            val listaSalasSegura = it
            adapter = AdapterListaPrincipal(this, listaSalasSegura)
            binding.lista.adapter = adapter

        }
        binding.lista.setOnItemClickListener { _, _, i, _ ->

            val listaActual = viewModel.listaSala.value
            val salaSeleccionada = listaActual?.get(i).let { salaSegura ->
                val imagenLogo = salaSegura?.imagen
                val id = salaSegura?.id
                binding.logo.setImageResource(imagenLogo!!)
                preferencias.edit().putInt(SELECCIONADO, imagenLogo).apply()
                preferencias.edit().putInt(IDSALA, id ?: -1).apply()

            }
        }
        binding.logo.setOnClickListener {

            val idSeleccionado = preferencias.getInt(IDSALA, -1)

            if (idSeleccionado != -1) {
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("idSeleccion", idSeleccionado)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Ha de seleccionar un instituto", Toast.LENGTH_LONG).show()
            }
        }
    }
}