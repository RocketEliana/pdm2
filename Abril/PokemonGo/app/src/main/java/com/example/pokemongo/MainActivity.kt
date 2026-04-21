package com.example.pokemongo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pokemongo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencias: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        preferencias=getSharedPreferences("preferencias", Context.MODE_PRIVATE)

        binding.acceso.setOnClickListener {
            val idSeleccionado=preferencias.getLong("idSeleccionado",-1L)
            if(idSeleccionado != -1L){
                val intent= Intent(this, Principal::class.java)
                intent.putExtra("idSeleccionado",idSeleccionado)
                startActivity(intent)
            }else{
                val intent= Intent(this, AccesoRegistro::class.java)
                startActivity(intent)
            }
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
                true}

            else ->  super.onOptionsItemSelected(item)//"Que se encargue Android"
        }
    }
}