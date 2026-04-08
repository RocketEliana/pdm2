package com.example.bibliotecaabril.ViewModel

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bibliotecaabril.Fragment2
import com.example.bibliotecaabril.R
import com.example.bibliotecaabril.databinding.ActivityMain2Binding
import com.example.bibliotecaabril.model.Traslado

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val viewModel: AppViewModel by viewModels()
    private var idOrigen: Long? = null
    private var idDestino: Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        idOrigen = intent.getLongExtra("idSeleccionado", -1L) ?: -1
        val fragment1 = Fragment1()
        val bundle = Bundle()
        bundle.putLong("idOrigenMain", idOrigen ?: -1)
        fragment1.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.superior, fragment1).commit()
        binding.registro.setOnClickListener {
            if(idOrigen != -1L && idDestino != -1L){
            val ba = viewModel.bibliotecaById(idOrigen!!)
            val nombreOrigen = ba?.nombre ?: ""
            val bb = viewModel.bibliotecaById(idDestino!!)
            val nombreDestino = bb?.nombre ?: ""
             mostrarCalendario { fechaFinal ->
                val t = Traslado(origen = nombreOrigen, destino = nombreDestino, fecha = fechaFinal)
                if(viewModel.insertaTraslado(t) != -1L){
                    Toast.makeText(this,"Insertado",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"No se ha podido realizar la insercion",Toast.LENGTH_LONG).show()

                }
            }
            }


        }
        binding.verTraslado.setOnClickListener {
            val intent= Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun pasoPorId(id: Long) {
        val fragment2 = Fragment2()
        val bundle = Bundle()
        if (id != -1L) {
            idDestino=id
            bundle.putLong("idDestino", id)
            bundle.putLong("idOrigen", idOrigen!!)

            fragment2.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.inferior, fragment2).commit()
        }
    }


    // Cambiamos el retorno String por un callback (String) -> Unit
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