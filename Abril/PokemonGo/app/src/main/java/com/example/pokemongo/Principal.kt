package com.example.pokemongo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pokemongo.databinding.ActivityPrincipalBinding
import com.example.pokemongo.viewModel.AppViewModel

class Principal : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding
    private val viewModel: AppViewModel by viewModels()
    private lateinit var preferencias: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarPrincipal)
        val id = intent.getLongExtra("idSeleccionado", -1)

        if (id != -1L) {
            val user = viewModel.getUserId(id)
            Log.d("DEBUG_APP", "Usuario recuperado: ${user?.nombre}") // ¿Sale null aquí?
            user?.let {
                val nombre = it.nombre
                supportActionBar?.title = it.nombre

                // Log para confirmar que el código llega aquí
                Log.d("DEBUG_APP", "Título enviado a ActionBar: ${it.nombre}")
            }

        }
        preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        binding.nuevoPokemon.setOnClickListener { supportFragmentManager.beginTransaction().replace(R.id.contenedorPokemon,NuevoPokemonFragment()).commit() }
        binding.batallaPokemon.setOnClickListener { supportFragmentManager.beginTransaction().replace(R.id.contenedorPokemon,
            BatallaFragment()).commit() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_toolbar_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {


            R.id.cerrarSesion -> {
          preferencias.edit().putLong("idSeleccionado",-1).apply()
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)//"Que se encargue Android"
        }
    }
}