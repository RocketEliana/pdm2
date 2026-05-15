package com.example.pokemonextension

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pokemonextension.databinding.ActivityMainBinding
import view.AppViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencias: SharedPreferences

    companion object {
        const val PREFERENCES = "preferences"
        const val ID_S = "idSeleccionado"
        const val PRIMERAEJECUCION = "primera_ejecucion"
    }

    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencias = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val primeraEjecucion = preferencias.getBoolean(PRIMERAEJECUCION, true)
        if (primeraEjecucion) {

            preferencias.edit()
                .putBoolean(PRIMERAEJECUCION, false)
                .apply()

            startActivity(Intent(this, Video::class.java))
            return
        }
        //return → corta el onCreate
        //finish() → evita que MainActivity se quede abierta detrás
        //Evitas que se lancen 2 pantallas seguidas
        val id_seleccionado=preferencias.getLong(ID_S,-1L)
        println("DEBUG ID: $id_seleccionado")
        if(id_seleccionado!=-1L){

            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)

        }else{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        }



    }
}