package com.example.drones

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.drones.databinding.ActivityMain2Binding
import model.Viaje
import viewModel.AppViewModel

class MainActivity2 : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels ()
    private lateinit var binding: ActivityMain2Binding
    private var idOrigen:Long=-1L
    private var idDestino:Long=-1L
    private  var idModifico=-1L//implemento para modificar,pero ojo con el callBack y la suplantacion
                              // de contenedores,hal final hay que recrear de nuevo la vista!!!!
                              //idOrigen esta en preferencias desde la Actividad1!!
   private lateinit var preferencias: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        idModifico=intent.getLongExtra("idModificar",-1L)
         preferencias=getSharedPreferences(MainActivity.PREFERENCIAS, Context.MODE_PRIVATE)
        idOrigen=preferencias.getLong(MainActivity.ID_SELECCIONADO,-1)

        binding.listado.setOnClickListener {
            val intent= Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
        if(idModifico !=-1L){

            val f1= DestinoFragment()
            var bundle= Bundle()

            f1.arguments=bundle
            supportFragmentManager.beginTransaction().replace(R.id.fragmentoSuperior,f1).commit()



        }
       binding.registro.setOnClickListener {
            val instiOrigen=viewModel.instiPorId(idOrigen)
            val instiDestino=viewModel.instiPorId(idDestino)

            mostrarCalendario { fechaFinal ->
                val nombreO=instiOrigen?.nombre
                val nombreD=instiDestino?.nombre
                val v = Viaje(origen = nombreO!!, destino = nombreD!!, fechaHora = fechaFinal)
                if(viewModel.insertViaje(v) != -1L){
                    Toast.makeText(this,"Insertado",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"No se ha podido realizar la insercion",Toast.LENGTH_LONG).show()

                }
            }
        }

       



        val f1= DestinoFragment()
        var bundle= Bundle()
        bundle.putLong("id",idOrigen)
        f1.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentoSuperior,f1).commit()



    }
    fun maps(idDestinoP:Long){

        idDestino = idDestinoP


        val fragment= MapsFragment()
        val bundle=Bundle()


        Log.d("DEBUG", "idOrigen en maps(): $idOrigen")
        Log.d("DEBUG", "idDestino en maps(): $idDestino")
        bundle.putLong("idO",idOrigen)
        bundle.putLong("idD",idDestinoP)
        fragment.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentoInferior,fragment).commit()
        if(idModifico !=-1L){
            if(idDestino != -1L) {


                mostrarCalendario { fechaFinal ->
                    val idinstiOrigen =preferencias.getLong(MainActivity.ID_SELECCIONADO,-1)
                    val instiDestino = viewModel.instiPorId(idDestino)
                    val instOrigen=viewModel.instiPorId(idinstiOrigen)
                    val nombreO=instOrigen?.nombre
                    val nombreD = instiDestino?.nombre
                    val v = Viaje(origen = nombreO!!, destino = nombreD!!, fechaHora = fechaFinal)
                    viewModel.actualizaViaje(v)
                    val intent = Intent(this, MainActivity3::class.java)
                    startActivity(intent)
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

