package com.example.ecorutas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecorutas.databinding.ActivityMainBinding
import model.Espacio
import viewModel.AppViewModel
import kotlin.getValue



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var preferencias: SharedPreferences
    private lateinit var adapter: AdapterSpinner
    private var primeraSeleccion: Boolean=true
    companion object {
        const val PREFERENCES = "preferences"
        const val ID_S = "idSeleccionado"
        const val PRIMERAEJECUCION = "primera_ejecucion"
    }


    private val viewModel: AppViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencias=getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        var iniciado=preferencias.getBoolean(PRIMERAEJECUCION,true)
        var id_seleccionado=preferencias.getLong(ID_S,-1L)
        val sitiosPrueba = listOf(


            Espacio(
                nombre = "Pizzería Napoli",
                icono = R.drawable.p,
                telefono = "987333444",
                latitud = 42.4601,
                longitud = -5.7460
            ),
            Espacio(
                nombre = "YOYO",
                icono = R.drawable.m,
                telefono = "987333674",
                latitud = 42.4619,
                longitud = -5.7478
            ),

            Espacio(
                nombre = "TuOYO",
                icono = R.drawable.g,
                telefono = "987333894",
                latitud = 42.4597,
                longitud = -5.7501

            )
        )
        if(iniciado){
            for(s in sitiosPrueba){
                viewModel.insertaEspacio(s)
            }
            preferencias.edit().putBoolean(PRIMERAEJECUCION,false).apply()
        }
        adapter= AdapterSpinner(this,mutableListOf())
        binding.spinnerOrigen.adapter=adapter
        viewModel.listaEspacio.observe(this){
            lista->adapter.actualizarLista(lista)
            if (id_seleccionado != -1L) {
                val posicion = lista.indexOfFirst { it.id == id_seleccionado }//busca la primera posición que cumpla una condición
                if (posicion != -1) {
                    binding.spinnerOrigen.setSelection(posicion)
                    binding.imagenInicio.setImageResource(lista[posicion].icono)
                }
            }


        }
        binding.spinnerOrigen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (primeraSeleccion) {
                    primeraSeleccion = false
                    return
                }

                val espacio = adapter.getItem(position)
                espacio?.let {
                    binding.imagenInicio.setImageResource(it.icono)
                    preferencias.edit().putLong(ID_S, it.id).apply()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.imagenInicio.setOnClickListener {
            id_seleccionado=preferencias.getLong(ID_S,-1L)
            if(id_seleccionado==-1L){return@setOnClickListener}
            val intent= Intent(this, MainActivity2::class.java)
            intent.putExtra("idOrigen",id_seleccionado)
            startActivity(intent)
        }




    }

}