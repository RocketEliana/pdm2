package com.example.atletismo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.atletismo.databinding.ActivityMain2Binding
import model.Atleta
import viewModel.AppViewModel
import java.util.Calendar

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val viewModel: AppViewModel by viewModels ()
    private var nombreN:String=""
    private var dorsalN:Int=0
    private var id_catN:Long=-1L
    private var id_compet:Long=-1L
    private var pruebaN:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        id_compet = intent.getLongExtra("id", -1L)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentoSuperior, SuperiorFragment()).commit()
        binding.registro.setOnClickListener {
            mostrarCalendario { fechaFinal ->
                var a = Atleta(
                    nombre = nombreN,
                    dorsal = dorsalN,
                    prueba = pruebaN,
                    fecha = fechaFinal,
                    competicionId = id_compet,
                    categoriaId = id_catN
                )
                val id = viewModel.insertarAtleta(a)
                if (id != -1L) {
                    Toast.makeText(this, "Exitos!!", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
    fun nuevoAtleta(nombre:String,dorsal:Int,idCategoria:Long,prueba:String){
        nombreN=nombre
        dorsalN=dorsal
        id_catN=idCategoria
        pruebaN=prueba
    }
    fun categoriaMaps(id_cate:Long){
        val fragment= MapsFragment()
        id_compet = intent.getLongExtra("id", -1L)
        val bundle=Bundle()
        bundle.putLong("categoria",id_cate)
        bundle.putLong("competicion",id_compet)
        fragment.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentoInferior,fragment).commit()

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
