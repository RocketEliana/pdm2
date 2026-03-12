package com.example.mp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mp.databinding.ActivityMain2Binding
import kotlinx.coroutines.launch
import ui.AdapterSpinner
import viewModel.ViewModelGira
import kotlin.math.roundToInt

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val viewModel: ViewModelGira by viewModels()
    private var idRecibido: Int = -1  // Guardar el ID original

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Guardar el ID recibido
        idRecibido = intent.getIntExtra("idSeleccion", -1)

        // Fragment 1 (Spinner)
        val fragment1 = Fragment1()
        val bundle = Bundle()
        bundle.putInt("id", idRecibido)
        fragment1.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentoSuperior, fragment1)
            .commit()

        // Fragment 2 (Mapa) - SOLO con el primer marcador
        mostrarMapaConMarcadores(idRecibido, null)
    }

    // Esta función se llama cuando se selecciona en el Spinner
    fun recibeSeleccionado(idSpinner: Int) {
        // Actualizar el mapa con AMBOS marcadores
        mostrarMapaConMarcadores(idRecibido, idSpinner)
    }

    // Función auxiliar para mostrar/actualizar el mapa
    private fun mostrarMapaConMarcadores(idPrimero: Int, idSegundo: Int?) {
        val fragment2 = MapsFragment()
        val bundle2 = Bundle()
        bundle2.putInt("id", idPrimero)

        if (idSegundo != null) {
            bundle2.putInt("idSpinner", idSegundo)
        }

        fragment2.arguments = bundle2

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentoInferior, fragment2)
            .commit()
    }
}