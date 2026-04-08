package com.example.bibliotecaabril

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bibliotecaabril.ViewModel.AdapterOrigenSpinner
import com.example.bibliotecaabril.ViewModel.AppViewModel
import com.example.bibliotecaabril.ViewModel.MainActivity2
import com.example.bibliotecaabril.databinding.ActivityMainBinding
import com.example.bibliotecaabril.model.Biblioteca

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencias: SharedPreferences
    private val viewModel: AppViewModel by viewModels()
    private lateinit var adapter: AdapterOrigenSpinner

    private var primeraSeleccion = true

    companion object {
        const val PREFERENCIAS = "preferences"
        const val INICIADO = "iniciado"
        const val ID_FAVORITO = "id_favorito"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencias = getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE)

        val iniciado = preferencias.getBoolean(INICIADO, false)
        val idSeleccion = preferencias.getLong(ID_FAVORITO, -1L)

        if (!iniciado) {
            val b1 = Biblioteca(
                nombre = "Valladolid",
                correo = "Biblioteca.universitaria@uva.es",
                imagen = R.drawable.va,
                latitud = 41.65541,
                longitud = -4.72353
            )
            val b2 = Biblioteca(
                nombre = "Salamanca",
                correo = "Biblioteca.universitaria@sal.es",
                imagen = R.drawable.sa,
                latitud = 40.96882,
                longitud = -5.66388
            )
            val b3 = Biblioteca(
                nombre = "Zamora",
                correo = "Biblioteca.universitaria@za.es",
                imagen = R.drawable.za,
                latitud = 41.5225857,
                longitud = -5.8005370
            )

            viewModel.insertaBiblioteca(b1)
            viewModel.insertaBiblioteca(b2)
            viewModel.insertaBiblioteca(b3)

            preferencias.edit().putBoolean(INICIADO, true).apply()
        }


        if (idSeleccion == -1L) {
            binding.imagenInicio.setImageResource(R.drawable.defecto)
        }

        adapter = AdapterOrigenSpinner(this, mutableListOf())
        binding.spinner.adapter = adapter


        viewModel.listaBiblioteca.observe(this) { lista ->
            adapter.actualizarLista(lista)

            if (idSeleccion != -1L) {
                val posicion = lista.indexOfFirst { it.id == idSeleccion }
                if (posicion != -1) {
                    binding.spinner.setSelection(posicion)
                    binding.imagenInicio.setImageResource(lista[posicion].imagen)
                }
            }
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

                val biblioteca = adapter.getItem(position)
                biblioteca?.let {
                    binding.imagenInicio.setImageResource(it.imagen)
                    preferencias.edit().putLong(ID_FAVORITO, it.id).apply()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        binding.imagenInicio.setOnClickListener {
            val idActual = preferencias.getLong(ID_FAVORITO, -1L)
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("idSeleccionado", idActual)

            if (idActual != -1L) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Seleccione un origen", Toast.LENGTH_LONG).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}