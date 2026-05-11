package com.example.comidallevar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.comidallevar.databinding.ActivityMain2Binding
import model.Pedido
import viewModel.AppViewModel

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var preferencias: SharedPreferences
    private var plato: String = ""
    private var direccion: String = ""


    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencias = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentoSuperior, PlatoFragment())
            .commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentoInferior, MapsFragment())
            .commit()
        binding.listado.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
        binding.registro.setOnClickListener {

            aniadirPedido()

        }
    }


    fun insert(platoP: String, direccionP: String) {
        plato = platoP
        direccion = direccionP

    }

    fun aniadirPedido() {
        mostrarCalendario { fechaFinal ->
            val idS = preferencias.getLong(MainActivity.ID_S, -1L)
            if (idS != -1L) {

                val r = viewModel.getById(idS)
                val nombre = r?.nombre

                val pedido = Pedido(
                    restaurante = nombre!!,
                    plato = plato,
                    entrega = direccion,
                    fechaHora = fechaFinal
                )
                val idNew = viewModel.insertarPedido(pedido)
                if (idNew == null) {
                    Toast.makeText(
                        this, "Faltan campos o ha habido incidencia,aseguirate de que todo va ok",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this, "ok",
                        Toast.LENGTH_LONG
                    ).show()

                }


            }
        }
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

