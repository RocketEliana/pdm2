package com.example.trailtracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trailtracker.databinding.ActivityMainBinding
import model.Actividad
import model.Senda
import view.AdapterLista
import view.AppViewModel

class MainActivity : AppCompatActivity() {
    private  lateinit var preferencias: SharedPreferences
    private val viewModel: AppViewModel by viewModels ()
    private lateinit var adapter: AdapterLista
    private var senda: Senda?=null
    companion object{
        const val PREFERENCES="preferencias"
        const val ID_SELECCION="id_SEleccionado"
        const val INICIADO="iniciado"

    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencias=getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        var iniciado=preferencias.getBoolean(INICIADO,false)
        var idSeleccion=preferencias.getLong(ID_SELECCION,-1L)
        if(idSeleccion == -1L) {
            binding.imagenInicio.setImageResource(R.drawable.lau)
        }else{
            senda=viewModel.getSendaId(idSeleccion)
            senda?.let {
                binding.imagenInicio.setImageResource(it.foto)
            }
        }
        if(!iniciado){
            val senda1 = Senda(
                nombre        = "Cañón del Río Lobos",
                tefono        = "975363564",
                foto          = R.drawable.canon,
                latitud       = 41.84397,
                longitud      = -3.16417,
                actSenderismo = Actividad(tipo = "senderismo", latA = 41.72182, longA = -3.04736),
                actEscalada   = Actividad(tipo = "escalada",   latA = 41.75177, longA = -3.06830),
                actMontana    = Actividad(tipo = "montaña",    latA = 41.79478, longA = -3.10672)
            )
            viewModel.insertaSenda(senda1)

            val senda2 = Senda(
                nombre        = "Senda del Oso",
                tefono        = "985764623",
                foto          = R.drawable.oso,
                latitud       = 43.17001,
                longitud      = -6.09739,
                actSenderismo = Actividad(tipo = "senderismo", latA = 43.17001, longA = -6.09739),
                actEscalada   = Actividad(tipo = "escalada",   latA = 43.25672, longA = -6.00541),
                actMontana    = Actividad(tipo = "montaña",    latA = 43.23058, longA = -6.03592)
            )
            viewModel.insertaSenda(senda2)

            val senda3 = Senda(
                nombre        = "Monte Valonsadero",
                tefono        = "975000000",
                foto          = R.drawable.valonsadero,
                latitud       = 41.80983,
                longitud      = -2.54514,
                actSenderismo = Actividad(tipo = "senderismo", latA = 41.80983, longA = -2.54514),
                actEscalada   = Actividad(tipo = "escalada",   latA = 41.80500, longA = -2.53500),
                actMontana    = Actividad(tipo = "montaña",    latA = 41.81241, longA = -2.52611)
            )
            viewModel.insertaSenda(senda3)
            preferencias.edit().putBoolean(INICIADO,true).apply()
        }
       adapter= AdapterLista(this,mutableListOf()) { telefono ->
           val intent = Intent(Intent.ACTION_DIAL).apply {
               data = Uri.parse("tel:$telefono")
           }
           startActivity(intent)
       }
        binding.lista.adapter=adapter;
        viewModel.listaSenda.observe(this){
            lista->
            adapter.actualizarLista(lista)
        }
        binding.lista.setOnItemClickListener { parent, view, position, id ->
             senda = adapter.getItem(position)
            senda?.let {
                binding.imagenInicio.setImageResource(it.foto)
                preferencias.edit().putLong(ID_SELECCION,it.id).apply()
            }

        }
        binding.imagenInicio.setOnClickListener {
            idSeleccion=preferencias.getLong(ID_SELECCION,-1L)
            if(idSeleccion != -1L){
                val intent=Intent(this, MainActivity2::class.java)
                startActivity(intent)

            }

        }



    }
}