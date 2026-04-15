package com.example.mislugares

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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mislugares.databinding.ActivityMainBinding
import com.example.mislugares.viewModel.AppViewModel
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AppViewModel by viewModels ()
    private lateinit var adapter: AdapterLista
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        preferences=getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val seleccionado=preferences.getLong("idSeleccionado",-1)
        if(seleccionado != -1L){ val intent=Intent(this,Detalle::class.java)
            intent.putExtra("id",seleccionado)
            startActivity(intent)
        }

        adapter= AdapterLista(this,mutableListOf())
        binding.lista.adapter=adapter

        viewModel.lista.observe(this){
            lista->adapter.actualizarLista(lista)
        }
        binding.lista.setOnItemClickListener { parent, view, position, id ->
            val lugar = adapter.getItem(position)
            val lugarId = lugar?.id ?: -1L
            if(lugarId != -1L){
                preferences.edit().putLong("idSeleccionado",lugarId).apply()
                val intent=Intent(this,Detalle::class.java)
                intent.putExtra("id",lugarId)
                startActivity(intent)
            }

        }


        binding.addBtn.setOnClickListener {
            val intent= Intent(this, Nuevo::class.java)

            startActivity(intent)
        }




    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.acercade -> {
                Toast.makeText(this, "TheBug,2026", Toast.LENGTH_LONG).show()

                true
            }

            R.id.ordenarmayor -> {
                viewModel.lmayorMenor.observe(this){
                    adapter.actualizarLista(it)
                }

                true
            }
            R.id.ordenarmenor -> {
                viewModel.lmenorMayor.observe(this){
                    adapter.actualizarLista(it)
                }

                true
            }

            else ->  super.onOptionsItemSelected(item)//"Que se encargue Android"
        }
    }
   /* fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2).pow(2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2).pow(2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return r * c
    }

    */



}