package com.example.trailtracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.trailtracker.databinding.ActivityMain2Binding
import com.example.trailtracker.MapsFragment

import view.AppViewModel

class MainActivity2 : AppCompatActivity() {

    private lateinit var preferencias: SharedPreferences
    private val viewModel: AppViewModel by viewModels()
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            binding = ActivityMain2Binding.inflate(layoutInflater)
            setContentView(binding.root)
        preferencias = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE) // ← añade esto
        supportFragmentManager.beginTransaction().replace(R.id.fragmentoSuperior,
            SeleccionTipoFragment()).commit()




        }
    fun irActividad(actividad:String){
        val idSenda=preferencias.getLong(MainActivity.ID_SELECCION,-1L)
        if(idSenda !=-1L){
            val transaction=supportFragmentManager.beginTransaction()
            val fragmento= MapsFragment()
            val bundle= Bundle()
            bundle.putString("tipo",actividad)
            fragmento.arguments=bundle
            transaction.replace(R.id.fragmentoInferior,fragmento).commit()
        }
    }

}
