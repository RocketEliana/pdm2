package com.example.ecorutas

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecorutas.databinding.ActivityMain2Binding
import model.Viaje
import viewModel.AppViewModel

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val viewModel: AppViewModel by viewModels()
    private var idOrigen: Long = -1L
    private var idDestino: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        idOrigen = intent.getLongExtra("idOrigen", -1L)
        val fragmentoS = SuperiorFragment()
        val bundle = Bundle()
        bundle.putLong("idOrigen", idOrigen)
        fragmentoS.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentoSuperior, fragmentoS)
            .commit()
        binding.registro.setOnClickListener {
            mostrarCalendario { fechaFinal ->
                val origenN = viewModel.getPorIdEspacio(idOrigen)
                val destinoN = viewModel.getPorIdEspacio(idDestino)
                val nombreO = origenN?.nombre
                val nombreD = destinoN?.nombre
                val t = Viaje(origen = nombreO!!, destino = nombreD!!, fecha = fechaFinal)
                if (viewModel.insertaViaje(t) != -1L) {
                    Toast.makeText(this, "Insertado", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "No se ha podido realizar la insercion", Toast.LENGTH_LONG)
                        .show()

                }
            }
        }
        binding.listado.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
        }


    }

    fun mapa(idDestino: Long) {
        idOrigen = intent.getLongExtra("idOrigen", -1L)
        val fragment = MapsFragment()
        val bundle = Bundle()
        bundle.putLong("idOrigen", idOrigen)
        bundle.putLong("idDestino", idDestino)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentoInferior, fragment).commit()

    }

    fun getIdDestino(idDestinoFragment: Long) {
        idDestino = idDestinoFragment

    }

    private fun mostrarCalendario(callback: (String) -> Unit) {
        val calendario = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, año, mes, dia ->
                TimePickerDialog(
                    this,
                    { _, hora, min ->
                        // Formateamos el resultado
                        val fechaSeleccionada =
                            String.format("%02d/%02d/%d %02d:%02d", dia, mes + 1, año, hora, min)

                        // Ejecutamos el callback con el resultado
                        callback(fechaSeleccionada)

                    },
                    calendario.get(Calendar.HOUR_OF_DAY),
                    calendario.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}

