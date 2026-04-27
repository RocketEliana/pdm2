package com.example.musicplayer

import View.AppViewModel
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.databinding.ActivityPrincipalBinding

class Principal : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding
    private val viewModel: AppViewModel by viewModels()
    private lateinit var preferencias: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencias = getSharedPreferences(MainActivity.PREFERENCIAS, Context.MODE_PRIVATE)

        setSupportActionBar(binding.toolbarPrincipal)
        binding.nuevaCancion.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.contenedorMain2, NuevaFragment()).addToBackStack(null)
                .commit()
        }
        binding.conciertos.setOnClickListener {

            supportFragmentManager.beginTransaction().replace(R.id.contenedorMain2, MapsFragment()).commit()

        }
        binding.comparar
            .setOnClickListener {

            supportFragmentManager.beginTransaction().replace(R.id.contenedorMain2,
                CompararFragment()).commit()

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cerraSesion -> {
                cerrarSesion()
                true
            }


            R.id.acercade -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setMessage("Estas conociendo al desarrollador de la app")
                    .setTitle("The bug")
                    .setIcon(R.drawable.mu)
                    .setPositiveButton("Encantada!!!") { dialog, which ->
                        // Do something.
                    }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                true
            }

            else -> super.onOptionsItemSelected(item)//"Que se encargue Android"
        }

    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val idSeleccionado = preferencias.getLong(MainActivity.ID_REGISTRADO, -1L)

        if (idSeleccionado != -1L) {
            val user = viewModel.userById(idSeleccionado)
            menu?.findItem(R.id.user)?.title = user?.nombre ?: "Usuario"
        }

        return super.onPrepareOptionsMenu(menu)
    }

    fun cerrarSesion() {
        preferencias.edit().putLong(MainActivity.ID_REGISTRADO,-1L).apply()
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}